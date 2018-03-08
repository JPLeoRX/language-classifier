package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.DictionariesLoader;
import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.neural_network.WordsDataSet;
import com.tekleo.language_classifier.neural_network.WordsDataSetBuilder;

import java.util.List;

public class ExampleCreateDataset {
    public static void main(String[] args) {
        // Load all dictionaries
        List<Dictionary> dictionaries = DictionariesLoader.load();

        // Create data set builder
        WordsDataSetBuilder builder = new WordsDataSetBuilder();
        builder = builder.setRatio(0.75);
        builder = builder.setBatchSize(10);
        builder = builder.setDictionaries(dictionaries);

        // Create data set
        WordsDataSet dataSet = builder.build();

        // Check its data
        System.out.println("Total words in the data set: " + dataSet.getSizeTotal());
        System.out.println("Training words in the data set: " + dataSet.getSizeTraining());
        System.out.println("Testing words in the data set: " + dataSet.getSizeTesting());
    }
}