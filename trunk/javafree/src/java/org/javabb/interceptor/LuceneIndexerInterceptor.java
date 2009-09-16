/*
 * Copyright 2004 JavaFree.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javabb.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.javabb.lucene.index.Indexer;

import org.javabb.vo.Post;

/**
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 * 
 * $Id: LuceneIndexerInterceptor.java,v 1.3 2005/04/08 20:19:42 daltoncamargo
 * Exp $
 */
public class LuceneIndexerInterceptor implements MethodInterceptor {

	protected static final Log logger = LogFactory
			.getLog(LuceneIndexerInterceptor.class);

	private Indexer indexer;

	/**
	 *  
	 */
	public LuceneIndexerInterceptor() {

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param index
	 */
	public LuceneIndexerInterceptor(Indexer index) {

		this.indexer = index;

	}

	/**
	 * @return Returns the indexer.
	 */
	public Indexer getIndexer() {

		return indexer;

	}

	/**
	 * @param indexer
	 *            The indexer to set.
	 */
	public void setIndexer(Indexer indexer) {

		this.indexer = indexer;

	}

	/**
	 * Works only for Post objects.
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object[] params = invocation.getArguments();
		Object post = params[0];

		Object result = post;

		if (post instanceof Post) {

			Post postObj = (Post) post;
			indexer.indexPost(postObj);

			result = invocation.proceed();

		} else {

			// TODO logger...
		}

		return result;

	}

}