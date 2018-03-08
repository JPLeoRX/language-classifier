package com.tekleo.language_classifier.dictionaries;

import com.tekleo.language_classifier.utils.FileUtils;
import org.nd4j.linalg.primitives.Pair;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class represents a dictionary that parses a list of words and prepares them for machine learning
 * We start by reading whole file as one string
 * Then we split that string into words and shuffle them
 * Finally we parse each word and shuffle them again
 * @author Leo Erunta
 * @since 07.03.2018 23:42
 */
public class Dictionary implements Iterable<Pair<double[], double[]>> {
    // Input
    private String filepath;
    private Language language;

    // Output
    private String text;
    private List<Word> words;
    private List<Pair<double[], double[]>> pairs;

    public Dictionary(String filepath, Language language) {
        this.filepath = filepath;
        this.language = language;

        this.initText();
        this.initWords();
        this.shuffleWords();
        this.initPairs();
        this.shufflePairs();
    }

    // Initialization
    //------------------------------------------------------------------------------------------------------------------
    private void initText() {
        text = FileUtils.getFileAsString(filepath);
    }

    private void initWords() {
        words = Word.getWords(text, "\n", language);
    }

    private void shuffleWords() {
        Collections.shuffle(words, new Random(7));
    }

    private void initPairs() {
        pairs = Word.getPairs(words);
    }

    private void shufflePairs() {
        Collections.shuffle(pairs, new Random(13));
    }
    //------------------------------------------------------------------------------------------------------------------

    public List<Word> getWords() {
        return words;
    }

    @Override
    public Iterator<Pair<double[], double[]>> iterator() {
        return pairs.iterator();
    }
}
