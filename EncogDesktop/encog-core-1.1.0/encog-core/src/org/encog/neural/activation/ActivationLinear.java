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
package org.encog.neural.activation;

import org.encog.neural.NeuralNetworkError;
import org.encog.neural.persist.Persistor;
import org.encog.neural.persist.persistors.ActivationLinearPersistor;

/**
 * ActivationLinear: The Linear layer is really not an activation function at
 * all. The input is simply passed on, unmodified, to the output. This
 * activation function is primarily theoretical and of little actual use.
 * Usually an activation function that scales between 0 and 1 or -1 and 1 should
 * be used.
 */
public class ActivationLinear implements ActivationFunction {

	/**
	 * Serial id for this class.
	 */
	private static final long serialVersionUID = -5356580554235104944L;

	/**
	 * The description for this object.
	 */
	private String description;
	
	/**
	 * The name of this object.
	 */
	private String name;

	/**
	 * A threshold function for a neural network.
	 * 
	 * @param d
	 *            The input to the function.
	 * @return The output from the function.
	 */
	public double activationFunction(final double d) {
		return d;
	}

	/**
	 * Create a persistor for this object.
	 * @return The new persistor.
	 */
	public Persistor createPersistor() {
		return new ActivationLinearPersistor();
	}

	/**
	 * Some training methods require the derivative.
	 * 
	 * @param d
	 *            The input.
	 * @return The output.
	 */
	public double derivativeFunction(final double d) {
		throw new NeuralNetworkError(
				"Can't use the linear activation function "
						+ "where a derivative is required.");
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
