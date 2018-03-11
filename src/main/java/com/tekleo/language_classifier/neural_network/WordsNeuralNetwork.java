package com.tekleo.language_classifier.neural_network;

import com.tekleo.language_classifier.ExampleCreateNetwork;
import com.tekleo.language_classifier.dictionaries.LanguageEvaluator;
import com.tekleo.language_classifier.dictionaries.Word;
import com.tekleo.language_classifier.dictionaries.WordEvaluator;
import com.tekleo.language_classifier.utils.NDArrayUtils;
import org.deeplearning4j.datasets.iterator.DoublesDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.ui.stats.StatsListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class WordsNeuralNetwork {
    // Inputs
    private DoublesDataSetIterator trainingSet;
    private DoublesDataSetIterator testingSet;

    // Settings
    private int numberOfEpochs = 100;
    private int inputSize = 50;
    private int middleLayer1 = 100;
    private int outputSize = 6;

    // Generated
    private MultiLayerConfiguration config;
    private MultiLayerNetwork network;
    private Evaluation evaluation;

    public WordsNeuralNetwork(DoublesDataSetIterator trainingSet, DoublesDataSetIterator testingSet) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;

        this.initConfig();
        this.initNetwork();
        this.initEvaluation();
    }

    private void initConfig() {
        config = new NeuralNetConfiguration.Builder()
                .seed(666)
                .iterations(1)
                .learningRate(0.01)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(0.9))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(middleLayer1)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(middleLayer1)
                        .nOut(outputSize)
                        .activation(Activation.SOFTMAX)
                        .build())
                .pretrain(false)
                .backprop(true)
                //.setInputType(InputType.feedForward(inputSize))
                .build();
    }

    private void initNetwork() {
        // Build network from config
        network = new MultiLayerNetwork(config);

        // Call network's internal initialize
        network.init();

        // Hook it to UI listener
        network.setListeners(new StatsListener(ExampleCreateNetwork.statsStorage));
    }

    private void initEvaluation() {
        evaluation = new Evaluation(outputSize);
    }

    public void train() {
        // For each epoch
        for (int i = 0; i < numberOfEpochs; i++) {
            System.out.println("Epoch " + i);
            network.fit(trainingSet);
        }
    }

    public void test() {
        while (testingSet.hasNext()) {
            // Get nex data
            DataSet next = testingSet.next();

            // Get input/output
            INDArray input = next.getFeatureMatrix();
            INDArray output = next.getLabels();

            // Compute network's result
            INDArray networkResult = network.output(input);

            // Convert back to meaningful words and print the results
            WordEvaluator wordInput = new WordEvaluator(NDArrayUtils.rowToArray(input));
            LanguageEvaluator languageExpected = new LanguageEvaluator(NDArrayUtils.rowToArray(output));
            LanguageEvaluator languageActual = new LanguageEvaluator(NDArrayUtils.rowToArray(networkResult));
            System.out.println("Word: '" + wordInput.getWord() + "' with expected languages: " + languageExpected.getBest() + " and actual: " + languageActual.getBest3());

            // Add this test case to total evaluation
            evaluation.eval(output, networkResult);
        }

        // Print total evaluation
        System.out.println(evaluation.stats());
    }

    public LanguageEvaluator compute(String word) {
        INDArray networkInput = Nd4j.create(Word.getAsDoubles(word));
        INDArray networkOutput = network.output(networkInput);
        return new LanguageEvaluator(NDArrayUtils.rowToArray(networkOutput));
    }
}