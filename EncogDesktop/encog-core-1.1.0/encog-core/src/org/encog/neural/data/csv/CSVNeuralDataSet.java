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
package org.encog.neural.data.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.encog.neural.NeuralNetworkError;
import org.encog.neural.data.NeuralData;
import org.encog.neural.data.NeuralDataError;
import org.encog.neural.data.NeuralDataPair;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataPair;
import org.encog.util.ReadCSV;

/**
 * An implementation of the NeuralDataSet interface designed to provide a CSV
 * file to the neural network. This implementation uses the BasicNeuralData to
 * hold the data being read. This class has no ability to write CSV files.
 * The columns of the CSV file will specify both the input and ideal 
 * columns.  
 * 
 * This class is not memory based, so very long files can be used, 
 * without running out of memory.
 * @author jheaton
 */
public class CSVNeuralDataSet implements NeuralDataSet {

	/**
	 * An iterator designed to read from CSV files.
	 * @author jheaton
	 */
	public class CSVNeuralIterator implements Iterator<NeuralDataPair> {
		
		/**
		 * A ReadCSV object used to parse the CSV file.
		 */
		private ReadCSV reader;
		
		/**
		 * Is there data that has been read and is ready?
		 */
		private boolean dataReady;

		/**
		 * Default constructor.  Create a new iterator from the parent class.
		 */
		public CSVNeuralIterator() {
			try {
				this.reader = null;
				this.reader = new ReadCSV(CSVNeuralDataSet.this.filename,
						CSVNeuralDataSet.this.headers,
						CSVNeuralDataSet.this.delimiter);
				this.dataReady = false;
			} catch (final IOException e) {
				throw new NeuralNetworkError(e);
			}
		}

		/**
		 * Close the iterator, and the underlying CSV file.
		 */
		public void close() {
			try {
				this.reader.close();
			} catch (final IOException e) {
				throw new NeuralDataError("Can't close CSV file.");
			}
		}

		/**
		 * Determine if there is more data to be read.
		 * @return True if there is more data to be read.
		 */
		public boolean hasNext() {
			if (this.reader == null) {
				return false;
			}

			if (this.dataReady) {
				return true;
			}

			try {
				if (this.reader.next()) {
					this.dataReady = true;
					return true;
				}
				this.dataReady = false;
				return false;
			} catch (final IOException e) {
				throw new NeuralNetworkError(e);
			}

		}

		/**
		 * Read the next record from the CSV file.
		 * @return The next data pair read.
		 */
		public NeuralDataPair next() {

			final NeuralData input = new BasicNeuralData(
					CSVNeuralDataSet.this.inputSize);
			NeuralData ideal = null;

			for (int i = 0; i < CSVNeuralDataSet.this.inputSize; i++) {
				input.setData(i, this.reader.getDouble(i));
			}

			if (CSVNeuralDataSet.this.idealSize > 0) {
				ideal = new BasicNeuralData(CSVNeuralDataSet.this.idealSize);
				for (int i = 0; i < CSVNeuralDataSet.this.idealSize; i++) {
					ideal.setData(i, this.reader.getDouble(i
							+ CSVNeuralDataSet.this.inputSize));
				}
			}

			this.dataReady = false;
			return new BasicNeuralDataPair(input, ideal);
		}

		/**
		 * Removes are not supported.
		 */
		public void remove() {

			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Error message indicating that adds are not supported.
	 */
	public static final String ADD_NOT_SUPPORTED = 
		"Adds are not supported with this dataset, it is read only.";
	
	/**
	 * The CSV filename to read from.
	 */
	private final String filename;
	
	/**
	 * The number of columns of input data.
	 */
	private final int inputSize;
	
	/**
	 * The number of columns of ideal data.
	 */	
	private final int idealSize;
	
	/**
	 * The delimiter that separates the columns, defaults to a comma.
	 */
	private final char delimiter;
	
	/**
	 * Specifies if headers are present on the first row.
	 */
	private final boolean headers;

	/**
	 * A collection of iterators that have been created.
	 */
	private final List<CSVNeuralIterator> iterators = 
		new ArrayList<CSVNeuralIterator>();

	
	/**
	 * Construct this data set using a comma as a delimiter.
	 * @param filename The CSV filename to read.
	 * @param inputSize The number of columns that make up the input set.	 * 
	 * @param idealSize The number of columns that make up the ideal set.
	 * @param headers True if headers are present on the first line.
	 */
	public CSVNeuralDataSet(final String filename, final int inputSize,
			final int idealSize, final boolean headers) {
		this(filename, inputSize, idealSize, headers, ',');
	}

	/**
	 * Construct this data set using a comma as a delimiter.
	 * @param filename The CSV filename to read.
	 * @param inputSize The number of columns that make up the input set.	 * 
	 * @param idealSize The number of columns that make up the ideal set.
	 * @param headers True if headers are present on the first line.
	 * @param delimiter The delimiter to use.
	 */
	public CSVNeuralDataSet(final String filename, final int inputSize,
			final int idealSize, final boolean headers, final char delimiter) {
		this.filename = filename;
		this.inputSize = inputSize;
		this.idealSize = idealSize;
		this.delimiter = delimiter;
		this.headers = headers;
	}

	/**
	 * Adds are not supported.
	 * @param data1 Not used.
	 */
	public void add(final NeuralData data1) {
		throw new NeuralDataError(CSVNeuralDataSet.ADD_NOT_SUPPORTED);
	}

	/**
	 * Adds are not supported.
	 * @param inputData Not used.
	 * @param idealData Not used.
	 */
	public void add(final NeuralData inputData, final NeuralData idealData) {
		throw new NeuralDataError(CSVNeuralDataSet.ADD_NOT_SUPPORTED);

	}

	/**
	 * Adds are not supported.
	 * @param inputData Not used.
	 */
	public void add(final NeuralDataPair inputData) {
		throw new NeuralDataError(CSVNeuralDataSet.ADD_NOT_SUPPORTED);
	}

	/**
	 * Close any iterators from this dataset.
	 */
	public void close() {
		for (final CSVNeuralIterator iterator : this.iterators) {
			iterator.close();
		}
	}

	/**
	 * @return the delimiter
	 */
	public char getDelimiter() {
		return this.delimiter;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * @return The size of the ideal data.
	 */
	public int getIdealSize() {
		return this.idealSize;
	}

	/**
	 * @return The size of the input data.
	 */
	public int getInputSize() {
		return this.inputSize;
	}

	/**
	 * Get an iterator to use with the CSV data.
	 * @return An iterator.
	 */
	public Iterator<NeuralDataPair> iterator() {
		return new CSVNeuralIterator();
	}
}
