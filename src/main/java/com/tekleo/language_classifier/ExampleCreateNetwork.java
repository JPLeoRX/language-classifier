package com.tekleo.language_classifier;

import com.tekleo.language_classifier.dictionaries.DictionariesLoader;
import com.tekleo.language_classifier.dictionaries.Dictionary;
import com.tekleo.language_classifier.neural_network.WordsDataSet;
import com.tekleo.language_classifier.neural_network.WordsDataSetBuilder;
import com.tekleo.language_classifier.neural_network.WordsNeuralNetwork;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.nd4j.jita.conf.CudaEnvironment;

import java.util.List;

public class ExampleCreateNetwork {
    public static StatsStorage statsStorage;

    public static void initUI() {
        //Initialize the user interface backend
        UIServer uiServer = UIServer.getInstance();

        //Configure where the network information (gradients, score vs. time etc) is to be stored. Here: store in memory.
        statsStorage = new InMemoryStatsStorage();         //Alternative: new FileStatsStorage(File), for saving and loading later

        //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
        uiServer.attach(statsStorage);
    }

    /**
     * An Epoch is a complete pass through all the training data.
     * A neural network is trained until the error rate is acceptable, and this will often take multiple passes through the complete data set.
     *
     * note
     * An iteration is when parameters are updated and is typically less than a full pass.
     * For example if BatchSize is 100 and data size is 1,000 an epoch will have 10 iterations.
     * If trained for 30 epochs there will be 300 iteration
     */
    public static void main(String[] args) {
        CudaEnvironment.getInstance().getConfiguration()
                .setMaximumGridSize(512)
                .setMaximumBlockSize(512);

        CudaEnvironment.getInstance().getConfiguration()
                .setMaximumDeviceCacheableLength(1024 * 1024 * 1024L)
                .setMaximumDeviceCache(3L * 1024 * 1024 * 1024L)
                .setMaximumHostCacheableLength(1024 * 1024 * 1024L)
                .setMaximumHostCache(3L * 1024 * 1024 * 1024L);

        initUI();

        // Load all dictionaries
        List<Dictionary> dictionaries = DictionariesLoader.load();

        // Create data set builder
        WordsDataSetBuilder builder = new WordsDataSetBuilder();
        builder = builder.setRatio(0.8);
        builder = builder.setBatchSize(10000);
        builder = builder.setDictionaries(dictionaries);

        // Create data set
        WordsDataSet dataSet = builder.build();

        // Create neural network
        WordsNeuralNetwork network = new WordsNeuralNetwork(dataSet.getIteratorTraining(), dataSet.getIteratorTesting());
        network.runEarlyStopTraining();
        network.test();
    }
}
