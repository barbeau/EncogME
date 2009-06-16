/*
 * Encog Neural Network and Bot Library for Java v1.x
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
package org.encog.neural.networks.training.backpropagation;

import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Hashtable;  //sjb - used in place for HashMap for Java ME

import org.encog.neural.NeuralNetworkError;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Layer;
import org.encog.neural.networks.Network;
import org.encog.neural.networks.Train;
import org.encog.neural.networks.layers.FeedforwardLayer;

/**
 * Backpropagation: This class implements a backpropagation training algorithm
 * for feed forward neural networks. It is used in the same manner as any other
 * training class that implements the Train interface.
 * 
 * Backpropagation is a common neural network training algorithm. It works by
 * analyzing the error of the output of the neural network. Each neuron in the
 * output layer's contribution, according to weight, to this error is
 * determined. These weights are then adjusted to minimize this error. This
 * process continues working its way backwards through the layers of the neural
 * network.
 * 
 * This implementation of the backpropagation algorithm uses both momentum and a
 * learning rate. The learning rate specifies the degree to which the weight
 * matrixes will be modified through each iteration. The momentum specifies how
 * much the previous learning iteration affects the current. To use no momentum
 * at all specify zero.
 */
public class Backpropagation implements Train {
	/**
	 * The error from the last iteration.
	 */
	private double error;

	/**
	 * The learning rate. This is the degree to which the deltas will affect the
	 * current network.
	 */
	private final double learnRate;

	/**
	 * The momentum, this is the degree to which the previous training cycle
	 * affects the current one.
	 */
	private final double momentum;

	/**
	 * THe network that is being trained.
	 */
	private final BasicNetwork network;

	/**
	 * A map between neural network layers and the corresponding
	 * BackpropagationLayer.
	 */
	//private final Map<Layer, BackpropagationLayer> layerMap = new HashMap<Layer, BackpropagationLayer>();
    private final Hashtable layerMap = new Hashtable();  //sjb - removed Generics, Changed to Hashtable for Java ME

	/**
	 * The training data to use.
	 */
	private final NeuralDataSet training;

	/**
	 * Construct a backpropagation trainer.
	 * @param network The network to train.
	 * @param training The training data to use.
	 * @param learnRate
	 *            The rate at which the weight matrix will be adjusted based on
	 *            learning.
	 * @param momentum
	 *            The influence that previous iteration's training deltas will
	 *            have on the current iteration.
	 */
	public Backpropagation(final BasicNetwork network,
			final NeuralDataSet training, final double learnRate,
			final double momentum) {
		this.network = network;
		this.learnRate = learnRate;
		this.momentum = momentum;
		this.training = training;

		//for (final Layer layer : network.getLayers()) {
        Enumeration e = network.getLayers().elements();  //sjb - changed to support Vectors
        while(e.hasMoreElements()){  //sjb - changed to support Vectors
            Layer layer = (Layer) e.nextElement();  //sjb - changed to support Vectors, cast Object
			if (layer instanceof FeedforwardLayer) {
				final BackpropagationLayer bpl = new BackpropagationLayer(this,
						(FeedforwardLayer) layer);
				this.layerMap.put(layer, bpl);
			}

		}
	}

	/**
	 * Calculate the error for the recognition just done.
	 * 
	 * @param ideal
	 *            What the output neurons should have yielded.
	 */
	public void calcError(final NeuralData ideal) {

		if (ideal.size() != this.network.getOutputLayer().getNeuronCount()) {
			throw new NeuralNetworkError(
					"Size mismatch: Can't calcError for ideal input size="
							+ ideal.size() + " for output layer size="
							+ this.network.getOutputLayer().getNeuronCount());
		}

		// clear out all previous error data
		//for (final Layer layer : this.network.getLayers()) {
        Enumeration e = this.network.getLayers().elements();  //sjb - changed to support Vectors
        while(e.hasMoreElements()){  //sjb - changed to support Vectors
            Layer layer = (Layer) e.nextElement();  //sjb - changed to support Vectors, cast Object
			getBackpropagationLayer(layer).clearError();
		}

		for (int i = this.network.getLayers().size() - 1; i >= 0; i--) {
			//final Layer layer = this.network.getLayers().get(i);
            final Layer layer = (Layer) this.network.getLayers().elementAt(i); //sjb - changed to Vector methods, cast object

			if (layer instanceof FeedforwardLayer) {

				if (layer.isOutput()) {

					getBackpropagationLayer(layer).calcError(ideal);
				} else {
					getBackpropagationLayer(layer).calcError();
				}
			}
		}
	}

	/**
	 * Get the BackpropagationLayer that corresponds to the specified layer.
	 * 
	 * @param layer
	 *            The specified layer.
	 * @return The BackpropagationLayer that corresponds to the specified layer.
	 */
	public BackpropagationLayer getBackpropagationLayer(final Layer layer) {
		//final BackpropagationLayer result = this.layerMap.get(layer);
        final BackpropagationLayer result = (BackpropagationLayer) this.layerMap.get(layer); //sjb - cast object

		if (result == null) {
			throw new NeuralNetworkError(
					"Layer unknown to backpropagation trainer, "
					+ "was a layer added after training begain?");
		}

		return result;
	}

	/**
	 * Returns the root mean square error for a complete training set.
	 * 
	 * @return The current error for the neural network.
	 */
	public double getError() {
		return this.error;
	}

	/**
	 * Get the current best neural network.
	 * 
	 * @return The current best neural network.
	 */
	//public BasicNetwork getNetwork() {
    public Network getNetwork() {
		return this.network;
	}

	/**
	 * Perform one iteration of training.
	 */
	public void iteration() {

		//for (final NeuralDataPair pair : this.training) {
        BasicNeuralDataSet.BasicNeuralIterator iterator = ((BasicNeuralDataSet)this.training).iterator();  //sjb - Removed FOR loop format with new iteration format
        while (iterator.hasNext()){  //sjb - new iteration
            NeuralDataPair pair = iterator.next(); //sjb - new iteration
			this.network.compute(pair.getInput());
			calcError(pair.getIdeal());
		}
		learn();

		this.error = this.network.calculateError(this.training);
	}

	/**
	 * Modify the weight matrix and thresholds based on the last call to
	 * calcError.
	 */
	public void learn() {

		//for (final Layer layer : this.network.getLayers()) {
        Enumeration e = this.network.getLayers().elements();  //sjb - changed FOR loop format for iterator implementation
        while(e.hasMoreElements()){  //sjb - changed FOR loop format for iterator implementation
            Layer layer = (Layer) e.nextElement();  //sjb - changed FOR loop format for iterator implementation
			if (layer instanceof FeedforwardLayer) {
				getBackpropagationLayer(layer).learn(this.learnRate,
						this.momentum);
			}
		}

	}
}
