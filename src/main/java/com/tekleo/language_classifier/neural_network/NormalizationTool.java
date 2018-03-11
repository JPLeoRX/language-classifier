package com.tekleo.language_classifier.neural_network;

public class NormalizationTool {
    private double actualLow, actualHigh;
    private double normalizedLow, normalizedHigh;

    public NormalizationTool(double actualLow, double actualHigh, double normalizedLow, double normalizedHigh) {
        this.actualHigh = actualHigh;
        this.actualLow = actualLow;
        this.normalizedHigh = normalizedHigh;
        this.normalizedLow = normalizedLow;
    }

    public double normalize(double x) {
        return ((x - actualLow) / (actualHigh - actualLow)) * (normalizedHigh - normalizedLow) + normalizedLow;
    }

    public double[] normalize(double[] array) {
        double[] normalizedArray = new double[array.length];
        for (int index = 0; index < array.length; index++)
            normalizedArray[index] = this.normalize(array[index]);
        return normalizedArray;
    }

    public double denormalize(double norm) {
        return (norm - normalizedLow) / (normalizedHigh - normalizedLow) * (actualHigh - actualLow) + actualLow;
    }

    public double[] denormalize(double[] normalizedArray) {
        double[] denormalizedArray = new double[normalizedArray.length];
        for (int index = 0; index < normalizedArray.length; index++)
            denormalizedArray[index] = this.denormalize(normalizedArray[index]);
        return denormalizedArray;
    }
}