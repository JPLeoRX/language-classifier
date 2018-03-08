package com.tekleo.language_classifier.dictionaries;

public enum Language {
    ENGLISH(1), GERMAN(2);

    private int value;

    private Language(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int[] getAllPossibleValues() {
        return new int[]{ENGLISH.value, GERMAN.value};
    }
}
