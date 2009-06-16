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

package org.encog.util;

import org.encog.neural.data.NeuralData;

/**
 * ErrorCalculation: An implementation of root mean square (RMS) error
 * calculation. This class is used by nearly every neural network in this book
 * to calculate error.
 */
public class ErrorCalculation {

	/**
	 * The overall error.
	 */
	private double globalError;

	/**
	 * The size of a set.
	 */
	private int setSize;

	/**
	 * Returns the root mean square error for a complete training set.
	 * 
	 * @return The current error for the neural network.
	 */
	public double calculateRMS() {
		final double err = Math.sqrt(this.globalError / this.setSize);
		return err;

	}

	/**
	 * Reset the error accumulation to zero.
	 */
	public void reset() {
		this.globalError = 0;
		this.setSize = 0;
	}

	/**
	 * Called to update for each number that should be checked.
	 * 
	 * @param actual
	 *            The actual number.
	 * @param ideal
	 *            The ideal number.
	 */
	public void updateError(final double[] actual, final double[] ideal) {
		for (int i = 0; i < actual.length; i++) {
			final double delta = ideal[i] - actual[i];
			this.globalError += delta * delta;
			this.setSize += ideal.length;
		}
	}

	/**
	 * Update the error.
	 * 
	 * @param actual
	 *            The actual values.
	 * @param ideal
	 *            The ideal values.
	 */
	public void updateError(final NeuralData actual, final NeuralData ideal) {
		updateError(actual.getData(), ideal.getData());
	}

}
