package com.tekleo.language_classifier.neural_network;

import com.tekleo.language_classifier.ExampleCreateNetwork;
import com.tekleo.language_classifier.dictionaries.LanguageEvaluator;
import com.tekleo.language_classifier.dictionaries.Word;
import com.tekleo.language_classifier.dictionaries.WordEvaluator;
import com.tekleo.language_classifier.utils.NDArrayUtils;
import org.deeplearning4j.datasets.iterator.DoublesDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.scorecalc.DataSetLossCalculator;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
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

import java.util.concurrent.TimeUnit;

public class WordsNeuralNetwork {
    // Inputs
    private DoublesDataSetIterator trainingSet;
    private DoublesDataSetIterator testingSet;

    // Settings
    private int numberOfEpochs = 40;
    private int inputSize = 50;
    private int middleLayer1 = 100;
    private int middleLayer2 = 200;
    private int middleLayer3 = 300;
    private int middleLayer4 = 400;
    private int middleLayer5 = 500;
    private int middleLayer6 = 600;
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
                .learningRate(1e-4)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.RELU)
                .updater(new Nesterovs(0.9))
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize)
                        .nOut(middleLayer1)
                        .activation(Activation.TANH)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(middleLayer1)
                        .nOut(middleLayer2)
                        .activation(Activation.TANH)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nIn(middleLayer2)
                        .nOut(middleLayer3)
                        .activation(Activation.TANH)
                        .build())
                .layer(3, new DenseLayer.Builder()
                        .nIn(middleLayer3)
                        .nOut(middleLayer4)
                        .activation(Activation.TANH)
                        .build())
                .layer(4, new DenseLayer.Builder()
                        .nIn(middleLayer4)
                        .nOut(middleLayer5)
                        .activation(Activation.TANH)
                        .build())
                .layer(5, new DenseLayer.Builder()
                        .nIn(middleLayer5)
                        .nOut(middleLayer6)
                        .activation(Activation.TANH)
                        .build())
                .layer(6, new OutputLayer.Builder()
                        .lossFunction(LossFunctions.LossFunction.MCXENT)
                        .nIn(middleLayer6)
                        .nOut(outputSize)
                        .activation(Activation.SOFTMAX)
                        .build())
                .pretrain(false)
                .backprop(true)
                //.setInputType(InputType.feedForward(inputSize))
                .build();

        /*
         * For classification problems, you generally want to use the softmax activation function, combined with the negative log likelihood / MCXENT (multi-class cross entropy). The softmax activation function gives you a probability distribution over classes (i.e., outputs sum to 1.0).
         * For regression problems, the “identity” activation function is frequently a good choice, in conjunction with the MSE (mean squared error) loss function.
         */
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

    public void runEarlyStopTraining() {
        EarlyStoppingConfiguration<MultiLayerNetwork> esConf = new EarlyStoppingConfiguration.Builder<MultiLayerNetwork>()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(numberOfEpochs))
                .iterationTerminationConditions(new MaxTimeIterationTerminationCondition(1, TimeUnit.HOURS))
                .scoreCalculator(new DataSetLossCalculator(testingSet, true))
                .evaluateEveryNEpochs(1)
                .modelSaver(new LocalFileModelSaver("D:\\Dropbox\\TekLeo\\Language Classifier\\networks"))
                .build();

        EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, network, trainingSet);

        //Conduct early stopping training:
        EarlyStoppingResult<MultiLayerNetwork> result = trainer.fit();

        //Print out the results:
        System.out.println("Termination reason: " + result.getTerminationReason());
        System.out.println("Termination details: " + result.getTerminationDetails());
        System.out.println("Total epochs: " + result.getTotalEpochs());
        System.out.println("Best epoch number: " + result.getBestModelEpoch());
        System.out.println("Score at best epoch: " + result.getBestModelScore());

        //Get the best model:
        MultiLayerNetwork bestModel = result.getBestModel();
        this.network = bestModel;
    }

    public void test() {
        testingSet.reset();

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