package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.DictionariesLoader;
import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.neural_network.WordsDataSet;
import com.tekleo.language_classifier.neural_network.WordsDataSetBuilder;
import com.tekleo.language_classifier.neural_network.WordsNeuralNetwork;

import java.util.List;

public class ExampleCreateNetwork {
    public static void main(String[] args) {
        // Load all dictionaries
        List<Dictionary> dictionaries = DictionariesLoader.load();

        // Create data set builder
        WordsDataSetBuilder builder = new WordsDataSetBuilder();
        builder = builder.setRatio(0.75);
        builder = builder.setBatchSize(1);
        builder = builder.setDictionaries(dictionaries);

        // Create data set
        WordsDataSet dataSet = builder.build();

        // Create neural network
        WordsNeuralNetwork network = new WordsNeuralNetwork(dataSet.getIteratorTraining(), dataSet.getIteratorTesting());
        network.train();
        network.test();
    }
}
