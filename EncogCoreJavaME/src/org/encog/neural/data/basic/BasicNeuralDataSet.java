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

package org.encog.neural.data.basic;

//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.Iterator;
//import java.util.List;

import java.util.Enumeration;
import java.util.Vector;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.neural.persist.Persistor;
//import org.encog.neural.persist.persistors.BasicNeuralDataSetPersistor;

/**
 * Basic implementation of the NeuralDataSet class. This class simply stores the
 * neural data in an ArrayList. This class is memory based, so large enough
 * datasets could cause memory issues. Many other dataset types extend this
 * class.
 * 
 * @author jheaton
 */
public class BasicNeuralDataSet implements NeuralDataSet, EncogPersistedObject {

	/**
	 * An iterator to be used with the BasicNeuralDataSet. This iterator does
	 * not support removes.
	 * 
	 * @author jheaton
	 */
	//public class BasicNeuralIterator implements Iterator<NeuralDataPair> {
      public class BasicNeuralIterator {  //sjb - Removed Iterable and Generics reference, neither of which are supported in Java ME

		/**
		 * The index that the iterator is currently at.
		 */
		private int currentIndex = 0;

		/**
		 * Is there more data for the iterator to read?
		 * 
		 * @return Returns true if there is more data to read.
		 */
		public boolean hasNext() {
			return this.currentIndex < BasicNeuralDataSet.this.data.size();
		}

		/**
		 * Read the next item.
		 * 
		 * @return The next item.
		 */
		public NeuralDataPair next() {
			if (!hasNext()) {
				return null;
			}

			//return BasicNeuralDataSet.this.data.get(this.currentIndex++);
            return (NeuralDataPair) BasicNeuralDataSet.this.data.elementAt(this.currentIndex++);  //sjb - changed to Vector methods, cast object
		}

		/**
		 * Removes are not supported.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -2279722928570071183L;

	/**
	 * The data held by this object.
	 */
	//private List<NeuralDataPair> data = new ArrayList<NeuralDataPair>();
    private Vector data = new Vector();  //sjb - changed to use Vector instead of List, removed Generics

	/**
	 * The iterators that are currently open to this object.
	 */
	//private final List<BasicNeuralIterator> iterators =
		//new ArrayList<BasicNeuralIterator>();
    
    private final Vector iterators = 
		new Vector();   //sjb - changed to use Vector instead of List, removed Generics

	/**
	 * The description for this object.
	 */
	private String description;

	/**
	 * The name for this object.
	 */
	private String name;

	/**
	 * Default constructor.
	 */
	public BasicNeuralDataSet() {
	}

	/**
	 * Construct a data set from an input and idea array.
	 * 
	 * @param input
	 *            The input into the neural network for training.
	 * @param ideal
	 *            The ideal output for training.
	 */
	public BasicNeuralDataSet(final double[][] input, final double[][] ideal) {
		if (ideal != null) {
			for (int i = 0; i < input.length; i++) {
				final BasicNeuralData inputData = new BasicNeuralData(input[i]);
				final BasicNeuralData idealData = new BasicNeuralData(ideal[i]);
				this.add(inputData, idealData);
			}
		} else {
			for (int i = 0; i < input.length; i++) {
				final BasicNeuralData inputData = new BasicNeuralData(input[i]);
				this.add(inputData);
			}
		}
	}

	/**
	 * Add input to the training set with no expected output. This is used for
	 * unsupervised training.
	 * 
	 * @param data
	 *            The input to be added to the training set.
	 */
	public void add(final NeuralData data) {
		//this.data.add(new BasicNeuralDataPair(data));
        this.data.addElement(new BasicNeuralDataPair(data));  //sjb - changed to Vector methods instead of List
	}

	/**
	 * Add input and expected output. This is used for supervised training.
	 * 
	 * @param inputData
	 *            The input data to train on.
	 * @param idealData
	 *            The ideal data to use for training.
	 */
	public void add(final NeuralData inputData, final NeuralData idealData) {
		if (!this.iterators.isEmpty()) {
            try {
                throw new Exception();
            } catch (Exception ex) {
                System.out.println("BasicNeuralDataSet.add() - Concurrent Modification Exception replacement() - " + ex);
                ex.printStackTrace();
            }
		}
		final NeuralDataPair pair = new BasicNeuralDataPair(inputData,
				idealData);
		//this.data.add(pair);
        this.data.addElement(pair);  //sjb - replaced with Vector methods instead of list
	}

	/**
	 * Add a neural data pair to the list.
	 * 
	 * @param inputData
	 *            A NeuralDataPair object that contains both input and ideal
	 *            data.
	 */
	public void add(final NeuralDataPair inputData) {
		//this.data.add(inputData);
        this.data.addElement(inputData);  //sjb - replaced with Vector methods instead of List
	}

	/**
	 * Close this data set.
	 */
	public void close() {
		// nothing to close

	}

	/**
	 * Create a persistor for this object.
	 * @return A persistor for this object.
	 */
	public Persistor createPersistor() {
		//return new BasicNeuralDataSetPersistor();
        throw new UnsupportedOperationException("Persistance not supported in Java ME");  //sjb - persistance not supported in Java ME
	}

	/**
	 * Get the data held by this container.
	 * 
	 * @return the data
	 */
	//public List<NeuralDataPair> getData() {
    public Vector getData() {  //sjb - removed Generics, changed List to Vector
		return this.data;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get the size of the ideal dataset. This is obtained from the first item
	 * in the list.
	 * 
	 * @return The size of the ideal data.
	 */
	public int getIdealSize() {
		if (this.data.isEmpty()) {
			return 0;
		}
		//final NeuralDataPair first = this.data.get(0);
        final NeuralDataPair first = (NeuralDataPair) this.data.elementAt(0);  //sjb - modified to Vector methods, cast object
		if (first.getIdeal() == null) {
			return 0;
		}

		return first.getIdeal().size();
	}

	/**
	 * Get the size of the input dataset. This is obtained from the first item
	 * in the list.
	 * 
	 * @return The size of the input data.
	 */
	public int getInputSize() {
		if (this.data.isEmpty()) {
			return 0;
		}
		//final NeuralDataPair first = this.data.get(0);
        final NeuralDataPair first = (NeuralDataPair) this.data.elementAt(0);  //sjb - modified to Vector methods, cast object
		return first.getInput().size();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Determine if this neural data set is supervied.  All of the pairs
	 * should be either supervised or not, so simply check the first pair.
	 * If the list is empty then assume unsupervised.
	 * @return True if supervised.
	 */
	public boolean isSupervised() {
		if (this.data.size() == 0) {
			return false;
		}
		//return this.data.get(0).isSupervised();
        return ((NeuralDataPair) this.data.elementAt(0)).isSupervised();  //sjb - modified to Vector methods, cast object
	}

	/**
	 * Create an iterator for this collection.
	 * 
	 * @return An iterator to access this collection.
	 */
	//public Iterator<NeuralDataPair> iterator() {
    public BasicNeuralIterator iterator() {  //sjb - removed generics, changed to object type instead of Iterator
		final BasicNeuralIterator result = new BasicNeuralIterator();
		//this.iterators.add(result);
        this.iterators.addElement(result);  //sjb - replaced with Vector methods

		return result;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	//public void setData(final List<NeuralDataPair> data) {
    public void setData(final Vector data) {  //sjb - removed Generics, changed to Vector
		this.data = data;
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
