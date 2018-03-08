package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.utils.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will be used to restore language back from its machine learning format
 * It takes the neural network's output array and parses it back to present probabilities of languages
 * @author Leo Ertuna
 * @since 09.03.2018 01:22
 */
public class LanguageEvaluator {
    // Input
    private double[] neuralNetworkOutput;

    // Output
    private Map<Language, Double> probabilities = new HashMap<>();

    /**
     * Constructor
     * @param neuralNetworkOutput
     */
    public LanguageEvaluator(double[] neuralNetworkOutput) {
        this.neuralNetworkOutput = neuralNetworkOutput;
        this.init();
    }

    public Map<Language, Double> getProbabilities() {
        return probabilities;
    }

    private void init() {
        // For each existing language
        for (Language language : Language.values())
            // Store its probability in the hashmap
            probabilities.put(language, neuralNetworkOutput[language.getAsInt() - 1]);

        // Sort by values
        probabilities = MapUtils.sortByValue(probabilities);
    }
}