package com.tekleo.language_classifier.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

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
}
