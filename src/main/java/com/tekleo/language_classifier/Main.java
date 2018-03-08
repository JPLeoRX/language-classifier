package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.dictionaries.Language;
import com.tekleo.language_classifier.dictionaries.Word;
import com.tekleo.language_classifier.utils.FileUtils;
import com.tekleo.language_classifier.utils.StringUtils;
import org.deeplearning4j.datasets.fetchers.BaseDataFetcher;
import org.deeplearning4j.datasets.iterator.BaseDatasetIterator;
import org.deeplearning4j.datasets.iterator.DoublesDataSetIterator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Load dictionary
        Dictionary d = new Dictionary("english.txt", Language.ENGLISH);

        // Convert it to data set for machine learning
        DoublesDataSetIterator i = new DoublesDataSetIterator(d, 10);


    }
}
