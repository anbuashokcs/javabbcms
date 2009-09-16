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
package org.javabb.lucene.index;

import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.javabb.infra.Monitor;
import org.javabb.vo.Noticias;
import org.javabb.vo.Post;
import org.javabb.vo.Wiki;
import org.springframework.core.io.Resource;

/**
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 * 
 * @version $Id: Indexer.java,v 1.1 2009/05/11 20:27:20 daltoncamargo Exp $
 * 
 *          C:/eclipse/workspace/liber/javabb/src/sql/mysql/creates/0.02.sql
 */
public class Indexer {

    private static final Log logger = LogFactory.getLog(Indexer.class);

    private Directory path;
    private Analyzer analyzer;

    private boolean optimize;
    private boolean createNew;
    private boolean initialized = false;

    private static Object monitor = Monitor.MONITOR;

    /**
     * @param analyzer
     * @param path
     * @param opt
     * @param createNew
     * @throws IOException
     */
    public Indexer(Analyzer analyzer, Directory path, boolean opt,
	    boolean createNew) throws IOException {

	this.analyzer = analyzer;
	this.path = path;
	this.optimize = opt;
	this.createNew = createNew;

	initialize();
    }

    /**
     * @param analyzer
     * @param path
     * @param opt
     * @param createNew
     * @throws IOException
     */
    public Indexer(Analyzer analyzer, Resource path, boolean opt,
	    boolean createNew) throws IOException {
	this(analyzer, FSDirectory.getDirectory(path.getFile(), false), opt,
		createNew);
    }

    /**
     * @return Returns the analyzer.
     */
    public Analyzer getAnalyzer() {

	return analyzer;

    }

    /**
     * @return Returns the path.
     */
    public Directory getPath() {
	return path;
    }

    /**
     * @return Returns the optimize.
     */
    public boolean isOptimize() {
	return optimize;
    }

    /**
     * @param post
     */
    public void indexPost(Post post) {
	Document document = postToDocument(post);
	indexDocument(document);
    }



    private Document postToDocument(Post post) {
	Document document = new Document();
	document.add(new Field("postId", String.valueOf(post.getId()),
		Field.Store.YES, Field.Index.UN_TOKENIZED));
	document.add(new Field("date", Integer.toString((int) (post
		.getPostDate().getTime() / 3600000l)), Field.Store.YES,
		Field.Index.UN_TOKENIZED));
	document.add(new Field("subject", post.getSubject(), Field.Store.NO,
		Field.Index.TOKENIZED));
	document.add(new Field("text", post.getPostBody(), Field.Store.NO,
		Field.Index.TOKENIZED));
	document.add(new Field("userId",
		String.valueOf(post.getUser().getId()), Field.Store.NO,
		Field.Index.TOKENIZED));
	document
		.add(new Field("forumId", String.valueOf(post.getTopic()
			.getForum().getId()), Field.Store.YES,
			Field.Index.UN_TOKENIZED));
	String titleTopic = post.getTopic().getTitleTopic();
	document.add(new Field("title", titleTopic, Field.Store.NO,
		Field.Index.TOKENIZED));

	return document;
    }

    public void indexNoticia(Noticias noticia) {
	Document document = noticiaToDocument(noticia);
	indexDocument(document);
    }

    public Document noticiaToDocument(Noticias noticia) {
	Document document = new Document();
	document.add(new Field("notId", String.valueOf(noticia.getNotId()),
		Field.Store.YES, Field.Index.UN_TOKENIZED));
	if (noticia.getPublishDate() != null) {
	    document.add(new Field("publishDate",Integer.toString((int) (noticia.getPublishDate().getTime() / 3600000l)), Field.Store.YES,
			    Field.Index.UN_TOKENIZED));
	}
	document.add(new Field("notTitle", noticia.getTitle(), Field.Store.YES,
		Field.Index.TOKENIZED));
	document.add(new Field("notBody", noticia.getBody(), Field.Store.YES,
		Field.Index.TOKENIZED));
	document.add(new Field("notUserId", String.valueOf(noticia.getUser()
		.getId()), Field.Store.NO, Field.Index.TOKENIZED));

	return document;
    }

