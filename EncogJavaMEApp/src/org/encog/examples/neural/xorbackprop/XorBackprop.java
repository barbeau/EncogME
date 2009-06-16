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
package org.encog.examples.neural.xorbackprop;

import javaMEDemo.NeuralNetDemo;
import javaMEDemo.wc.WebToMobileClient;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.networks.training.backpropagation.Backpropagation;
import org.encog.util.db.javaMETestsServer.TestResult;


/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses backpropagation to train the neural network.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class XorBackprop implements Runnable {

    //Reference to main MIDlet
    NeuralNetDemo midlet;

    //Variable to break the training loop if the thread is deactivated
    boolean active = true;

    //Max number of Epochs to train for
    int MAX_EPOCHS = 500;

    //Minimum amount of error to train the neural network to
    double MIN_ERROR = 0.001;
    public int numTests = 1;  //Varible to hold the number of tests to run
    public double learningRate = 0.7;  //Variable to hold the learning rate
    public double momentum = 0.9;  //Variable to hold the momentum
    public long timeBetweenTests;  //Varible that holds the time between tests, in milliseconds

    public static double XOR_INPUT[][] = {{0.0, 0.0}, {1.0, 0.0},
        {0.0, 1.0}, {1.0, 1.0}};
    public static double XOR_IDEAL[][] = {{0.0}, {1.0}, {1.0}, {0.0}};
    //WebToMobileClient that will submit results to server
    WebToMobileClient wsClient;
//    public static String url = "http://131.247.19.91:8080/wsEncogResults/servlet/org.encog.util.db.javaMETests.ws.servlets.WebToMobileServlet";
    public static String url = "http://localhost:8080/wsEncogResults/servlet/org.encog.util.db.javaMETests.ws.servlets.WebToMobileServlet";


    public XorBackprop(NeuralNetDemo midlet) {
        this.midlet = midlet;
        wsClient = new WebToMobileClient(url);        
    }

    public boolean isActive() {
        return active;
    }

    public int getMAX_EPOCHS() {
        return MAX_EPOCHS;
    }

    public void setMAX_EPOCHS(int MAX_EPOCHS) {
        this.MAX_EPOCHS = MAX_EPOCHS;
    }

    public double getMIN_ERROR() {
        return MIN_ERROR;
    }

    public void setMIN_ERROR(double MIN_ERROR) {
        this.MIN_ERROR = MIN_ERROR;
    }

    public int getNumTests() {
        return this.numTests;
    }

    public void setNumTests(int numTests) {
        this.numTests = numTests;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getLearningRate() {
        return this.learningRate;
    }

    public void setTimeBetweenTests(long timeBetweenTests) {
        this.timeBetweenTests = timeBetweenTests;
    }

    public long getTimeBetweenTests() {
        return this.timeBetweenTests;
    }



    public void startExample() {
        this.active = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stopExample() {
        this.active = false;
    }

    public void run() {
        //Initiate objects to save test results
        TestResult result;

        int counter = 0;

        while ((counter < numTests) && (isActive())) {

            BasicNetwork network = new BasicNetwork();
            network.addLayer(new FeedforwardLayer(2));
            network.addLayer(new FeedforwardLayer(3));
            network.addLayer(new FeedforwardLayer(1));
            network.reset();

            result = new TestResult();
            //#if Emulator
//#             result.setPlatform(1);  //If its the emulator configuration, set the platform for emulator
            //#else
            result.setPlatform(2);  //Otherwise, set the phone platform value
            //#endif
            result.setNumEpochs(MAX_EPOCHS);
            result.setMinStopError(MIN_ERROR);
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
                if (epoch % 100 == 0) {
                    //Every 100 epochs, output to GUI
                    midlet.updateGUI("Epoch #" + epoch + " Error:" + train.getError());
                }

                epoch++;
            } while ((epoch < MAX_EPOCHS) && (train.getError() > MIN_ERROR) && (isActive()));

            result.setNumEpochs(epoch - 1);
            result.setFinalTrainError(train.getError());

            System.out.println("Final Epoch #" + (epoch - 1) + " Error:" + train.getError());
            midlet.updateGUI("Final Epoch #" + (epoch - 1) + " Error:" + train.getError());

            long endTime = System.currentTimeMillis();
            long trainingTime = endTime - startTime;
            System.out.println("Training time (ms): " + trainingTime);
            midlet.updateGUI("Training time (ms): " + trainingTime);
            result.setTrainingTime(trainingTime);

            // test the neural network
            System.out.println("Neural Network Results:");
            midlet.updateGUI("Neural Network Results:");
            //for(NeuralDataPair pair: trainingSet ) {

            long testTime;

            BasicNeuralDataSet.BasicNeuralIterator iterator = ((BasicNeuralDataSet) trainingSet).iterator();  //sjb - Removed FOR loop format with new iteration format
            while (iterator.hasNext()) {  //sjb - new iteration
                NeuralDataPair pair = iterator.next(); //sjb - new iteration
                startTime = System.currentTimeMillis();
                final NeuralData output = network.compute(pair.getInput());
                endTime = System.currentTimeMillis();
                testTime = endTime - startTime;
                System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));

                midlet.updateGUI(pair.getInput().getData(0) + "," + pair.getInput().getData(1));
                midlet.updateGUI("actual=" + output.getData(0));
                midlet.updateGUI("ideal=" + pair.getIdeal().getData(0));
                System.out.println("Test time (ms): " + testTime);
                midlet.updateGUI("Test time (ms): " + testTime);
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

                midlet.updateGUI("----------------");

            }
            
            counter++;
            //Use web client to submit result to server
            try {
                wsClient.saveResult(result);
                System.out.println("Saved result to database.");
            } catch (Exception ex) {
                System.out.println("Error saving results to database: " + ex);
            }

            //Deallocate all variables to force garbage collection before next loop
            network = null;
            result = null;
            trainingSet = null;
            train = null;

            System.gc();  //Ask VM to run garbage collection (tries to prevent garbage collection from running during time benchmarks)
            
            try {
                Thread.sleep(timeBetweenTests);
            } catch (InterruptedException ex) {
                System.out.println("Error sleeping: " + ex);
            }

        }
        System.out.println("Testing finished.");
        midlet.updateGUI("Testing finished.");
    }

//	public static void main(final String args[]) {
//		BasicNetwork network = new BasicNetwork();
//		network.addLayer(new FeedforwardLayer(2));
//		network.addLayer(new FeedforwardLayer(3));
//		network.addLayer(new FeedforwardLayer(1));
//		network.reset();
//
//		NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);
//
//		// train the neural network
//		final Train train = new Backpropagation(network, trainingSet,
//				0.7, 0.9);
//
//		int epoch = 1;
//
//		do {
//			train.iteration();
//			System.out
//					.println("Epoch #" + epoch + " Error:" + train.getError());
//			epoch++;
//		} while ((epoch < 5000) && (train.getError() > 0.001));
//
//		// test the neural network
//		System.out.println("Neural Network Results:");
//		//for(NeuralDataPair pair: trainingSet ) {
//        BasicNeuralDataSet.BasicNeuralIterator iterator = ((BasicNeuralDataSet)trainingSet).iterator();  //sjb - Removed FOR loop format with new iteration format
//        while (iterator.hasNext()){  //sjb - new iteration
//            NeuralDataPair pair = iterator.next(); //sjb - new iteration
//			final NeuralData output = network.compute(pair.getInput());
//			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
//					+ ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
//		}
//	}
}
