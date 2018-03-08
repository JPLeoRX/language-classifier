package com.tekleo.language_classifier.dictionaries;

import java.util.LinkedList;
import java.util.List;

public enum Language {
    CZECH(1), ENGLISH(2), GERMAN(3), RUSSIAN(4), TURKISH(5);

    private int value;

    Language(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<Integer> getAllPossibleValues() {
        List<Integer> values = new LinkedList<>();
        for (Language language : values())
            values.add(language.getValue());
        return values;
    }
}