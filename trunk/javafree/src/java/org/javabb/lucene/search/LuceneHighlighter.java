/*
 * Copyright 24/04/2005 - Vicinity - www.vicinity.com All rights reserveds
 */
package org.javabb.lucene.search;


import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import org.javabb.lucene.analysis.PortugueseAnalyzer;


/**
 *
 * @author Marcos Silva Pereira - marcos.pereira@vicinity.com
 *
 * @since 24/04/2005
 *
 * @version $Id: LuceneHighlighter.java,v 1.1 2009/05/11 20:27:11 daltoncamargo Exp $
 */
public class LuceneHighlighter {

	private static final String CONTENTS_FIELD = "contents";

	// TODO Remove and make Spring hold it
	private static final Analyzer analyzer = new PortugueseAnalyzer();

	/**
	 *
	 */
	public String highlight( String text, String query, String separator,
			int fragSize, int numFrags, boolean complete ) {

		return text;
		/*String result = "";
		try {

			Directory ramDir = new RAMDirectory();

			addDocument(text, ramDir);

			IndexReader reader = IndexReader.open(ramDir);

			QueryParser qr = new QueryParser(CONTENTS_FIELD, analyzer);
			Query queryObj = qr.parse(query);
			queryObj = queryObj.rewrite(reader);

			Scorer scorer = new QueryScorer(queryObj);
			Formatter formatter = new CSSFormatter("highlight");
			Highlighter highlighter = new Highlighter(formatter, scorer);

			highlighter.setTextFragmenter(new SimpleFragmenter(fragSize));

			TokenStream token;
			token = analyzer
					.tokenStream(CONTENTS_FIELD, new StringReader(text));

			if (!complete) {
				//result = highlighter.getBestFragments(token, text, numFrags, separator);
			} else {
				//result =  highlighter.get
				//result = highlighter.getCompleteTextHighlight(token, text);

			}
			result = highlighter.getBestFragments(token, text, numFrags, separator);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO logger

		} finally {

			result = avoidEmpty(result, text);

		}

		return result;
*/
	}

	/**
	 * @param actualText
	 * @param ramDir
	 * @throws IOException
	 */
	private void addDocument( String actualText, Directory ramDir )
			throws IOException {

		Field field = new Field(CONTENTS_FIELD, actualText, Field.Store.YES, Field.Index.TOKENIZED);

		Document document = new Document();

		document.add(field);

		IndexWriter writer = new IndexWriter(ramDir, analyzer, true);

		writer.addDocument(document);
		writer.optimize();
		writer.close();

	}

	private String avoidEmpty( String string , String text) {

		String result = string;

		if (string == null || "".equals(string.trim())) {

			int length = Math.min(200, text.length());
			result = text.substring(0, length);

		}

		return result;
	}
}
