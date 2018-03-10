package com.tekleo.language_classifier.utils;

import org.nd4j.linalg.api.ndarray.INDArray;

public class NDArrayUtils {
    public static double[] rowToArray(INDArray row) {
        double[] rowArray = new double[row.columns()];
        for (int i = 0; i < row.columns(); i++)
            rowArray[i] = row.getDouble(i);
        return rowArray;
    }
}
