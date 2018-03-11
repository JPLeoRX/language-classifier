package com.tekleo.language_classifier.utils;

import java.util.ArrayList;

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

    public static double[] toDoublesArray(ArrayList<Double> list) {
        double[] doublesArray = new double[list.size()];
        for (int index = 0; index < list.size(); index++)
            doublesArray[index] = list.get(index);
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

    public static double min(double[] a) {
        double m = Double.MAX_VALUE;
        for (double x : a)
            if (x < m)
                m = x;
        return m;
    }

    public static double max(double[] a) {
        double m = Double.MIN_VALUE;
        for (double x : a)
            if (x > m)
                m = x;
        return m;
    }
}