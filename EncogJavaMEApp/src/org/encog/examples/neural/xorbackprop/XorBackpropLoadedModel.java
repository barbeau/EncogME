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

import java.io.IOException;
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

/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses backpropagation to train the neural network.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class XorBackpropLoadedModel implements Runnable {

    //Reference to main MIDlet
    NeuralNetDemo midlet;

    //Variable to break the training loop if the thread is deactivated
    boolean active = true;
   
    public static double XOR_INPUT[][] = {{0.0, 0.0}, {1.0, 0.0},
        {0.0, 1.0}, {1.0, 1.0}};
    public static double XOR_IDEAL[][] = {{0.0}, {1.0}, {1.0}, {0.0}};
    //WebToMobileClient that will submit results to server
    WebToMobileClient wsClient;
    //  public static String url = "http://131.247.19.91:8080/wsEncogResults/servlet/org.encog.util.db.javaMETests.ws.servlets.WebToMobileServlet";
    public static String url = "http://localhost:8080/wsEncogResults/servlet/org.encog.util.db.javaMETests.ws.servlets.WebToMobileServlet";

    public XorBackpropLoadedModel(NeuralNetDemo midlet) {
        this.midlet = midlet;
        wsClient = new WebToMobileClient(url);
    }

    public boolean isActive() {
        return active;
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

            BasicNetwork network = new BasicNetwork();
            network.addLayer(new FeedforwardLayer(2));
            network.addLayer(new FeedforwardLayer(3));
            network.addLayer(new FeedforwardLayer(1));
            network.reset();
            System.out.println("Initialized structure of neural network.");
            NeuralDataSet trainingSet = new BasicNeuralDataSet(XOR_INPUT, XOR_IDEAL);

            Double[] weightsThresholds = null;
            int id = 2;  //ID for record of model that will be pulled back from database
                     
            try {
                System.out.println("Retrieving weights and thresholds from the server...");
                //ID for record of model that will be pulled back from database

                //  travis:  updated following line to give weightsThresholds a val.  was crashing later with null since not assigned a value
                //  wsClient.getModel(id);
                weightsThresholds = wsClient.getModel(id);
                System.out.println("Successfully retrieved weights and thresholds from the server:");
            } catch (IOException ex) {
                System.out.println("Error loading weights and thresholds from the server:" + ex);
                ex.printStackTrace();
            }            
            
            //  print weights and thresholds from converted XML
            int i = 0;
            for(i=0;i<weightsThresholds.length;i++)
            {
            System.out.println(weightsThresholds[i].toString());
            }

            System.out.println("Loading weights and thresholds into the local neural network...");
            network.loadWeightsThresholds(weightsThresholds);
            System.out.println("Finished loading weights and thresholds into the local neural network.");

            // ?
            // network.reset();

            // test the neural network
            System.out.println("Neural Network Results:");
            midlet.updateGUI("Neural Network Results:");

            long startTime;
            long endTime;
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

                midlet.updateGUI("----------------");

            }          
         
        System.out.println("Testing finished.");
        midlet.updateGUI("Testing finished.");
    }


}
