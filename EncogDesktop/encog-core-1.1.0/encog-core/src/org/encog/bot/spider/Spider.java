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
package org.encog.bot.spider;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.encog.bot.spider.filter.SpiderFilter;
import org.encog.bot.spider.workload.WorkloadManager;

/**
 * Spider: This is the main class that implements the Heaton Research Spider.
 */
public class Spider {
	/**
	 * The logger.
	 */
	private static Logger logger = Logger
			.getLogger("com.heatonresearch.httprecipes.spider.Spider");

	/**
	 * Milliseconds in a minute.
	 */
	public static final int MILISECONDS2MINUTES = 60000;
	
	/**
	 * How many seconds to wait for work.
	 */
	public static final int WAIT4WORK = 60;
	
	/**
	 * The object that the spider reports its findings to.
	 */
	private SpiderReportable report;

	/**
	 * A flag that indicates if this process should be canceled.
	 */
	private boolean cancel = false;

	/**
	 * The workload manager, the spider can use any of several different
	 * workload managers. The workload manager tracks all URL's found.
	 */
	private WorkloadManager workloadManager;

	/**
	 * The Java thread executor that will manage the thread pool.
	 */
	private final ThreadPoolExecutor threadPool;

	/**
	 * The BlockingQueue that will hold tasks for the thread pool.
	 */
	private final BlockingQueue<Runnable> tasks;

	/**
	 * The options for the spider.
	 */
	private final SpiderOptions options;

	/**
	 * Filters used to block specific URL's.
	 */
	private final List<SpiderFilter> filters = new ArrayList<SpiderFilter>();

	/**
	 * The time that the spider began.
	 */
	private Date startTime;

	/**
	 * The time that the spider ended.
	 */
	private Date stopTime;

	/**
	 * Construct a spider object. The options parameter specifies the options
	 * for this spider. The report parameter specifies the class that the spider
	 * is to report progress to.
	 * 
	 * @param options
	 *            The options to run the spider with.
	 * 
	 * @param report
	 *            A class that implements the SpiderReportable interface, that
	 *            will receive information that the spider finds.
	 */
	public Spider(final SpiderOptions options, final SpiderReportable report) {
		this.options = options;
		this.report = report;

		try {
			this.workloadManager = (WorkloadManager) Class.forName(
					options.getWorkloadManager()).newInstance();
		} catch (final InstantiationException e) {
			throw new SpiderError(e);
		} catch (final IllegalAccessException e) {
			throw new SpiderError(e);
		} catch (final ClassNotFoundException e) {
			throw new SpiderError(e);
		}

		this.workloadManager.init(this);
		report.init(this);

		this.tasks = new SynchronousQueue<Runnable>();
		this.threadPool = new ThreadPoolExecutor(options.getCorePoolSize(),
				options.getMaximumPoolSize(), options.getKeepAliveTime(),
				TimeUnit.SECONDS, this.tasks);
		this.threadPool
				.setRejectedExecutionHandler(
						new ThreadPoolExecutor.CallerRunsPolicy());

		// add filters
		if (options.getFilter() != null) {
			for (final String name : options.getFilter()) {
				SpiderFilter filter;
				try {
					filter = (SpiderFilter) Class.forName(name).newInstance();
				} catch (final InstantiationException e) {
					throw new SpiderError(e);
				} catch (final IllegalAccessException e) {
					throw new SpiderError(e);
				} catch (final ClassNotFoundException e) {
					throw new SpiderError(e);
				}
				this.filters.add(filter);
			}
		}

		// perform startup
		if (options.getStartup().equalsIgnoreCase(
				SpiderOptions.STARTUP_RESUME)) {
			this.workloadManager.resume();
		} else {
			this.workloadManager.clear();
		}
	}

	/**
	 * Add a URL for processing. Accepts a SpiderURL.
	 * 
	 * @param url The URL to be added.
	 * @param source Where the URL came from.
	 * @param depth How deep is this URL.
	 */
	public void addURL(final URL url, final URL source, final int depth) {
		// check the depth
		if (this.options.getMaxDepth() != -1 
				&& depth > this.options.getMaxDepth()) {
			return;
		}

		// see if it does not pass any of the filters
		for (final SpiderFilter filter : this.filters) {
			if (filter.isExcluded(url)) {
				return;
			}
		}

		// add the item
		if (this.workloadManager.add(url, source, depth)) {
			final StringBuilder str = new StringBuilder();
			str.append("Adding to workload: ");
			str.append(url);
			str.append("(depth=");
			str.append(depth);
			str.append(")");
			logger.fine(str.toString());
		}
	}

	/**
	 * Set a flag that will cause the begin method to return before it is done.
	 */
	public void cancel() {
		this.cancel = true;
	}

	/**
	 * Get the list of filters for the spider.
	 * 
	 * @return The list of filters for the spider.
	 */
	public List<SpiderFilter> getFilters() {
		return this.filters;
	}

	/**
	 * Get the options for this spider.
	 * 
	 * @return The options for this spider.
	 */
	public SpiderOptions getOptions() {
		return this.options;
	}

	/**
	 * Get the object that the spider reports to.
	 * 
	 * @return The object that spider reports to.
	 */
	public SpiderReportable getReport() {
		return this.report;
	}

	/**
	 * Generate basic status information about the spider.
	 * 
	 * @return The status of the spider.
	 */
	public String getStatus() {
		final StringBuilder result = new StringBuilder();
		result.append("Start time:");
		result.append(this.startTime.toString());
		result.append('\n');
		result.append("Stop time:");
		result.append(this.stopTime.toString());
		result.append('\n');
		result.append("Minutes Elapsed:");
		result.append((this.stopTime.getTime() 
				- this.startTime.getTime()) / Spider.MILISECONDS2MINUTES);
		result.append('\n');

		return result.toString();
	}

	/**
	 * Get the workload manager.
	 * 
	 * @return The workload manager.
	 */
	public WorkloadManager getWorkloadManager() {
		return this.workloadManager;
	}

	/**
	 * Called to start the spider.
	 */
	public void process() {
		this.cancel = false;
		this.startTime = new Date();

		// process all hosts
		do {
			processHost();
		} while (this.workloadManager.nextHost() != null);

		this.threadPool.shutdown();
		this.stopTime = new Date();
	}

	/**
	 * Process one individual host.
	 */
	private void processHost() {
		URL url = null;

		final String host = this.workloadManager.getCurrentHost();

		// first notify the manager
		if (!this.report.beginHost(host)) {
			return;
		}

		// second, notify any filters of a new host
		for (final SpiderFilter filter : this.filters) {
			try {
				filter.newHost(host, this.options.getUserAgent());
			} catch (final IOException e) {
				logger.log(Level.INFO, "Error while reading robots.txt file:"
						+ e.getMessage());
			}
		}

		// now process this host
		do {
			url = this.workloadManager.getWork();
			if (url != null) {
				final SpiderWorker worker = new SpiderWorker(this, url);
				this.threadPool.execute(worker);
			} else {
				this.workloadManager.waitForWork(WAIT4WORK, TimeUnit.SECONDS);
			}
		} while ((url != null || this.threadPool.getActiveCount() > 0)
				&& !this.cancel);
	}

	/**
	 * Set the report object to use.  The spider will report its progess
	 * to this object.
	 * @param report The report object to use.
	 */
	public void setReport(final SpiderReportable report) {
		this.report = report;
	}

}