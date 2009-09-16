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
package org.javabb.lucene.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.javabb.infra.Monitor;
import org.javabb.lucene.analysis.PortugueseAnalyzer;
import org.springframework.core.io.Resource;

/**
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 * @author <a href="mailto:dalton@javabb.org">Dalton Camargo</a>
 * 
 * @version $Id: SimpleLuceneSearcher.java,v 1.4.8.4 2008/10/07 23:06:11
 *          daltoncamargo Exp $
 */
public class SimpleLuceneSearcher implements LuceneSearcher {

    private Directory path;
    private Analyzer analyzer;

    /** Ignore words that are less than it often in the document code */
    private static final int DEFALT_MIN_DOC_FREQ = 1;

    /** Ignore terms that are less than it often in the document code */
    private static final int DEFAULT_MIN_TERM_FREQ = 1;

    /** Maximum number of terms that will be included in the query */
    private static final int MAX_QUERY_TERMS = 1000;

    /** Minimum length of a word to be taken into consideration */
    private static final int DEFAULT_MIN_WORD_LENGTH = 2;

    private static Object monitor = Monitor.MONITOR;

    /**
     * @param path
     * @param analyzer
     */
    public SimpleLuceneSearcher(Directory path, Analyzer analyzer) {
	this.path = path;
	this.analyzer = analyzer;
    }

    /**
     * @param path
     * @param analyzer
     * @throws IOException
     */
    public SimpleLuceneSearcher(Resource path, Analyzer analyzer)
	    throws IOException {
	this(FSDirectory.getDirectory(path.getFile(), false), analyzer);
    }

    /**
     * With a simple word, the lucene will search all similar topics related
     * with.
     * 
     * @param query
     * @param fields
     * @return
     */
    @SuppressWarnings("unchecked")
    public List searchSimilarWords(String query, String[] fields,
	    String fieldKey) {
	try {
	    return moreLikeThisAnalyzer(query, fields, fieldKey);
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	return null;
    }

    /** Search documents by similarity using the class {@link MoreLikeThis} */
    @SuppressWarnings("unchecked")
    private List moreLikeThisAnalyzer(String query, String[] fields,
	    String fieldKey) throws Throwable {

	IndexReader indexReader = IndexReader.open(path);
	IndexSearcher indexSearcher = new IndexSearcher(indexReader);

	MoreLikeThis mlt = new MoreLikeThis(indexReader);
	mlt.setFieldNames(fields);
	mlt.setMinDocFreq(DEFALT_MIN_DOC_FREQ);
	mlt.setMinTermFreq(DEFAULT_MIN_TERM_FREQ);
	mlt.setMaxQueryTerms(MAX_QUERY_TERMS);
	mlt.setMinWordLen(DEFAULT_MIN_WORD_LENGTH);
	mlt.setBoost(true);
	mlt.setAnalyzer(analyzer);

	Set stopWords = StopFilter.makeStopSet(PortugueseAnalyzer.STOP_WORDS);
	mlt.setStopWords(stopWords);

	// Query query= mlt.like( new
	// FileInputStream(getClass().getClassLoader().getResource(original).getPath())
	// );

	// Create temp file.
	File temp = File.createTempFile("pattern", ".suffix");

	// Delete temp file when program exits.
	temp.deleteOnExit();

	BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	out.write(query);
	out.close();

	Query queryResult = mlt.like(temp);

	Hits hits = indexSearcher.search(queryResult);

	int len = hits.length();
	List postsList = new ArrayList();

	for (int i = 0; i < Math.min(40, len); i++) {
	    Document d = hits.doc(i);
	    // String idValue = d.getField("postId").stringValue();
	    String idValue = d.getField(fieldKey).stringValue();
	    postsList.add(new Long(idValue));
	}
	indexReader.close();
	indexSearcher.close();
	return postsList;
    }

    /**
     * Simple search
     */
    @SuppressWarnings("unchecked")
    public List search(String query, String[] fields, String fieldKey) {
	List result = new ArrayList();

	try {
	    synchronized (monitor) {
		IndexSearcher searcher = new IndexSearcher(path);
		Query queryObj;
		BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
			BooleanClause.Occur.SHOULD };
		queryObj = MultiFieldQueryParser.parse(query, fields, flags,
			analyzer);

		Hits hits = searcher.search(queryObj);

		for (int i = 0; i < hits.length(); i++) {
		    Document document = hits.doc(i);

		    // String idValue =
		    // document.getField("postId").stringValue();
		    String idValue = document.getField(fieldKey).stringValue();
		    long postId = Long.parseLong(idValue);
		    result.add(new Long(postId));
		}
		searcher.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    /**
     * @see org.javabb.lucene.search.LuceneSearcher#search(java.lang.String)
     */
    public List search(String query[], String[] fields) {
	List result = new ArrayList();

	try {
	    synchronized (monitor) {
		IndexSearcher searcher = new IndexSearcher(path);
		Query queryObj;
		BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
		queryObj = MultiFieldQueryParser.parse(query, fields, flags,
			analyzer);
		Hits hits = searcher.search(queryObj);

		for (int i = 0; i < hits.length(); i++) {
		    Document document = hits.doc(i);
		    String idValue = document.getField("postId").stringValue();
		    long postId = Long.parseLong(idValue);
		    long forumId = new Long(document.getField("forumId")
			    .stringValue()).longValue();
		    long forumByParameterId = new Long(query[2]).longValue();
		    if (forumId == forumByParameterId) {
			result.add(new Long(postId));
		    }
		}
		searcher.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

}
