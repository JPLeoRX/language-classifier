package com.tekleo.language_classifier.neural_network;

import org.deeplearning4j.datasets.iterator.DoublesDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class WordsNeuralNetwork {
    // Inputs
    DoublesDataSetIterator trainingSet;
    DoublesDataSetIterator testingSet;

    // Settings
    int numberOfEpochs = 5;
    int inputSize = 50;
    int middleLayer = 100;
    int outputSize = 37;
    int networkSeed = 666;
    int networkIterations = 1;
    double networkLearningRate = 0.006;
    double networkMomentum = 0.9;
    OptimizationAlgorithm networkOptAlgo = OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT;
    Updater networkUpdater = Updater.SGD;
    WeightInit networkWeightInit = WeightInit.XAVIER;
    Activation inputActiv = Activation.SIGMOID;
    Activation middleActiv = Activation.SIGMOID;
    Activation outputActiv = Activation.SIGMOID;
    LossFunctions.LossFunction outputLossFunction = LossFunctions.LossFunction.MEAN_ABSOLUTE_PERCENTAGE_ERROR;

    // Generated
    MultiLayerConfiguration config;
    MultiLayerNetwork network;
    Evaluation evaluation = new Evaluation();

    public WordsNeuralNetwork(DoublesDataSetIterator trainingSet, DoublesDataSetIterator testingSet) {
        this.trainingSet = trainingSet;
        this.testingSet = testingSet;

        this.initConfig();
        this.initNetwork();
    }

    private void initConfig() {
        config = new NeuralNetConfiguration.Builder()
                .seed(networkSeed)
                .iterations(networkIterations)
                .learningRate(networkLearningRate)
                .momentum(networkMomentum)
                .optimizationAlgo(networkOptAlgo)
                .updater(networkUpdater)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(middleLayer)
                        .activation(inputActiv)
                        .weightInit(networkWeightInit)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(middleLayer)
                        .nOut(middleLayer)
                        .activation(middleActiv)
                        .weightInit(networkWeightInit)
                        .build())
                .layer(2, new OutputLayer.Builder()
                        .lossFunction(outputLossFunction)
                        .nIn(middleLayer)
                        .nOut(outputSize)
                        .activation(outputActiv)
                        .weightInit(networkWeightInit)
                        .build())
                .pretrain(false)
                .backprop(true)
                .setInputType(InputType.feedForward(inputSize))
                .build();
    }

    private void initNetwork() {
        network = new MultiLayerNetwork(config);
    }

    public void train() {
        for (int i = 0; i < numberOfEpochs; i++) {
            System.out.println("Epoch " + i);
            network.fit(trainingSet);
        }
    }

    public void test() {
        while (testingSet.hasNext()) {
            DataSet next = testingSet.next();
            INDArray output = network.output(next.getFeatureMatrix());
            evaluation.eval(next.getLabels(), output);
        }

        System.out.println(evaluation.stats());
    }
}
