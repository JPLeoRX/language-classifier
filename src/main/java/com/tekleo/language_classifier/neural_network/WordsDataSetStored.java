package com.tekleo.language_classifier.neural_network;

import com.tekleo.language_classifier.dictionaries.Dictionary;

import java.io.*;
import java.util.List;

public class WordsDataSetStored implements Serializable, Cloneable {
    private double ratio;                                               // Ration between training and testing data sets (from 0 to 1)
    private int batchSize;                                              // Batch size
    private List<Dictionary> dictionaries;                              // All dictionaries, each dictionary will be sliced equaly for training and testing

    public WordsDataSetStored(double ratio, int batchSize, List<Dictionary> dictionaries) {
        this.ratio = ratio;
        this.batchSize = batchSize;
        this.dictionaries = dictionaries;
    }

    public WordsDataSetStored(WordsDataSet wordsDataSet) {
        this(wordsDataSet.getRatio(), wordsDataSet.getBatchSize(), wordsDataSet.getDictionaries());
    }

    public WordsDataSet build() {
        return new WordsDataSet(ratio, batchSize, dictionaries);
    }

    public void writeToFile(String filepath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static WordsDataSetStored readFromFile(String filepath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            WordsDataSetStored wordsDataSetStored = (WordsDataSetStored) objectInputStream.readObject();
            return wordsDataSetStored;
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
