/*
 * Encog Neural Network and Bot Library for Java
 * http://www.heatonresearch.com/encog/
 * http://code.google.com/p/encog-java/
 *
 * Copyright 2008, Heaton Research Inc., and individual contributors.
 * See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.encog.examples.neural.modelgen;

import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.util.db.javaMETests.TestResultManager;
import org.encog.util.db.javaMETests.TestResult;

/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses backpropagation to train the neural network.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ModelGenerator {

    public static double XOR_INPUT[][] = {{0.0, 0.0}, {1.0, 0.0},
        {0.0, 1.0}, {1.0, 1.0}};
    public static double XOR_IDEAL[][] = {{0.0}, {1.0}, {1.0}, {0.0}};
    public static int numTests = 1;  //Varible to hold the number of tests to run
    public static int maxEpochs = 500;  //Variable to hold max epochs value
    public static double minStopError = 0.001;  //Variable to hold min Stop Error value
    public static double learningRate = 0.7;  //Variable to hold the learning rate
    public static double momentum = 0.9;  //Variable to hold the momentum

    public static void main(final String args[]) {

        //Parse input arguments
        try {
            numTests = Integer.parseInt(args[0]);
            System.out.println("Running " + numTests + " test(s)...");
            maxEpochs = Integer.parseInt(args[1]);
            System.out.println(maxEpochs + " maximum epochs...");
            minStopError = Double.parseDouble(args[2]);
            System.out.println(minStopError + " is minimum stopping error...");
        } catch (Exception e) {
            System.out.println("Error parsing arguments: " + e);
            System.out.println("Arguments format is: <number of Tests> <Max Epochs> <Minimum Stopping Error>");
            System.exit(1);
        }

        //Initiate objects to save test results
        TestResult result;
        TestResultManager resultsManager = new TestResultManager(false);
        //Vector allResults = new Vector();

        int counter = 0;

        while (counter < numTests) {

            BasicNetwork network = new BasicNetwork();
            network.addLayer(new FeedforwardLayer(2));
            network.addLayer(new FeedforwardLayer(3));
            network.addLayer(new FeedforwardLayer(1));
            network.reset();

            result = new TestResult();
            result.setPlatform(TestResult.PLATFORM_DESKTOP);
            result.setNumEpochs(maxEpochs);
            result.setMinStopError(minStopError);
            result.setMathPOWSolution(3);
            result.setLearningRate(learningRate);
            result.setMomemtum(momentum);

            NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);

            // train the neural network
            Train train = new Backpropagation(network, trainingSet,
                    learningRate, momentum);

            int epoch = 1;

            long startTime = System.currentTimeMillis();

            do {
                train.iteration();
                System.out.println("Epoch #" + epoch + " Error:" + train.getError());
                epoch++;
            } while ((epoch < maxEpochs) && (train.getError() > minStopError));

            result.setNumEpochs(epoch - 1);
            result.setFinalTrainError(train.getError());

            long endTime = System.currentTimeMillis();
            long trainingTime = endTime - startTime;
            System.out.println("Training time (ms): " + trainingTime);
            result.setTrainingTime(trainingTime);

            long testTime;
            // test the neural network
            System.out.println("Neural Network Results:");
            for (NeuralDataPair pair : trainingSet) {
                startTime = System.currentTimeMillis();
                final NeuralData output = network.compute(pair.getInput());
                endTime = System.currentTimeMillis();
                testTime = endTime - startTime;
                System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
                System.out.println("Test time (ms): " + testTime);
                if (pair.getInput().getData(0) == 0 && pair.getInput().getData(1) == 0) {
                    //0,0                   
                    result.setTest00Actual(output.getData(0));
                    result.setTest00Error(output.getData(0));
                    result.setTest00Time(testTime);
                } else {
                    if (pair.getInput().getData(0) == 1 && pair.getInput().getData(1) == 0) {
                        //1,0
                        result.setTest10Actual(output.getData(0));
                        result.setTest10Error(1 - output.getData(0));
                        result.setTest10Time(testTime);
                    } else {
                        if (pair.getInput().getData(0) == 0 && pair.getInput().getData(1) == 1) {
                            //0,1
                            result.setTest01Actual(output.getData(0));
                            result.setTest01Error(1 - output.getData(0));
                            result.setTest01Time(testTime);
                        } else {
                            if (pair.getInput().getData(0) == 1 && pair.getInput().getData(1) == 1) {
                                //1,1
                                result.setTest11Actual(output.getData(0));
                                result.setTest11Error(output.getData(0));
                                result.setTest11Time(testTime);

                            }
                        }
                    }
                }
            }
            //allResults.add(result);  //Add results to vector of results
            counter++;

            //Training Times seem to be skewed when saving all results to a vector and then dumping at once,
            //so instead insert one result at a time
            try {
                resultsManager.saveTestResults(result);
            } catch (Exception ex) {
                System.out.println("Error saving results to database: " + ex);
            }

            try {
                //TODO - save generated XMLPersistance stuff so XML file ends up in SQL server database
                resultsManager.savedModelToDatabase(network);
            } catch (Exception ex) {
                System.out.println("Error saving network information to database: " + ex);
            }

            //Deallocate all variables to force garbage collection before next loop
            network = null;
            result = null;
            trainingSet = null;
            train = null;

            System.gc();  //Ask VM to run garbage collection (tries to prevent garbage collection from running during time benchmarks)

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println("Error sleeping: " + ex);
            }
        }

    //This code dumps all results at once in form of a vector - not used because of skewed results in training time
//        try {
//            TestResult[] tempResults = new TestResult[allResults.size()];
//            allResults.copyInto(tempResults);
//            resultsManager.saveTestResults(tempResults);
//        } catch (Exception ex) {
//            System.out.println("Error saving results to database: " + ex);
//        }
    }
}
