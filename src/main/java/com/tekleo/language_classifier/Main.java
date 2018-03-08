package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.dictionaries.Language;
import com.tekleo.language_classifier.neural_network.WordsDataSet;
import com.tekleo.language_classifier.neural_network.WordsDataSetBuilder;

public class Main {
    public static void main(String[] args) {
        // Load dictionaries
        Dictionary dictionaryEnglish = new Dictionary("english.txt", Language.ENGLISH);
        Dictionary dictionaryGerman = new Dictionary("german.txt", Language.GERMAN);

        // Create data set
        WordsDataSet dataSet = new WordsDataSetBuilder().setRatio(0.8).setBatchSize(10).addDictionary(dictionaryEnglish).addDictionary(dictionaryGerman).build();

        // Check its data
        System.out.println("Total words in the data set: " + dataSet.getSizeTotal());
        System.out.println("Training words in the data set: " + dataSet.getSizeTraining());
        System.out.println("Testing words in the data set: " + dataSet.getSizeTesting());
    }
}