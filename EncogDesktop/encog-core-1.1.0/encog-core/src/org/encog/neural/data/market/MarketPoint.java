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

import java.util.Date;

import org.encog.neural.data.temporal.TemporalPoint;

/**
 * Hold one market datapoint.  This class is based on the TemporalPoint,
 * however it is designed to take its sequence number from a date.
 * @author jheaton
 *
 */
public class MarketPoint extends TemporalPoint {

	/**
	 * When to hold the data from.
	 */
	private Date when;

	/** 
	 * Construct a MarketPoint with the specified date and size.
	 * @param when When is this data from.
	 * @param size What is the size of the data.
	 */
	public MarketPoint(final Date when, final int size) {
		super(size);
		this.when = when;
	}


	/**
	 * @return the when
	 */
	public Date getWhen() {
		return when;
	}
	
	
	
}
