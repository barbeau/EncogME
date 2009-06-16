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

package org.encog.neural.data.market;

import org.encog.neural.activation.ActivationFunction;
import org.encog.neural.data.temporal.TemporalDataDescription;

/**
 * This class is used to describe the type of financial data that is needed.
 * Each piece of data can be used for input, prediction or both. If used for
 * input, it will be used as data to help predict. If used for prediction, it
 * will be one of the values predicted. It is possible, and quite common, to use
 * data from both input and prediction.
 * 
 * @author jheaton
 * 
 */
public class MarketDataDescription extends TemporalDataDescription {

	/**
	 * The ticker symbol to be loaded.
	 */
	private TickerSymbol ticker;

	/**
	 * The type of data to be loaded from the specified ticker symbol.
	 */
	private MarketDataType dataType;

	/**
	 * Construct a MarketDataDescription item.
	 * 
	 * @param ticker
	 *            The ticker symbol to use.
	 * @param dataType
	 *            The data type needed.
	 * @param activationFunction
	 *            The activation function to apply to this data, can be null.
	 * @param input
	 *            Is this field used for input?
	 * @param predict
	 *            Is this field used for prediction?
	 */
	public MarketDataDescription(final TickerSymbol ticker,
			final MarketDataType dataType,
			final ActivationFunction activationFunction, final boolean input,
			final boolean predict) {
		super(activationFunction, Type.PERCENT_CHANGE, input, predict);
		this.ticker = ticker;
		this.dataType = dataType;
	}

	/**
	 * Construct a MarketDataDescription item.
	 * 
	 * @param ticker
	 *            The ticker symbol to use.
	 * @param dataType
	 *            The data type needed.
	 * @param input
	 *            Is this field used for input?
	 * @param predict
	 *            Is this field used for prediction?
	 */
	public MarketDataDescription(final TickerSymbol ticker,
			final MarketDataType dataType, final boolean input,
			final boolean predict) {
		this(ticker, dataType, null, input, predict);
	}

	/**
	 * @return the ticker
	 */
	public TickerSymbol getTicker() {
		return ticker;
	}

	/**
	 * @return the dataType
	 */
	public MarketDataType getDataType() {
		return dataType;
	}

}
