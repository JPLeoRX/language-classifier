package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.neural_network.NormalizationTool;
import com.tekleo.language_classifier.utils.ArrayUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * This class will be used to restore words back from their machine learning format
 * It takes the neural network's input array and parses it back to  meaningful word
 * @author Leo Ertuna
 * @since 09.03.2018 01:20
 */
public class WordEvaluator {
    // Input
    private double[] neuralNetworkInput;

    // Output
    private String word;

    /**
     * Constructor
     * @param neuralNetworkInput
     */
    public WordEvaluator(double[] neuralNetworkInput) {
        this.neuralNetworkInput = neuralNetworkInput;
        this.init();
    }

    public String getWord() {
        return word;
    }

    private void init() {
        // Denormalize
        double[] denormalized = new NormalizationTool(32, 8220, 0, 1).denormalize(neuralNetworkInput);

        // Convert to int array
        int[] intArray = ArrayUtils.toIntArray(denormalized);

        // Convert to char
        char[] charArray = ArrayUtils.toCharArray(intArray);

        // Filter out the empty spaces
        List<Character> filtered = new LinkedList<>();
        for(char c : charArray)
            if (!String.valueOf(c).equals(" "))
                filtered.add(c);

        // Convert back to string
        String str = "";
        for (Character c : filtered)
            str += String.valueOf(c);

        word = str;
    }
}