package org.javabb.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import net.sf.hibernate.type.LongType;

import org.javabb.dao.entity.IPostDAO;
import org.javabb.infra.Paging;
import org.javabb.lucene.index.Indexer;
import org.javabb.lucene.search.LuceneSearcher;
import org.javabb.vo.Forum;
import org.javabb.vo.Post;
import org.javabb.vo.PostFile;
import org.javabb.vo.PostText;
import org.javabb.vo.Topic;
import org.javabb.vo.User;
import org.springframework.orm.hibernate.HibernateCallback;

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

/**
 * $Id: PostHibernateDAO.java,v 1.22.2.2.4.3 2006/03/27 12:13:15 daltoncamargo
 * Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class PostHibernateDAO extends HibernateDAO implements IPostDAO {

	private LuceneSearcher searcher;

	private Indexer indexer;

	/**
	 * @return Returns the searcher.
	 */
	public LuceneSearcher getSearcher() {
		return searcher;
	}

	/**
	 * @param searcher
	 *            The searcher to set.
	 */
	public void setSearcher(LuceneSearcher seacher) {
		this.searcher = seacher;
	}

	/**
	 * @param indexer
	 *            The indexer to set.
	 */
	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}

	/**
	 * @return Returns the indexer.
	 */
	public Indexer getIndexer() {
		return indexer;
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#load(java.lang.Long)
	 */
	public PostText load(Long id) {

		return (PostText) getHibernateTemplate().load(PostText.class, id);
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#create(org.javabb.vo.Post)
	 */
	public Long create(Post post) {
		Long result = (Long) getHibernateTemplate().save(post);
		indexer.indexPost(post);

		invalidateAllCaches();

		return result;
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#delete(java.lang.Long)
	 */
	public void delete(Long postId) {

		getHibernateTemplate().delete(
				"FROM " + Post.class.getName() + " o WHERE o.idPost=?", postId,
				new LongType());

		getHibernateTemplate().delete(
				"FROM " + PostText.class.getName() + " o WHERE o.idPost=?",
				postId, new LongType());

		indexer.deletePost(postId);

		invalidateAllCaches();

	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#countPostsByTopic(java.lang.Long)
	 */
	public int countPostsByTopic(Long idTopic) {

		return countRowsWhere(Post.class, "o.idPost", "o.topic.idTopic=?",
				new Object[] { idTopic });
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findByTopic(java.lang.Long, int, int)
	 */
	public List findByTopic(Long topicId, int pageNumber, int itemsPerPage) {

		return find(Post.class, "o.topic.idTopic=?", new Object[] { topicId },
				"o.idPost ASC", pageNumber, itemsPerPage);
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findByUser(java.lang.Long, int, int)
	 */
	public List findByUser(final Long userId, final int pageNumber,
			final int itemsPerPage) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createCriteria(Post.class) //
						.add(Expression.eq("user.idUser", userId)) //
						.addOrder(Order.desc("idPost")) //
						.setFirstResult((pageNumber - 1) * itemsPerPage) //
						.setMaxResults(itemsPerPage) //
						.list();
			}
		});
	}

	public int countPostsByUser(Long userId) {
		return countRowsWhere(Post.class, "o.idPost", "o.user.idUser=?",
				new Object[] { userId });
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findUnanswered(int, int)
	 */
	public List findUnanswered(int pageNumber, int itemsPerPage) {

		String hql = "SELECT new Post("
				+ "post.topic.id, post.topic.pageLastPost, post.id,"
				+ "post.topic.titleTopic, post.topic.forum.id, post.topic.forum.nome, "
				+ "post.postDate, post.user.id, post.user.user, post.topic.respostas, "
				+ "post.topic.visualizacoes) "
				+ " FROM Post as post where post.topic.respostas = ? ORDER BY post.idPost DESC";

		return find(hql, new Object[] { new Integer(0) }, pageNumber,
				itemsPerPage);
	}

	public int countUnanswered() {
		return countRowsWhere(Post.class, "o.idPost", "o.topic.respostas = ?",
				new Object[] { new Integer(0) });
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#update(org.javabb.vo.Post)
	 */
	public void update(Post post) {

		getHibernateTemplate().update(post);

		indexer.updatePost(post);
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findByTopicDesc(org.javabb.vo.Topic)
	 */
	public List findByTopicDesc(Topic topic) {

		String[] whereField = { "topic.idTopic" };
		String[] whereValue = { String.valueOf(topic.getIdTopic()) };
		String[] orderBy = { "postDate" };
		String[] orderType = { "desc" };

		return findAll(PostText.class, whereField, whereValue, orderBy,
				orderType);
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findLasPosts(int)
	 */
	public List findLastPosts(int limit) {

		String[] orderBy = { "idPost" };
		String[] orderType = { "desc" };
		return findAll(PostText.class, orderBy, orderType, 1, limit);
	}

	@SuppressWarnings("unchecked")
	public List findLastPostUsingSQL(int limit) throws Exception {
		List lst = postCache.getFindLastPostUsingSQL(limit);
		if (lst == null) {
			String sql = "select t.last_post_date lastPostDate, t.title_topic title, t.id_topic id, post_body body, t.last_post_page page, t.last_post_id pid "
					+ "  from jbb_posts p, jbb_posts_text x, "
					+ " (select * from jbb_topics t1 order by t1.last_post_date desc limit "
					+ limit
					+ ") t "
					+ " where t.id_topic = p.id_topic "
					+ "   and t.last_post_id = p.id_post "
					+ "   and p.id_post = x.id_post "
					+ "group by t.id_topic order by t.last_post_date desc";

			ResultSet rs = getSession().connection().createStatement()
					.executeQuery(sql);
			if (rs != null) {
				lst = new ArrayList();
				while (rs.next()) {
					Topic t = new Topic();
					PostText p = new PostText();
					p.setPostBody(rs.getString("body"));
					t.setId(rs.getLong("id"));
					t.setTitleTopic(rs.getString("title"));
					t.setPageLastPost(rs.getInt("page"));
					t.setLastPostId(rs.getLong("pid"));
					t.setLastPostDate(rs.getDate("lastPostDate"));
					p.setTopic(t);
					lst.add(p);
				}
			}
			postCache.setFindLastPostUsingSQL(lst, limit);
		}

		return lst;
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findLastPost()
	 */
	public Post findLastPost() {

		String[] orderBy = { "idPost" };
		String[] orderType = { "desc" };
		Post p = null;
		List lst = findAll(Post.class, orderBy, orderType, 1, 1);

		if ((lst != null) && !lst.isEmpty()) {
			p = (Post) lst.get(0);
		}
		return p;
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#findCountOfPostsByForum(org.javabb.vo.Forum)
	 */
	public Integer findCountOfPostsByForum(Forum forum) {

		String sql = "Post as p where p.topic.forum.idForum ="
				+ forum.getIdForum();

		return this.countRowsOfTable(sql, "p.idPost");

	}

	public int getTotalRowsOfLucene(String query, Long forumId) {
		List ids = searchIdsByQueryAndFields(query, forumId);
		if (ids != null) {
			return ids.size();
		}
		return 0;
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#search(java.lang.String)
	 */
	public List search(String query, Long forumId, int pageNumber,
			int itemsPerPage) {

		int first = Math.max(0, pageNumber - 1) * itemsPerPage;
		first = Math.max(0, first);
		// int limit = Math.max(first, itemsPerPage) + 1;
		int limit = Math.round(pageNumber * itemsPerPage);

		List ids = searchIdsByQueryAndFields(query, forumId);

		// // PAGING
		long postsCount = ids.size();
		int totalPages = Paging.getNroPages(itemsPerPage, postsCount);
		Paging.setPageList(pageNumber, totalPages);

		// return search(ids, 1, 3);
		return search(ids, first, limit);
	}

	/**
	 * 
	 * @param query
	 * @param fields
	 * @return
	 */
	public List searchIdsByQueryAndFields(String query, Long forumId) {
		if (forumId == null) {
			return searcher.search(query, new String[] { "text", "subject" },
					"postId");
		} else {
			String[] fields = new String[] { "text", "subject", "forumId" };
			String[] queries = new String[] { query, query, forumId.toString() };
			return searcher.search(queries, fields);
		}
	}

	/**
	 * @param query
	 * @param fields
	 * @param start
	 * @param limit
	 * 
	 * @return
	 * 
	 * @see org.javabb.dao.entity.IPostDAO#search(java.lang.String)
	 */
	private List search(final List ids, final int start, final int limit) {

		List result = new ArrayList();
		if (!ids.isEmpty()) {
			int _start = Math.max(0, start);
			int _limit = Math.min(limit, ids.size());
			List subListIds = ids.subList(_start, _limit);
			String hql = "SELECT new PostText("
					+ "post.topic.id, post.topic.pageLastPost, post.id,"
					+ "post.topic.titleTopic, post.topic.forum.id, post.topic.forum.nome, "
					+ "post.postDate, post.user.id, post.user.user, post.topic.respostas, "
					+ "post.topic.visualizacoes, post.postBody) "
					+ " FROM PostText as post where post.idPost in";

			hql += "(";
			if (!subListIds.isEmpty()) {
				for (int i = 0; i < subListIds.size(); i++) {
					hql += subListIds.get(i) + ",";
				}
				hql = hql.substring(0, hql.length() - 1);
				hql += ") ORDER BY post.idPost DESC";
				result = getHibernateTemplate().find(hql);
			}
		}

		Comparator sorter = new LuceneComparator(ids);

		Collections.sort(result, sorter);

		return result;

	}

	public List search(final String query, final int page) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(PostText.class);

				String[] tokens = query.split(" ");
				for (int i = 0; i < tokens.length; i++) {
					String token = tokens[i];
					criteria.add(Expression.like("postBody", token));
				}
				// criteria.setMaxResults(postsPerPage);
				return criteria.list();
			}
		});
	}

	/**
	 * @see org.javabb.dao.entity.IPostDAO#countAllPosts()
	 */
	public int countAllPosts() {
		return countRows(Post.class, "idPost");
	}

	private static class LuceneComparator implements Comparator {

		private final List ids;

		private LuceneComparator(List ids) {

			this.ids = ids;

		}

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(final Object o1, final Object o2) {

			final Long id1 = ((Post) o1).getId();
			final Long id2 = ((Post) o2).getId();
			for (int i = 0; i != ids.size(); i++) {
				final Long longId = (Long) ids.get(i);
				if (longId.equals(id1)) {
					return -1;
				}
				if (longId.equals(id2)) {
					return 1;
				}
			}
			return 0;
		}

	}

	public List findByForumDesc(Forum forum) {
		return null;
	}

	public PostFile loadPostFile(Long fileId) throws Exception {
		return (PostFile) getHibernateTemplate().load(PostFile.class, fileId);
	}

	public void deletePostFile(Long fileId) throws Exception {
		Connection cnn = getSession().connection();
		PreparedStatement pstmt = cnn
				.prepareStatement("delete from jbb_posts_files where file_id = ?");
		pstmt.setLong(1, fileId.longValue());
		pstmt.execute();
		cnn.commit();
		// getHibernateTemplate().delete(new PostFile(fileId));
	}

	public void updateState(Post post) {
		getHibernateTemplate().refresh(post);
	}

	/**
	 * Return all posts that must be replied
	 * 
	 * @param topic
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findEmailRepliesWhenReplyPost(Topic topic, User user)
			throws Exception {

		Long topicId = topic.getId();
		Long userId = user.getId();

		/*
		 * String[] whereField = { "topic.id", "user.id", "mailReply" };
		 * String[] whereValue = { topicId, userId, "1" }; String[] orderBy = {
		 * "postDate" }; String[] orderType = { "desc" };
		 */
		String hql = "select distinct p.user.email from Post p where p.topic.id = "
				+ topicId
				+ " and p.user.id != "
				+ userId
				+ " and p.mailReply = 1";
		List emailLists = null;
		try {
			emailLists = getList(hql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return emailLists;

	}

}
