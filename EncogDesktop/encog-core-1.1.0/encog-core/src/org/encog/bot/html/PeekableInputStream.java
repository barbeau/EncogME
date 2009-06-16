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
package org.encog.bot.html;

import java.io.IOException;
import java.io.InputStream;

/**
 * PeekableInputStream: This is a special input stream that allows the program
 * to peek one or more characters ahead in the file.
 */
public class PeekableInputStream extends InputStream {

	/**
	 * The depth to peek.
	 */
	public static final int INITIAL_DEPTH = 10;
	
	/**
	 * The underlying stream.
	 */
	private final InputStream stream;

	/**
	 * Bytes that have been peeked at.
	 */
	private byte[] peekBytes;

	/**
	 * How many bytes have been peeked at.
	 */
	private int peekLength;

	/**
	 * The constructor accepts an InputStream to setup the object.
	 * 
	 * @param is
	 *            The InputStream to parse.
	 */
	public PeekableInputStream(final InputStream is) {
		this.stream = is;
		this.peekBytes = new byte[PeekableInputStream.INITIAL_DEPTH];
		this.peekLength = 0;
	}

	/**
	 * Peek at the next character from the stream.
	 * 
	 * @return The next character.
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
	public int peek() throws IOException {
		return peek(0);
	}

	/**
	 * Peek at a specified depth.
	 * 
	 * @param depth
	 *            The depth to check.
	 * @return The character peeked at.
	 * @throws IOException
	 *             If an I/O exception occurs.
	 */
	public int peek(final int depth) throws IOException {
		// does the size of the peek buffer need to be extended?
		if (this.peekBytes.length <= depth) {
			final byte[] temp = new byte[depth + INITIAL_DEPTH];
			for (int i = 0; i < this.peekBytes.length; i++) {
				temp[i] = this.peekBytes[i];
			}
			this.peekBytes = temp;
		}

		// does more data need to be read?
		if (depth >= this.peekLength) {
			final int offset = this.peekLength;
			final int length = depth - this.peekLength + 1;
			final int lengthRead = this.stream.read(this.peekBytes, offset,
					length);

			if (lengthRead == -1) {
				return -1;
			}

			this.peekLength = depth + 1;
		}

		return this.peekBytes[depth];
	}

	/**
	 * Read a single byte from the stream. @throws IOException If an I/O
	 * exception occurs. @return The character that was read from the stream.
	 */
	@Override
	public int read() throws IOException {
		if (this.peekLength == 0) {
			return this.stream.read();
		}

		final int result = this.peekBytes[0];
		this.peekLength--;
		for (int i = 0; i < this.peekLength; i++) {
			this.peekBytes[i] = this.peekBytes[i + 1];
		}

		return result;
	}

}
