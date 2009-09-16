package org.javabb.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IRefreshStatsDAO;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.Paging;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
 * $Id: JdbcRefreshStatsDAO.java,v 1.1 2009/05/11 20:27:13 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a>
 */
public class JdbcRefreshStatsDAO extends JdbcSuper implements IRefreshStatsDAO {

    private final Log log = LogFactory.getLog(JdbcRefreshStatsDAO.class);

    public void refreshForum(Long forumId) {

        log.debug("Refreshing forums...");

        StringBuffer sql = new StringBuffer();
        // Attributes
        int topicCount = 0;
        int postCount = 0;

        // Last post
        Long postId = new Long(0);
        Timestamp postDate = null;
        Long userId = new Long(0);
        String userName = "";

        try {
            // Number of topics
            sql.append(" SELECT COUNT(id_topic) ");
            sql.append(" 	FROM jbb_topics WHERE id_forum = " + forumId);

            ResultSet rsTopicCount = this.getConnection().createStatement().executeQuery(sql.toString());

            rsTopicCount.next();
            topicCount = rsTopicCount.getInt(1);
            rsTopicCount.close();
            rsTopicCount = null;

            // Number of Posts..
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT COUNT(jbb_posts.id_post) FROM jbb_posts,jbb_topics, jbb_forum ");
            sql.append(" WHERE jbb_posts.id_topic = jbb_topics.id_topic AND ");
            sql.append(" jbb_topics.id_forum = jbb_forum.id_forum AND ");
            sql.append(" jbb_forum.id_forum = " + forumId);

            ResultSet rsPostCount = this.getConnection().createStatement().executeQuery(sql.toString());
            rsPostCount.next();
            postCount = rsPostCount.getInt(1);
            rsPostCount.close();
            rsPostCount = null;

            // Last post by Forum..
            sql = null;
            sql = new StringBuffer();
            sql.append("	SELECT  ");
            sql.append("		jbb_posts.id_post, jbb_posts.data_post, jbb_users.id_user, jbb_users.user_name");
            sql.append("	FROM jbb_posts, jbb_topics, jbb_forum, jbb_users");
            sql.append("	WHERE jbb_posts.id_topic = jbb_topics.id_topic AND");
            sql.append("		jbb_topics.id_forum = jbb_forum.id_forum AND");
            sql.append("		jbb_posts.id_user = jbb_users.id_user AND");
            sql.append("		jbb_forum.id_forum = " + forumId);
            sql.append("	ORDER BY jbb_posts.data_post DESC");
            sql.append("	LIMIT 1");

            ResultSet rsLastPost = this.getConnection().createStatement().executeQuery(sql.toString());

            if (rsLastPost.next()) {
                postId = new Long(rsLastPost.getLong(1));
                postDate = rsLastPost.getTimestamp(2);
                userId = new Long(rsLastPost.getLong(3));
                userName = rsLastPost.getString(4);
            }

            rsLastPost.close();
            rsLastPost = null;

            // Forum updating...
            sql = null;
            sql = new StringBuffer();
            sql.append(" UPDATE jbb_forum SET post_count = ?, topic_count = ?, last_post_id = ?, ");
            sql.append("   last_post_user_id = ?, last_post_user_name = ?, last_post_date = ? ");
            sql.append(" WHERE id_forum =  ?");

            PreparedStatement pStmt = this.getConnection().prepareStatement(sql.toString());

            pStmt.setInt(1, postCount);
            pStmt.setInt(2, topicCount);
            pStmt.setInt(3, postId.intValue());
            pStmt.setInt(4, userId.intValue());
            pStmt.setString(5, userName);
            pStmt.setTimestamp(6, postDate);
            pStmt.setInt(7, forumId.intValue());

            pStmt.executeUpdate();

            sql = null;
            pStmt.close();
            pStmt = null;

        } catch (SQLException e) {
            log.error("SQL Exception error:" + e.getMessage());
        } catch (Exception e) {
            log.error("Exception error:" + e.getMessage());
        }

        log.debug("Forums: ok!");
    }

