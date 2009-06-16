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

package org.encog.util.downsample;

import java.awt.Image;

/**
 * Utility to downsample an image.
 * @author jheaton
 *
 */
public interface Downsample {

	/**
	 * Downsample the image to the specified height and width.
	 * @param height The height to downsample to.
	 * @param width The width to downsample to.
	 * @return The downsampled image.
	 */
	double[] downSample(int height, int width);
	
	/**
	 * Process the specified image. 
	 * @param image The image to process.
	 */
	void processImage(Image image);
	
	/**
	 * Find the bounds around the image to exclude whitespace.
	 */
	void findBounds();
	
	/**
	 * @return The image to be downsampled.
	 */
	Image getImage();
	
	/**
	 * @return The image pixel map.
	 */
	int[] getPixelMap();
	
	/**
	 * @return The x-ratio of the downsample.
	 */
	double getRatioX();
	
	/**
	 * @return The y-ratio of the downsample.
	 */
	double getRatioY();
	
	/**
	 * @return The height of the image.
	 */
	int getImageHeight();
	
	/**
	 * @return The width of the image.
	 */
	int getImageWidth();
	
	/**
	 * @return The left boundary of the image.
	 */
	int getDownSampleLeft();
	
	/**
	 * @return Get the right boundary of the image.
	 */
	int getDownSampleRight();
	
	/**
	 * @return Get the top boundary of the image.
	 */
	int getDownSampleTop();
	
	/**
	 * @return Get the bottom boundary of the image.
	 */
	int getDownSampleBottom();
}
