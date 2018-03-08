package com.tekleo.language_classifier.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;

public class FileUtils {
    public static String getFileAsString(String fileName) {
        String result = "";

        ClassLoader classLoader = FileUtils.class.getClassLoader();

        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int getLengthOfLongestWord(String filename) {
        int maxLength = 0;
        String text = getFileAsString(filename);
        List<String> words = StringUtils.split(text, "\n");
        List<String> filteredWords = StringUtils.filter(words);
        for (String word : filteredWords)
            if (word.length() > maxLength)
                maxLength = word.length();

        return maxLength;
    }
}
