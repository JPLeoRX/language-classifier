package com.tekleo.language_classifier.utils;

import java.util.HashSet;
import java.util.LinkedList;
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

    public static String trim(String word) {
        if (word.contains("/"))
            word = word.split("/")[0];

        return word.toLowerCase();
    }

    public static List<String> trim(List<String> strings) {
        List<String> trimmed = new LinkedList<>();
        for (String word : strings)
            trimmed.add(trim(word));
        return trimmed;
    }

    public static List<String> filter(List<String> strings) {
        HashSet<String> discovered = new HashSet<>();
        List<String> filtered = new LinkedList<>();

        for (String word : strings) {
            // If the length is greater than one letter
            if (word.length() > 1) {
                // If this word doesn't contain any digits
                if (!word.matches(".*\\d+.*")) {
                    // And if this word was not already seen
                    if (!discovered.contains(word)) {
                        filtered.add(word);
                        discovered.add(word);
                    }
                }
            }
        }

        return filtered;
    }
}