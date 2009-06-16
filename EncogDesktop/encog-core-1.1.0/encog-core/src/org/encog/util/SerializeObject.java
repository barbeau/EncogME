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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * SerializeObject: Load or save an object using Java serialization.
 */
public final class SerializeObject {
	
	/**
	 * Private constructor.
	 */
	private SerializeObject() {
	}
	
	/**
	 * Load an object.
	 * 
	 * @param filename
	 *            The filename.
	 * @return The loaded object.
	 * @throws IOException
	 *             An IO error occurred.
	 * @throws ClassNotFoundException
	 *             The specified class can't be found.
	 */
	public static Serializable load(final String filename) throws IOException,
			ClassNotFoundException {
		Serializable object;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		fis = new FileInputStream(filename);
		in = new ObjectInputStream(fis);
		object = (Serializable) in.readObject();
		in.close();
		return object;
	}

	/**
	 * Save the specified object.
	 * @param filename The filename to save.
	 * @param object The object to save.
	 * @throws IOException An IO error occurred.
	 */
	public static void save(final String filename, final Serializable object)
			throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		fos = new FileOutputStream(filename);
		out = new ObjectOutputStream(fos);
		out.writeObject(object);
		out.close();
	}

}