    public void refreshTopic(Long topicId) {

        log.debug("Refreshing topics...");
        StringBuffer sql = new StringBuffer();

        // Attributes
        int forumId = 0;
        int topicCount = 0;
        int postCount = 0;

        // Last post
        Long postId = new Long(0);
        Timestamp postDate = null;
        Long userId = new Long(0);
        String userName = "";
        int totalPages = 0;

        try {
            // Get forum details
            sql.append(" SELECT id_forum ");
            sql.append(" 	FROM jbb_topics WHERE id_topic = " + topicId);

            ResultSet rsTopicDetail = this.getConnection().createStatement().executeQuery(sql.toString());

            rsTopicDetail.next();
            forumId = rsTopicDetail.getInt(1);
            rsTopicDetail.close();
            rsTopicDetail = null;

            // Number of topics
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT COUNT(id_topic) ");
            sql.append(" 	FROM jbb_topics WHERE id_forum = " + forumId);

            ResultSet rsTopicCount = this.getConnection().createStatement().executeQuery(sql.toString());

            rsTopicCount.next();
            topicCount = rsTopicCount.getInt(1);
            rsTopicCount.close();
            rsTopicCount = null;

            // Forum updating.....
            sql = null;
            sql = new StringBuffer();
            sql.append(" UPDATE jbb_forum SET topic_count = ? ");
            sql.append(" WHERE id_forum =  ?");

            PreparedStatement pStmt = this.getConnection().prepareStatement(sql.toString());

            pStmt.setInt(1, topicCount);
            pStmt.setInt(2, forumId);

            pStmt.executeUpdate();

            pStmt.close();
            pStmt = null;

            // Topic model updating.....
            sql = null;
            sql = new StringBuffer();
            sql.append(" UPDATE jbb_topics SET topic_model = 0 ");
            sql.append(" WHERE topic_model is null");

            pStmt = this.getConnection().prepareStatement(sql.toString());

            pStmt.executeUpdate();

            pStmt.close();
            pStmt = null;
            
            
            // Number of posts
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT COUNT(id_post) ");
            sql.append(" 	FROM jbb_posts WHERE id_topic = " + topicId);

            ResultSet rsPostsCount = this.getConnection().createStatement().executeQuery(sql.toString());

            rsPostsCount.next();
            postCount = rsPostsCount.getInt(1); //Answers
            rsPostsCount.close();
            rsPostsCount = null;

            // Last post by Topic..
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT jbb_posts.id_post, jbb_posts.data_post, jbb_users.user_name, jbb_users.id_user ");
            sql.append(" FROM jbb_posts, jbb_users ");
            sql.append(" WHERE id_topic = " + topicId);
            sql.append(" AND jbb_posts.id_user = jbb_users.id_user ");
            sql.append(" ORDER BY data_post ");
            sql.append(" DESC LIMIT 1 ");

            ResultSet rsLastPost = this.getConnection().createStatement().executeQuery(sql.toString());

            if (rsLastPost.next()) {
                postId = new Long(rsLastPost.getLong(1));
                postDate = rsLastPost.getTimestamp(2);
                userName = rsLastPost.getString(3);
                userId = new Long(rsLastPost.getLong(4));
            }

            rsLastPost.close();
            rsLastPost = null;

            // Last post page
            int rowsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
            totalPages = Paging.getNroPages(rowsPerPage, postCount);

            // Topic updating...
            sql = null;
            sql = new StringBuffer();
            sql.append(" UPDATE jbb_topics SET last_post_user_name = ?, last_post_id = ?, last_post_date = ?, ");
            sql.append("   last_post_user_id = ?, last_post_page = ? ");
            sql.append(" WHERE id_topic =  ?");

            pStmt = this.getConnection().prepareStatement(sql.toString());

            pStmt.setString(1, userName);
            pStmt.setInt(2, postId.intValue());
            pStmt.setTimestamp(3, postDate);
            pStmt.setInt(4, userId.intValue());
            pStmt.setInt(5, totalPages);
            pStmt.setInt(6, topicId.intValue());

            pStmt.executeUpdate();
            
            sql = null;
            pStmt.close();
            pStmt = null;

        } catch (SQLException e) {
            log.error("SQL Exception error:" + e.getMessage());
        }

        log.debug("Topics: ok!");

    }

