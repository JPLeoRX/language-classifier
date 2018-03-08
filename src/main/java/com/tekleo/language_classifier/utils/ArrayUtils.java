package com.tekleo.language_classifier.utils;

public class ArrayUtils {
    public static int[] fromOne(int i) {
        return new int[]{i};
    }

    public static double[] toDoublesArray(int[] intArray) {
        double[] doublesArray = new double[intArray.length];
        for (int index = 0; index < intArray.length; index++)
            doublesArray[index] = (double) intArray[index];
        return doublesArray;
    }

    public static double[] toDoublesArray(int i) {
        return toDoublesArray(fromOne(i));
    }

    public static boolean contains(int[] a, int i) {
        for (int x : a)
            if (x == i)
                return true;
        return false;
    }
}
