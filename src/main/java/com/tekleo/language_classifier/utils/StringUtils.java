package com.tekleo.language_classifier.utils;

import java.util.List;

public class StringUtils {
    public static List<String> split(String s, String d) {
        return ListUtils.toArrayList(s.split(d));
    }

    public static int[] toIntArray(String s) {
        // Create new arrays
        char[] c = s.toCharArray();
        int[] a = new int[s.length()];

        // For each character
        for (int i = 0; i < s.length(); i++)
            a[i] = c[i];

        // Return results
        return a;
    }

    public static String leftPad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }

    public static String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }
}