    public void refreshPost(Long postId) {

        log.debug("Refreshing posts...");

        StringBuffer sql = new StringBuffer();

        //	Attributes
        int topicId = 0;
        int forumId = 0;
        int userId = 0;
        String userName = "";
        int topicCount = 0;
        int postCount = 0;
        int postCountByTopic = 0;
        Timestamp postDate = null;
        int totalPages = 0;

        try {
            // Get forum details
            sql.append(" SELECT jbb_topics.id_topic, jbb_topics.id_forum");
            sql.append(" FROM jbb_topics, jbb_posts ");
            sql.append(" WHERE jbb_topics.id_topic = jbb_posts.id_topic");
            sql.append(" AND jbb_posts.id_post = " + postId);
            sql.append(" LIMIT 1");

            ResultSet rsPostDetail = this.getConnection().createStatement().executeQuery(sql.toString());

            rsPostDetail.next();
            topicId = rsPostDetail.getInt(1);
            forumId = rsPostDetail.getInt(2);
            rsPostDetail.close();
            rsPostDetail = null;

            // Number of posts by topic
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT COUNT(id_post) ");
            sql.append(" FROM jbb_posts");
            sql.append(" WHERE id_topic = " + topicId);

            ResultSet rsPostCountByTopic = this.getConnection().createStatement().executeQuery(sql.toString());

            rsPostCountByTopic.next();
            postCountByTopic = rsPostCountByTopic.getInt(1);
            rsPostCountByTopic.close();
            rsPostCountByTopic = null;

            // Number of posts by forum
            sql = null;
            sql = new StringBuffer();
            sql.append(" SELECT COUNT(id_post) ");
            sql.append(" FROM jbb_posts, jbb_topics ");
            sql.append(" WHERE jbb_posts.id_topic = jbb_topics.id_topic ");
            sql.append(" AND jbb_topics.id_forum = " + forumId);

            ResultSet rsPostsCount = this.getConnection().createStatement().executeQuery(sql.toString());

            rsPostsCount.next();
            postCount = rsPostsCount.getInt(1);
            rsPostsCount.close();
            rsPostsCount = null;

            // Last post by Forum..
            sql = null;
            sql = new StringBuffer();
            sql.append("	SELECT  ");
            sql.append("		jbb_posts.id_post, jbb_posts.data_post, jbb_users.id_user, jbb_users.user_name");
            sql.append("	FROM jbb_posts, jbb_topics, jbb_forum, jbb_users");
            sql.append("	WHERE jbb_posts.id_topic = jbb_topics.id_topic AND");
            sql.append("		jbb_topics.id_forum = jbb_forum.id_forum AND");
            sql.append("		jbb_posts.id_user = jbb_users.id_user AND");
            sql.append("		jbb_forum.id_forum = " + forumId);
            sql.append("	ORDER BY jbb_posts.data_post DESC");
            sql.append("	LIMIT 1");

            ResultSet rsLastPost = this.getConnection().createStatement().executeQuery(sql.toString());

            if (rsLastPost.next()) {
                postId = new Long(rsLastPost.getLong(1));
                postDate = rsLastPost.getTimestamp(2);
                userId = rsLastPost.getInt(3);
                userName = rsLastPost.getString(4);
            }

            rsLastPost.close();
            rsLastPost = null;

            // Last post page
            int rowsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
            totalPages = Paging.getNroPages(rowsPerPage, postCountByTopic);

            // Forum updating...
            sql = null;
            sql = new StringBuffer();
            sql.append(" UPDATE jbb_forum SET post_count = ?, last_post_id = ?, ");
            sql.append("   last_post_user_id = ?, last_post_user_name = ?, last_post_date = ?, last_page_post = ? ");
            sql.append(" WHERE id_forum =  ?");

            PreparedStatement pStmt = this.getConnection().prepareStatement(sql.toString());

            pStmt.setInt(1, postCount);
            pStmt.setInt(2, postId.intValue());
            pStmt.setInt(3, userId);
            pStmt.setString(4, userName);
            pStmt.setTimestamp(5, postDate);
            pStmt.setInt(6, totalPages);
            pStmt.setInt(7, forumId);

            pStmt.executeUpdate();

            sql = null;
            pStmt.close();
            pStmt = null;

        } catch (SQLException e) {
            log.error("SQL Exception error:" + e.getMessage());
        }
        
        log.debug("Posts: ok!");
    }

    /**
     * Do nothing
     */
	public void refreshSession(Object obj) {}
   
}