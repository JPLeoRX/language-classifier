package com.tekleo.language_classifier.neural_network;

import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.dictionaries.Word;
import com.tekleo.language_classifier.utils.ListUtils;
import org.deeplearning4j.datasets.iterator.DoublesDataSetIterator;
import org.nd4j.linalg.primitives.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a basic data set that takes in a list of dictionaries and parses them to machine learning format
 * First we split each dictionary in two parts - training and testing
 * Then we convert them to pairs of input-output
 * Finally we can generate {@link DoublesDataSetIterator} from them
 * @author Leo Ertuna
 * @since 08.03.2018 15:31
 */
public class WordsDataSet implements Serializable{
    // Inputs
    private double ratio;                                               // Ration between training and testing data sets (from 0 to 1)
    private int batchSize;                                              // Batch size
    private List<Dictionary> dictionaries;                              // All dictionaries, each dictionary will be sliced equaly for training and testing

    // Outputs
    private int sizeTotal;                                              // Total number of words, training + testing
    private int sizeTraining;                                           // Number of words in the training data set
    private int sizeTesting;                                            // Number of words in the testing data set
    private List<Word> wordsTraining;                                   // Training data set with words
    private List<Word> wordsTesting;                                    // Testing data set with words
    private List<Pair<double[], double[]>> pairsTraining;               // Training data set converted to doubles
    private List<Pair<double[], double[]>> pairsTesting;                // Testing data set converted to doubles
    private DoublesDataSetIterator iteratorTraining;                    // Training data set iterator
    private DoublesDataSetIterator iteratorTesting;                     // Testing data set iterator

    // Constructors
    //------------------------------------------------------------------------------------------------------------------
    public WordsDataSet(double ratio, int batchSize, List<Dictionary> dictionaries) {
        this.ratio = ratio;
        this.batchSize = batchSize;
        this.dictionaries = dictionaries;

        this.initWords();
        this.initPairsTraining();
        this.initPairsTesting();
        this.initSizes();
        this.initIteratorTraining();
        this.initIteratorTesting();
    }

    public WordsDataSet(double ratio, int batchSize, Dictionary ... dictionaries) {
        this(ratio, batchSize, ListUtils.toArrayList(dictionaries));
    }
    //------------------------------------------------------------------------------------------------------------------



    // Initialization
    //------------------------------------------------------------------------------------------------------------------
    private void initWords() {
        // Check for legal input
        if (dictionaries == null || dictionaries.isEmpty())
            throw new IllegalArgumentException("Dictionaries are null or empty");

        // Initialize lists
        wordsTraining = new LinkedList<>();
        wordsTesting = new LinkedList<>();

        // For each dictionary
        for (Dictionary dictionary : dictionaries) {
            // Slice the words in training and testing
            Pair<List<Word>, List<Word>> sliced = ListUtils.split(dictionary.getWords(), ratio);

            // Save words
            wordsTraining.addAll(sliced.getKey());
            wordsTesting.addAll(sliced.getValue());
        }
    }

    private void initPairsTraining() {
        // Check for legal input
        if (wordsTraining == null || wordsTraining.isEmpty())
            throw new IllegalArgumentException("Words for training are null or empty");

        // Get pairs of doubles
        pairsTraining = Word.getPairs(wordsTraining);
    }

    private void initPairsTesting() {
        // Check for legal input
        if (wordsTesting == null || wordsTesting.isEmpty())
            throw new IllegalArgumentException("Words for testing are null or empty");

        // Get pairs of doubles
        pairsTesting = Word.getPairs(wordsTraining);
    }

    private void initSizes() {
        sizeTraining = wordsTraining.size();
        sizeTesting = wordsTesting.size();
        sizeTotal = sizeTraining + sizeTesting;
    }

    private void initIteratorTraining() {
        // Check for legal input
        if (pairsTraining == null || pairsTraining.isEmpty())
            throw new IllegalArgumentException("Pairs for training are null or empty");

        iteratorTraining = new DoublesDataSetIterator(pairsTraining, batchSize);
    }

    private void initIteratorTesting() {
        // Check for legal input
        if (pairsTesting == null || pairsTesting.isEmpty())
            throw new IllegalArgumentException("Pairs for testing are null or empty");

        iteratorTesting = new DoublesDataSetIterator(pairsTesting, batchSize);
    }
    //------------------------------------------------------------------------------------------------------------------



    // Getters
    //------------------------------------------------------------------------------------------------------------------
    public double getRatio() {
        return ratio;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public int getSizeTotal() {
        return sizeTotal;
    }

    public int getSizeTraining() {
        return sizeTraining;
    }

    public int getSizeTesting() {
        return sizeTesting;
    }

    public List<Word> getWordsTraining() {
        return wordsTraining;
    }

    public List<Word> getWordsTesting() {
        return wordsTesting;
    }

    public List<Pair<double[], double[]>> getPairsTraining() {
        return pairsTraining;
    }

    public List<Pair<double[], double[]>> getPairsTesting() {
        return pairsTesting;
    }

    public DoublesDataSetIterator getIteratorTraining() {
        return iteratorTraining;
    }

    public DoublesDataSetIterator getIteratorTesting() {
        return iteratorTesting;
    }
    //------------------------------------------------------------------------------------------------------------------
}