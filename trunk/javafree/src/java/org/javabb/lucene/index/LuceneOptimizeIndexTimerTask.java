/*
 * Copyright 28/03/2005 - Vicinity - www.vicinity.com.br All rights reserveds
 */
package org.javabb.lucene.index;


import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.javabb.infra.Monitor;

import org.springframework.core.io.Resource;


/**
 * {@link java.util.Timer Timer task} to optimize lucene index in a defined time
 * period. It can be configured with a schedule which will invoke this task in
 * defined period. Therefore, indexer classes avoid optimize index each time that
 * them put a new document in index. If you plan use it with <a
 * href="http://www.springframework.org">SpringFramework</a>, create a schedule
 * using Timer support like the following:
 *
 * <pre>
 * &lt;bean id=&quot;optimizeLuceneIndex&quot; class=&quot;org.javabb.lucene.index.LuceneOptimizeIndexTimerTask&quot;&gt;
 *   &lt;constructor-arg index=&quot;0&quot;&gt;&lt;ref local=&quot;lucenePath&quot; /&gt;&lt;/constructor-arg&gt;
 *   &lt;constructor-arg index=&quot;1&quot;&gt;&lt;ref local=&quot;analyzer&quot; /&gt;&lt;/constructor-arg&gt;
 * &lt;/bean&gt;
 *
 * &lt;bean id=&quot;scheduledTask&quot; class=&quot;org.springframework.scheduling.timer.ScheduledTimerTask&quot;&gt;
 *   &lt;property name=&quot;delay&quot;&gt;&lt;value&gt;delayTime&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name=&quot;period&quot;&gt;&lt;value&gt;periodTime&lt;/value&gt;&lt;/property&gt;
 *   &lt;property name=&quot;timerTask&quot;&gt;&lt;ref local=&quot;optimizeLuceneIndex&quot; /&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * See Spring documentation for more details about task schedule.
 *
 * @author Marcos Silva Pereira - marcos.pereira@vicinity.com.br
 * @since 13/03/2005
 *
 * @version $Id: LuceneOptimizeIndexTimerTask.java,v 1.1 2009/05/11 20:27:20 daltoncamargo Exp $
 *
 * @see java.util.Timer
 * @see java.util.TimerTask
 */
public class LuceneOptimizeIndexTimerTask extends TimerTask {

	private static final Log logger;

	static {

		// create the logger.
		logger = LogFactory.getLog(LuceneOptimizeIndexTimerTask.class);

	}

	private static final Object monitor = Monitor.MONITOR;

	private Directory lucenePath;

	private Analyzer analyzer;

	/**
	 * Create a LuceneOptimizeIndexTimerTask object. It is the preferencial
	 * constructor because {@link Directory} class provides a high abstraction
	 * over where lucene index will be created.
	 *
	 * @param lucenePath
	 * @param analyzer
	 */
	public LuceneOptimizeIndexTimerTask ( Directory lucenePath,
			Analyzer analyzer ) {

		this.lucenePath = lucenePath;
		this.analyzer = analyzer;

	}

	/**
	 * @param lucenePath
	 * @param analyzer
	 * @throws IOException
	 */
	public LuceneOptimizeIndexTimerTask ( Resource lucenePath, Analyzer analyzer )
			throws IOException {

		this.lucenePath = FSDirectory.getDirectory(lucenePath.getFile(), false);
		this.analyzer = analyzer;

	}

	/**
	 * Optimize lucene index at a period time.
	 *
	 * @see java.util.TimerTask#run()
	 */
	public void run() {

		try {

			Date start = new Date();
			logger.info("Optimizing index - Time: [" + start + "].");

			synchronized (monitor) {

				IndexWriter writer;
				writer = new IndexWriter(lucenePath, analyzer, false);

				writer.optimize();
				writer.close();

			}

			Date finish = new Date();
			logger.info("Index optimized - Time: [" + finish + "].");

		} catch (Exception ex) {

			logger.info("Could not optimize lucene index at [" + lucenePath
					+ "]", ex);

		}

	}

	/**
	 * Returns the value of {@link analyzer} attribute
	 *
	 * @return Returns the analyzer.
	 */
	public Analyzer getAnalyzer() {

		return analyzer;

	}

	/**
	 * Returns the value of {@link lucenePath} attribute
	 *
	 * @return Returns the lucenePath.
	 */
	public Directory getLucenePath() {

		return lucenePath;

	}

}
