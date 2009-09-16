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
package org.javabb.dao.lucene;

import java.io.IOException;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateField;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.RAMDirectory;

import org.javabb.lucene.analysis.PortugueseAnalyzer;
import org.javabb.lucene.index.Indexer;
import org.javabb.vo.Post;
import org.javabb.vo.Topic;


/**
 *
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 *
 * @since 22/04/2005
 *
 * @version $Id: IndexerTest.java,v 1.1 2009/05/11 20:27:09 daltoncamargo Exp $
 */
public class IndexerTest extends TestCase {

	Indexer indexer;
	RAMDirectory directory;


	public static void main( String[] args ) {

		//junit.swingui.TestRunner.run(IndexerTest.class);
	}

	/**
	 * Constructor for IndexerTest.
	 * @param arg0
	 */
	public IndexerTest ( String name ) {

		super(name);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		super.setUp();


		Analyzer analyzer = new PortugueseAnalyzer();
		directory = new RAMDirectory();

		indexer = new Indexer(analyzer, directory, true, true);
	}

	public void testIndex() throws Exception {

		Post post = getPost();
		indexer.indexPost(post);

		verifyDocs();

	}

	/**
	 * @param post
	 * @throws IOException
	 */
	private void verifyDocs( ) throws IOException {

		Post post = getPost();

		IndexReader reader = IndexReader.open(directory);

		Assert.assertTrue(reader.numDocs() > 0);

		Document doc = reader.document(1);

		Assert.assertNotNull(doc);
		Assert.assertEquals(doc.get("postId"), post.getId().toString());
		Assert.assertNotNull(doc.get("date"));

		reader.close();
	}

	public void testUpdate() throws Exception {

		Post post = getPost();
		indexer.indexPost(post);

		verifyDocs();

		long oldDate = post.getPostDate().getTime();
		long newTime = new Date(System.currentTimeMillis() - 10000).getTime();

		post.setPostDate(new Date(newTime));

		indexer.updatePost(post);

		verifyDocs();

		IndexReader reader = IndexReader.open(directory);

		Document doc = reader.document(1);

		Date date = DateField.stringToDate(doc.get("date"));

		reader.close();

		Assert.assertEquals(newTime, date.getTime());
		Assert.assertFalse(oldDate == date.getTime());

	}

	/*
	 * Class under test for void delete(Post)
	 */
	public void testDeletePost() throws Exception {

		Post post = getPost();
		indexer.indexPost(post);

		verifyDocs();

		indexer.deletePost(post);

		IndexReader reader = IndexReader.open(directory);

		Assert.assertTrue(reader.hasDeletions());
		Assert.assertTrue(reader.isDeleted(1));

		reader.close();
	}

	/*
	 * Class under test for void delete(Long)
	 */
	public void testDeleteLong() throws Exception {

		Post post = getPost();
		indexer.indexPost(post);

		verifyDocs();

		indexer.deletePost(post.getId());

		IndexReader reader = IndexReader.open(directory);

		Assert.assertTrue(reader.hasDeletions());
		Assert.assertTrue(reader.isDeleted(1));

		reader.close();
	}

	private Post getPost() {

		Post post = new Post();

		post.setId(new Long(10));
		post.setIdPost(new Long(10)); // ???
		post.setPostBody("This is a simple post to test indexer class");
		post.setPostDate(new Date());
		post.setSubject("Post subject? Java, of course!");

		Topic topic = new Topic();
		topic.setTitleTopic("The Topic Title");

		post.setTopic(topic);

		return post;
	}
}
