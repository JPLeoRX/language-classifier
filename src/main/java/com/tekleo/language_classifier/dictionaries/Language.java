package com.tekleo.language_classifier.dictionaries;

import java.util.LinkedList;
import java.util.List;

public enum Language {
    // Values
    //------------------------------------------------------------------------------------------------------------------
    ARMENIAN(1),
    BASQUE(2),
    BULGARIAN(3),
    CATALAN(4),
    CROATIAN(5),
    CZECH(6),
    DANISH(7),
    DUTCH(8),
    ENGLISH(9),
    ESTONIAN(10),
    FRENCH(11),
    GALEGO(12),
    GERMAN(13),
    GREEK(14),
    HUNGARIAN(15),
    ICELANDIC(16),
    INDONESIAN(17),
    ITALIAN(18),
    KOREAN(19),
    LATIN(20),
    LATVIAN(21),
    LITHUANIAN(22),
    LUXEMBOURGISH(23),
    MALAYS(24),
    MONGOLIAN(25),
    NORWEGIAN(26),
    POLISH(27),
    PORTUGUESE(28),
    ROMANIAN(29),
    RUSSIAN(30),
    SERBIAN(31),
    SLOVENIAN(32),
    SPANISH(33),
    SWEDISH(34),
    TURKISH(35),
    UKRAINIAN(36),
    VIETNAMESE(37);
    //------------------------------------------------------------------------------------------------------------------



    // Enum
    //------------------------------------------------------------------------------------------------------------------
    private int asInt;

    Language(int asInt) {
        this.asInt = asInt;
    }

    public int getAsInt() {
        return asInt;
    }

    public int[] getAsVector() {
        int[] v = new int[37];
        v[asInt - 1] = 1;
        return v;
    }

    public static List<Integer> getAllPossibleValues() {
        List<Integer> values = new LinkedList<>();
        for (Language language : values())
            values.add(language.getAsInt());
        return values;
    }
    //------------------------------------------------------------------------------------------------------------------
}