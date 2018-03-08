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
    private static final int MAX_SIZE = 50;

    // Inputs
    private String word;                            // Original word
    private Language language;                      // Language of this word

    // Output
    private Pair<double[], double[]> pair;          // Pair of double arrays prepared for machine learning



    // Constructors
    //------------------------------------------------------------------------------------------------------------------
    public Word(String word, Language language) {
        this.word = word;                                                       // Original word
        this.language = language;                                               // Language of this word

        String wordStretched = StringUtils.rightPad(word, MAX_SIZE);            // Word stretched to its MAX_SIZE and filled with spaces
        int[] wordAsInt = StringUtils.toIntArray(wordStretched);                // Stretched word converted to integer array format
        int[] languageAsInt = language.getAsVector();                           // Language converted to integer array format
        double[] nnInput = ArrayUtils.toDoublesArray(wordAsInt);                // Input for neural networks
        double[] nnOutput = ArrayUtils.toDoublesArray(languageAsInt);           // Output for neural networks
        pair = new Pair<>(nnInput, nnOutput);                                   // Pair of double arrays prepared for machine learning
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
        // Parse text into unique strings
        List<String> strings = StringUtils.split(text, delim);

        // Trim each string
        List<String> trimmed = StringUtils.trim(strings);

        // Filter them (remove single letters and words with digits)
        List<String> filtered = StringUtils.filter(trimmed);

        // Parse as words
        return getWords(filtered, language);
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