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

    public static int[] toIntArray(double[] doublesArray) {
        int[] intArray = new int[doublesArray.length];
        for (int index = 0; index < doublesArray.length; index++)
            intArray[index] = (int) Math.round(doublesArray[index]);
        return intArray;
    }

    public static char[] toCharArray(int[] intArray) {
        char[] charArray = new char[intArray.length];
        for (int index = 0; index < intArray.length; index++)
            charArray[index] = Character.toString((char) intArray[index]).toCharArray()[0];
        return charArray;
    }

    public static boolean contains(int[] a, int i) {
        for (int x : a)
            if (x == i)
                return true;
        return false;
    }
}