    public void updateNoticia(Noticias noticia) {
	this.deleteNoticia(noticia.getNotId());
	this.indexNoticia(noticia);
    }

    public void deleteNoticia(Long id) {
	Term term = new Term("notId", String.valueOf(id));
	deleteUsingTerm(term);
    }

    /**
     * @param post
     */
    public void updatePost(Post post) {
	this.deletePost(post);
	this.indexPost(post);
    }

    /**
     * @param post
     */
    public void deletePost(Post post) {
	Term term = new Term("postId", String.valueOf(post.getId()));
	deleteUsingTerm(term);
    }

    /**
     * @param postId
     */
    public void deletePost(Long postId) {
	Term term = new Term("postId", String.valueOf(postId));
	deleteUsingTerm(term);
    }

    private IndexWriter makeWriter() throws IOException {
	IndexWriter writer;
	if (IndexReader.indexExists(path)) {
	    writer = new IndexWriter(path, analyzer, false);
	} else {
	    writer = new IndexWriter(path, analyzer, true);
	}
	return writer;
    }

    
    
    public void indexWiki(Wiki wiki) {
	Document document = wikiToDocument(wiki);
	indexDocument(document);
    }

    public Document wikiToDocument(Wiki wiki) {
	Document document = new Document();
	document.add(new Field("wikiId", String.valueOf(wiki.getId()),Field.Store.YES, Field.Index.UN_TOKENIZED));
	document.add(new Field("wikiWord", wiki.getWord(), Field.Store.YES, Field.Index.TOKENIZED));
	document.add(new Field("wikiBody", wiki.getBody(), Field.Store.YES,Field.Index.TOKENIZED));
	return document;
    }

    public void updateWiki(Wiki wiki) {
	this.deleteNoticia(wiki.getId());
	this.indexWiki(wiki);
    }

    public void deleteWiki(Long id) {
	Term term = new Term("wikiId", String.valueOf(id));
	deleteUsingTerm(term);
    }
    
    
    private void indexDocument(Document doc) {
	try {
	    synchronized (monitor) {
		IndexWriter writer = makeWriter();
		writer.addDocument(doc);
		if (optimize) {
		    writer.optimize();
		}
		writer.close();
	    }
	} catch (Exception e) {
	    if (logger.isDebugEnabled()) {
		logger.debug("A error occur when indexing document", e);
	    }
	    throw new LuceneIndexerException(e);
	}

    }

    /**
     * @param term
     */
    private void deleteUsingTerm(Term term) {

	try {
	    synchronized (monitor) {
		IndexReader reader = IndexReader.open(this.path);
		// reader.delete(term);
		reader.deleteDocuments(term);
		reader.close();
	    }
	} catch (IOException ioex) {
	    if (logger.isDebugEnabled()) {
		logger.debug("A error occur when deleting document", ioex);
	    }
	    throw new LuceneIndexerException(ioex);
	}
    }

    private void initialize() throws IOException {

	if (!initialized && createNew) {

	    logger.info("Initializing lucene index at " + path);

	    IndexWriter writer = new IndexWriter(path, analyzer, createNew);
	    writer.addDocument(new Document());
	    writer.close();

	}
	logger.info("Lucene indexer was successful initialized.");
	initialized = true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
	ToStringStyle style = ToStringStyle.MULTI_LINE_STYLE;
	return ToStringBuilder.reflectionToString(this, style);
    }

    /*
     * public void createIndex(List posts) {
     * 
     * IndexWriter writer = null;
     * 
     * synchronized (monitor) { try { writer = new IndexWriter(path, analyzer,
     * true); for (Iterator iter = posts.iterator(); iter.hasNext();) { Post
     * post = (Post) iter.next(); Document doc = postToDocument(post);
     * writer.addDocument(doc); } writer.optimize(); } catch (IOException e) {
     * if (logger.isDebugEnabled()) {
     * logger.debug("A error occur when creating index", e); } throw new
     * LuceneIndexerException(e); } finally { if(writer != null) { try {
     * writer.close(); } catch (IOException e) { // nothing to do? logger, maybe
     * } } } } }
     */
}
