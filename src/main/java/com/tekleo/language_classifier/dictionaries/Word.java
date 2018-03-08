package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.utils.ArrayUtils;
import com.tekleo.language_classifier.utils.StringUtils;
import org.nd4j.linalg.primitives.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a word that is prepared for machine learning
 * We start by stretching the word to its maximum length and filling it with empty spaces (padding)
 * Then we convert the stretched word to an array of integers (using char code)
 * Finally we convert integers to doubles and pair them as input-output
 * @author Leo Ertuna
 * @since 06.03.2018 23:50
 */
public class Word implements Serializable, Cloneable {
    private static final int MAX_SIZE = 200;

    // Inputs
    private String word;                            // Original word
    private Language language;                      // Language of this word

    // Output
    private String wordStretched;                   // Word stretched to its MAX_SIZE and filled with spaces
    private int[] wordAsInt;                        // Stretched word converted to integer array format
    private int languageAsInt;                      // Language converted to integer format
    private double[] nnInput;                       // Input for neural networks
    private double[] nnOutput;                      // Output for neural networks
    private Pair<double[], double[]> pair;          // Pair of double arrays prepared for machine learning

    // Constructors
    //------------------------------------------------------------------------------------------------------------------
    public Word(String word, Language language) {
        this.word = word;
        this.language = language;

        this.initWordStretched();
        this.initWordAsInt();
        this.initLanguageAsInt();
        this.initInput();
        this.initOutput();
        this.initPair();
    }
    //------------------------------------------------------------------------------------------------------------------



    // Initialization
    //------------------------------------------------------------------------------------------------------------------
    private void initWordStretched() {
        // Check if the word is legal
        if (word == null || word.isEmpty())
            throw new IllegalArgumentException("Illegal word value! word=" + word);

        // Stretch it
        wordStretched = StringUtils.rightPad(word, MAX_SIZE);
    }

    private void initWordAsInt() {
        // Check if the word stretched was computed
        if (wordStretched == null || wordStretched.length() != MAX_SIZE || !wordStretched.contains(word))
            throw new IllegalArgumentException("Illegal wordStretched value! wordStretched=" + wordStretched);

        // Get int array
        wordAsInt = StringUtils.toIntArray(wordStretched);
    }

    private void initLanguageAsInt() {
        // Check if the language is legal
        if (language == null)
            throw new IllegalArgumentException("Illegal language value! language=" + language);

        // Convert it
        languageAsInt = language.getValue();
    }

    private void initInput() {
        // Check if the word int is legal
        if (wordAsInt == null || wordAsInt.length != MAX_SIZE)
            throw new IllegalArgumentException("Illegal wordAsInt value! wordAsInt=" + wordAsInt);

        // Convert it
        nnInput = ArrayUtils.toDoublesArray(wordAsInt);
    }

    private void initOutput() {
        // Check if the language int is legal
        if (!ArrayUtils.contains(Language.getAllPossibleValues(), languageAsInt))
            throw new IllegalArgumentException("Illegal languageAsInt value! languageAsInt=" + languageAsInt);

        nnOutput = ArrayUtils.toDoublesArray(languageAsInt);
    }

    private void initPair() {
        // Check if the input is legal
        if (nnInput == null || nnInput.length != MAX_SIZE)
            throw new IllegalArgumentException("Illegal nnInput value! nnInput=" + nnInput);

        // Check if the output is legal
        if (nnOutput == null || nnOutput.length != 1)
            throw new IllegalArgumentException("Illegal nnOutput value! nnOutput=" + nnOutput);

        pair = new Pair<>(nnInput, nnOutput);
    }
    //------------------------------------------------------------------------------------------------------------------



    // Getters
    //------------------------------------------------------------------------------------------------------------------
    public String getOriginalWord() {
        return word;
    }

    public Language getLanguage() {
        return language;
    }

    public String getWordStretched() {
        return wordStretched;
    }

    public int[] getWordAsInt() {
        return wordAsInt;
    }

    public int getLanguageAsInt() {
        return languageAsInt;
    }

    public double[] getNnInput() {
        return nnInput;
    }

    public double[] getNnOutput() {
        return nnOutput;
    }

    public Pair<double[], double[]> getPair() {
        return pair;
    }
    //------------------------------------------------------------------------------------------------------------------



    // Static converters
    //------------------------------------------------------------------------------------------------------------------
    public static Word getWord(String s, Language l) {
        return new Word(s, l);
    }

    public static List<Word> getWords(List<String> strings, Language language) {
        List<Word> words = new ArrayList<>(strings.size());
        for (String string : strings)
            words.add(new Word(string, language));
        return words;
    }

    public static List<Word> getWords(String text, String delim, Language language) {
        return getWords(StringUtils.split(text, delim), language);
    }

    public static List<Pair<double[], double[]>> getPairs(List<Word> words) {
        List<Pair<double[], double[]>> pairs = new ArrayList<>(words.size());
        for (Word word : words)
            pairs.add(word.getPair());
        return pairs;
    }
    //------------------------------------------------------------------------------------------------------------------



    // Others
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && language == word1.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, language);
    }

    @Override
    protected Word clone() {
        return new Word(this.getOriginalWord(), this.getLanguage());
    }
    //------------------------------------------------------------------------------------------------------------------
}