package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.utils.StringUtils;

import java.util.Arrays;

/**
 * This class represents a word that is prepared for machine learning
 * @author Leo Ertuna
 * @since 06.03.2018 23:50
 */
public class Word {
    private static final int MAX_SIZE = 200;

    // Inputs
    private String word;
    private Language language;

    // Output
    private String wordStretched;
    private int[] wordAsInt;
    private int languageAsInt;

    public Word(String word, Language language) {
        this.word = word;
        this.language = language;

        this.initWordStretched();
        this.initWordAsInt();
        this.initLanguageAsInt();
    }

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
            throw new IllegalArgumentException("Illegal stretched word value! wordStretched=" + wordStretched);

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

    public int[] getWordAsInt() {
        return wordAsInt;
    }

    public int getLanguageAsInt() {
        return languageAsInt;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", language=" + language +
                ", wordStretched='" + wordStretched + '\'' +
                ", wordAsInt=" + Arrays.toString(wordAsInt) +
                ", languageAsInt=" + languageAsInt +
                '}';
    }
}