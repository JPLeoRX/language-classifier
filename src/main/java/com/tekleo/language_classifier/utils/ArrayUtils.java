package com.tekleo.language_classifier.utils;

public class ArrayUtils {
    public static double[] toDoublesArray(int[] intArray) {
        double[] doublesArray = new double[intArray.length];
        for (int index = 0; index < intArray.length; index++)
            doublesArray[index] = (double) intArray[index];
        return doublesArray;
    }
}
