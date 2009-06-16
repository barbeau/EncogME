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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.encog.neural.data.market.loader.LoadedMarketData;
import org.encog.neural.data.market.loader.MarketLoader;
import org.encog.neural.data.temporal.TemporalDataDescription;
import org.encog.neural.data.temporal.TemporalNeuralDataSet;
import org.encog.neural.data.temporal.TemporalPoint;
import org.encog.util.time.TimeUnit;

/**
 * A data set that is designed to hold market data. This class is based on the
 * TemporalNeuralDataSet. This class is designed to load financial data from
 * external sources. This class is designed to track financial data across days.
 * However, it should be usable with other levels of granularity as well.
 * 
 * @author jheaton
 * 
 */
public class MarketNeuralDataSet extends TemporalNeuralDataSet {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = 170791819906003867L;

	/**
	 * The loader to use to obtain the data.
	 */
	private final MarketLoader loader;

	/**
	 * A map between the data points and actual data.
	 */
	private final Map<Integer, TemporalPoint> pointIndex = 
		new HashMap<Integer, TemporalPoint>();

	/**
	 * Construct a market data set object.
	 * 
	 * @param loader
	 *            The loader to use to get the financial data.
	 * @param inputWindowSize
	 *            The input window size, that is how many datapoints do we use
	 *            to predict.
	 * @param predictWindowSize
	 *            How many datapoints do we want to predict.
	 */
	public MarketNeuralDataSet(final MarketLoader loader,
			final int inputWindowSize, final int predictWindowSize) {
		super(inputWindowSize, predictWindowSize);
		this.loader = loader;
		setSequenceGrandularity(TimeUnit.DAYS);
	}

	/**
	 * Add one description of the type of market data that we are seeking at
	 * each datapoint.
	 * 
	 * @param desc
	 *            The data description.
	 */
	public void addDescription(final TemporalDataDescription desc) {
		if (!(desc instanceof MarketDataDescription)) {
			throw new MarketError(
					"Only MarketDataDescription objects may be used "
							+ "with the MarketNeuralDataSet container.");
		}
		super.addDescription(desc);
	}

	/**
	 * Create a datapoint at the specified date.
	 * 
	 * @param when
	 *            The date to create the point at.
	 * @return Returns the TemporalPoint created for the specified date.
	 */
	public TemporalPoint createPoint(final Date when) {
		final int sequence = getSequenceFromDate(when);
		TemporalPoint result = this.pointIndex.get(sequence);

		if (result == null) {
			result = super.createPoint(when);
			this.pointIndex.put(result.getSequence(), result);
		}

		return result;
	}

	/**
	 * @return The loader that is being used for this set.
	 */
	public MarketLoader getLoader() {
		return this.loader;
	}

	/**
	 * Load data from the loader.
	 * 
	 * @param begin
	 *            The beginning date.
	 * @param end
	 *            The ending date.
	 */
	public void load(final Date begin, final Date end) {
		// define the starting point if it is not already defined
		if (getStartingPoint() == null) {
			setStartingPoint(begin);
		}

		// clear out any loaded points
		getPoints().clear();

		// first obtain a collection of symbols that need to be looked up
		final Set<TickerSymbol> set = new HashSet<TickerSymbol>();
		for (final TemporalDataDescription desc : getDescriptions()) {
			final MarketDataDescription mdesc = (MarketDataDescription) desc;
			set.add(mdesc.getTicker());
		}

		// now loop over each symbol and load the data
		for (final TickerSymbol symbol : set) {
			loadSymbol(symbol, begin, end);
		}

		// resort the points
		sortPoints();
	}

	/**
	 * Load one point of market data.
	 * 
	 * @param ticker
	 *            The ticker symbol to load.
	 * @param point
	 *            The point to load at.
	 * @param item
	 *            The item being loaded.
	 */
	private void loadPointFromMarketData(final TickerSymbol ticker,
			final TemporalPoint point, final LoadedMarketData item) {
		for (final TemporalDataDescription desc : getDescriptions()) {
			final MarketDataDescription mdesc = (MarketDataDescription) desc;

			if (mdesc.getTicker().equals(ticker)) {
				point.setData(mdesc.getIndex(), item.getData(mdesc
						.getDataType()));
			}
		}
	}

	/**
	 * Load one ticker symbol.
	 * 
	 * @param ticker
	 *            The ticker symbol to load.
	 * @param from
	 *            Load data from this date.
	 * @param to
	 *            Load data to this date.
	 */
	private void loadSymbol(final TickerSymbol ticker, final Date from,
			final Date to) {
		final Collection<LoadedMarketData> data = getLoader().load(ticker,
				null, from, to);
		for (final LoadedMarketData item : data) {
			final TemporalPoint point = this.createPoint(item.getWhen());

			loadPointFromMarketData(ticker, point, item);
		}
	}

}
