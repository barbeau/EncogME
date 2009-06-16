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

package org.encog.neural.data.image;

import java.awt.Image;

import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.util.downsample.Downsample;

/**
 * An extension of the BasicNeuralData class that is designed to hold images for
 * input into a neural network. This class should only be used with the
 * ImageNeuralDataSet collection.
 * 
 * This class provides the ability to associate images with the elements of a
 * dataset. These images will be downsampled to the resolution specified in the
 * ImageNeuralData set class that they are added to.
 * 
 * @author jheaton
 * 
 */
public class ImageNeuralData extends BasicNeuralData {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -4645971270240180450L;
	/**
	 * The image associated with this class.
	 */
	private Image image;

	/**
	 * Construct an object based on an image.
	 * 
	 * @param image
	 *            The image to use.
	 */
	public ImageNeuralData(final Image image) {
		super(1);
		this.image = image;
	}

	/**
	 * Downsample, and copy, the image contents into the data of this object.
	 * Calling this method has no effect on the image, as the same image can be
	 * downsampled multiple times to different resolutions.
	 * 
	 * @param downsampler
	 *            The downsampler object to use.
	 * @param findBounds
	 *            Should the bounds be located and cropped.
	 * @param height
	 *            The height to downsample to.
	 * @param width
	 *            The width to downsample to.
	 */
	public void downsample(final Downsample downsampler,
			final boolean findBounds, final int height, final int width) {
		if (findBounds) {
			downsampler.findBounds();
		}
		final double[] sample = downsampler.downSample(height, width);
		this.setData(sample);
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(final Image image) {
		this.image = image;
	}

}
