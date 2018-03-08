package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.DictionariesLoader;
import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.dictionaries.Language;
import com.tekleo.language_classifier.neural_network.WordsDataSet;
import com.tekleo.language_classifier.neural_network.WordsDataSetBuilder;
import com.tekleo.language_classifier.utils.FileUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        System.out.println(FileUtils.getLengthOfLongestWord("Ukrainian.dic"));
//        // Load dictionaries
//        List<Dictionary> dictionaries = DictionariesLoader.load();
//
//        // Create data set builder
//        WordsDataSetBuilder builder = new WordsDataSetBuilder();
//        builder = builder.setRatio(0.8);
//        builder = builder.setBatchSize(10);
//        builder = builder.setDictionaries(dictionaries);
//
//        // Create data set
//        WordsDataSet dataSet = builder.build();
//
//        // Check its data
//        // 1185534
//        System.out.println("Total words in the data set: " + dataSet.getSizeTotal());
//        System.out.println("Training words in the data set: " + dataSet.getSizeTraining());
//        System.out.println("Testing words in the data set: " + dataSet.getSizeTesting());
    }
}