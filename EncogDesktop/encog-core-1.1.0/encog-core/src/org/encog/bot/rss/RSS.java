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
package org.encog.bot.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * RSS: This is the class that actually parses the RSS and builds a collection
 * of RSSItems. To make use of this class call the load method with a URL that
 * points to RSS.
 */
public class RSS {
	/**
	 * Simple utility method that obtains the text of an XML node.
	 * 
	 * @param n
	 *            The XML node.
	 * @return The text of the specified XML node.
	 */
	public static String getXMLText(final Node n) {
		final NodeList list = n.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			final Node n2 = list.item(i);
			if (n2.getNodeType() == Node.TEXT_NODE) {
				return n2.getNodeValue();
			}
		}
		return null;
	}

	/**
	 * Simple utility function that converts a RSS formatted date into a Java
	 * date.
	 * 
	 * @param datestr
	 *            The RSS formatted date.
	 * @return A Java java.util.date
	 */
	public static Date parseDate(final String datestr) {
		try {
			final DateFormat formatter = new SimpleDateFormat(
					"E, dd MMM yyyy HH:mm:ss Z");
			final Date date = formatter.parse(datestr);
			return date;
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * All of the attributes for this RSS document.
	 */
	private final Map<String, String> attributes = 
		new HashMap<String, String>();

	/**
	 * All RSS items, or stories, found.
	 */
	private final List<RSSItem> items = new ArrayList<RSSItem>();

	/**
	 * Get the list of attributes.
	 * 
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	/**
	 * @return the items
	 */
	public List<RSSItem> getItems() {
		return this.items;
	}

	/**
	 * Load all RSS data from the specified URL.
	 * 
	 * @param url
	 *            URL that contains XML data.
	 * @throws IOException
	 *             Thrown if an IO error occurs.
	 * @throws SAXException
	 *             Thrown if there is an error while parsing XML.
	 * @throws ParserConfigurationException
	 *             Thrown if there is an XML parse config error.
	 */
	public void load(final URL url) throws IOException, SAXException,
			ParserConfigurationException {
		final URLConnection http = url.openConnection();
		final InputStream is = http.getInputStream();

		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		final Document d = factory.newDocumentBuilder().parse(is);

		final Element e = d.getDocumentElement();
		final NodeList nl = e.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			final Node node = nl.item(i);
			final String nodename = node.getNodeName();

			// RSS 2.0
			if (nodename.equalsIgnoreCase("channel")) {
				loadChannel(node);
				// RSS 1.0
			} else if (nodename.equalsIgnoreCase("item")) {
				loadItem(node);
			}
		}

	}

	/**
	 * Load the channle node.
	 * 
	 * @param channel
	 *            A node that contains a channel.
	 */
	private void loadChannel(final Node channel) {
		final NodeList nl = channel.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			final Node node = nl.item(i);
			final String nodename = node.getNodeName();
			if (nodename.equalsIgnoreCase("item")) {
				loadItem(node);
			} else {
				if (node.getNodeType() != Node.TEXT_NODE) {
					this.attributes.put(nodename, RSS.getXMLText(node));
				}
			}
		}
	}

	/**
	 * Load the specified RSS item, or story.
	 * 
	 * @param item
	 *            A XML node that contains a RSS item.
	 */
	private void loadItem(final Node item) {
		final RSSItem rssItem = new RSSItem();
		rssItem.load(item);
		this.items.add(rssItem);
	}

	/**
	 * Convert the object to a String.
	 * 
	 * @return The object as a String.
	 */
	public String toString() {
		final StringBuilder str = new StringBuilder();
		final Set<String> set = this.attributes.keySet();
		for (final String item : set) {
			str.append(item);
			str.append('=');
			str.append(this.attributes.get(item));
			str.append('\n');
		}
		str.append("Items:\n");
		for (final RSSItem item : this.items) {
			str.append(item.toString());
			str.append('\n');
		}
		return str.toString();
	}

}
