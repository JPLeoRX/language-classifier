package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.utils.FileUtils;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a dictionary that parses a list of words and prepares them for machine learning
 * We start by reading whole file as one string
 * Then we split that string into words and shuffle them
 * @author Leo Erunta
 * @since 07.03.2018 23:42
 */
public class Dictionary implements Serializable, Cloneable {
    private static final int SHUFFLE_SEED = 666;

    // Input
    private String filepath;
    private Language language;

    // Output
    private List<Word> words;



    // Constructors
    //------------------------------------------------------------------------------------------------------------------
    public Dictionary(String filepath, Language language) {
        this.filepath = filepath;
        this.language = language;

        String text = FileUtils.getFileAsString(filepath);
        words = Word.getWords(text, "\n", language);
        Collections.shuffle(words, new Random(SHUFFLE_SEED));
    }
    //------------------------------------------------------------------------------------------------------------------



    // Getters
    //------------------------------------------------------------------------------------------------------------------
    public String getFilepath() {
        return filepath;
    }

    public Language getLanguage() {
        return language;
    }

    public List<Word> getWords() {
        return words;
    }
    //------------------------------------------------------------------------------------------------------------------



    // Others
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dictionary)) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(filepath, that.filepath) && language == that.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filepath, language);
    }

    @Override
    protected Dictionary clone() {
        return new Dictionary(filepath, language);
    }
    //------------------------------------------------------------------------------------------------------------------
}