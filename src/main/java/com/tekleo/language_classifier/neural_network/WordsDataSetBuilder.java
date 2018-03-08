package com.tekleo.language_classifier.neural_network;

import com.tekleo.language_classifier.dictionaries.Dictionary;

import java.util.LinkedList;
import java.util.List;

/**
 * Builder for {@link WordsDataSet}
 * @author Leo Ertuna
 * @since 08.03.2018 15:42
 */
public class WordsDataSetBuilder {
    private double ratio = 0;
    private int batchSize = 0;
    private List<Dictionary> dictionaries = new LinkedList<>();

    public WordsDataSetBuilder setRatio(double ratio) {
        this.ratio = ratio;
        return this;
    }

    public WordsDataSetBuilder setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public WordsDataSetBuilder setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
        return this;
    }

    public WordsDataSetBuilder addDictionary(Dictionary dictionary) {
        dictionaries.add(dictionary);
        return this;
    }

    public WordsDataSet build() {
        return new WordsDataSet(ratio, batchSize, dictionaries);
    }
}