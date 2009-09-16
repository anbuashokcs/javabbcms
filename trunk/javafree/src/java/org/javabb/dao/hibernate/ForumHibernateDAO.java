package org.javabb.dao.hibernate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IForumDAO;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.DateUtil;
import org.javabb.infra.Paging;
import org.javabb.transaction.PostTransaction;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.Forum;
import org.javabb.vo.Post;
import org.javabb.vo.Topic;
import org.javabb.vo.User;

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
 * $Id: ForumHibernateDAO.java,v 1.1 2009/05/11 20:26:59 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class ForumHibernateDAO extends HibernateDAO implements IForumDAO {

    private final Log log = LogFactory.getLog(ForumHibernateDAO.class);
    
//  Dependency Injection
    private PostTransaction postTransaction;
    public void setPostTransaction(PostTransaction postTransaction) {
        this.postTransaction = postTransaction;
    }
    
//  Dependency Injection
    private UserTransaction userTransaction;
    public void setUserTransaction(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }
    
    /**
     * @see org.javabb.dao.entity.IForumDAO#load(java.lang.Long)
     */
    public Forum load(Long id) {
        return (Forum) load(Forum.class, id);
    }

    /**
     * @see org.javabb.dao.entity.IForumDAO#create(org.javabb.vo.Forum)
     */
    public Forum insertForum(Forum forum) {
       getHibernateTemplate().saveOrUpdate(forum);
       return null;
    }
    
    /**
     * @see org.javabb.dao.entity.IForumDAO#findAll()
     */
    public List findAll() {
        return findAll(Forum.class, "o.forumOrder ASC", ALL_PAGES, ALL_PAGES);
    }

    public List findByCategoryOrderAsc(Long id) {
        return this.findAll(Forum.class, 
        		new String[]{"category.idCategory"},
				new String[]{""+id},
				new String[]{"forumOrder"},
				new String[]{"asc"});
    }
    
    
    /**
     * @see org.javabb.dao.entity.IForumDAO#findByCategory(java.lang.Long)
     */
    public List findByCategory(Long id) {
        return findByAttribute(Forum.class, "o.category.idCategory", id);
    }
    
    /**
     * @see org.javabb.dao.entity.IForumDAO#update(org.javabb.vo.Forum)
     */
    public void update(Forum forum) {
        getHibernateTemplate().update(forum);
    }

    /**
     * @see org.javabb.dao.entity.IForumDAO#countAllForums()
     */
    public int countAllForums() {
        return countRows(Forum.class, "idForum");
    }

    /**
     * @param forum
     * @param forumTo
     * @throws Exception
     * @see org.javabb.dao.entity.IForumDAO#transferForum(org.javabb.vo.Forum, int)
     */
    public void transferForum(Forum forum, int forumTo) throws Exception {
        executeSQL("update jbb_topics set id_forum = " + forumTo
            + " where id_forum = "
            + forum.getIdForum());
    }

    /**
     * Delete all records connected at Forum table
     * @param forum
     * @throws Exception
     */
    public void deleteForum(Forum forum) throws Exception {
        deleteFrom("from org.javabb.vo.Post as p where p.topic.forum.idForum = " + forum.getIdForum());
        deleteFrom("from org.javabb.vo.Topic as t where t.forum.idForum = " + forum.getIdForum());
        deleteFrom("from org.javabb.vo.Forum as f where f.idForum = " + forum.getIdForum());
    }

    /**
     * @see org.javabb.dao.entity.IForumDAO#refreshForum(java.lang.Long)
     */
    public void refreshForum(Long forumId) {

        log.debug("Refreshing forums...");
        
        Forum forum = load(forumId);

        // Number of topics
        String customHql = "Topic as t where t.forum.idForum=" + forum.getIdForum();

        Integer cntTopics = countRowsOfTable(customHql, "t.idTopic");
        forum.setTopicCount(new Long(cntTopics.intValue()));

        // Number of posts

        customHql = "Post as p where p.topic.forum.idForum=" + forum.getIdForum();
        Integer postCount = countRowsOfTable(customHql, "p.idPost");
        forum.setPostCount(new Long(postCount.intValue()));

        // Last post of forum
        String[] whereField = { "topic.forum.idForum" };
        String[] whereValue = { "" + forum.getIdForum() };
        String[] orderField = { "postDate" };
        String[] orderValue = { "desc" };

        Post lastPost = null;
        List lstPosts = findAll(Post.class, whereField, whereValue, orderField, orderValue, 0, 1);

        if (!lstPosts.isEmpty()) {
            lastPost = (Post) lstPosts.get(0);
            //if have a post in this forum
            forum.setLastPostId(lastPost.getId());
            forum.setLastPostUserId(lastPost.getUser().getId());
            forum.setLastPostUserName(lastPost.getUser().getUser());
            forum.setLastPostDate(lastPost.getPostDate());
        } else {
        	//No have post
            forum.setLastPostId(null);
            forum.setLastPostUserId(null);
            forum.setLastPostUserName(null);
            forum.setLastPostDate(null);
        }

        lstPosts = null;

        log.debug("Forums: ok!");
        
        /** TODO CALCULAR A PÁGINA DO ÚLTIMO POST* */
        // forum.setLastPagePost(new Long(totalPages));
        // update(forum);
    }

    /**
     * @see org.javabb.dao.entity.IForumDAO#refreshTopic(java.lang.Long)
     */
    public void refreshTopic(Long topicId) {

        log.debug("Refreshing topics...");
        // XXX
        Topic topic = new Topic();
        topic.setId(topicId);
        topic = (Topic) load(topic);

        Forum forum = topic.getForum();
        forum = (Forum) load(forum);

        String customHql = "Topic as t where t.forum.idForum=" + forum.getIdForum();
        Integer cntTopics = countRowsOfTable(customHql, "t.idTopic");
        forum.setTopicCount(new Long(cntTopics.intValue()));

        String hqlRowsByTopic = "Post as p where p.topic.idTopic=" + topicId;
        Integer cntPosts = countRowsOfTable(hqlRowsByTopic, "p.idPost");
        topic.setRespostas(new Integer(cntPosts.intValue() - 1));
        
        Post post = postTransaction.findByTopicDesc(topic);
        
        User user = null;
        if(post.getUser().getUser() == null){
            user = userTransaction.getUser(post.getUser().getIdUser());
            topic.setLastPostUserName(user.getUser());
        } else {
            topic.setLastPostUserName(post.getUser().getUser());
        }

        topic.setLastPostId(post.getId());
        topic.setLastPostDate(post.getPostDate());
        topic.setLastPostUserId(post.getUser().getIdUser());
        
        Integer pageLastPost = postTransaction.getPageOfLastPostByTopic(topic);
        topic.setPageLastPost(pageLastPost);

        log.debug("Topics: ok!");
    }

    /**
     * @see org.javabb.dao.entity.IForumDAO#refreshPost(java.lang.Long)
     */
    public void refreshPost(Long postId) {

        log.debug("Refreshing posts...");
        // XXX
        Post post = new Post();
        post.setId(postId);
        post = (Post) load(post);

        Topic topic = post.getTopic();
        topic = (Topic) load(topic);
        Forum forum = topic.getForum();
        forum = (Forum) load(forum);

        String customHql = "Post as p where p.topic.forum.idForum=" + forum.getIdForum();
        Integer postCount = countRowsOfTable(customHql, "p.idPost");
        forum.setPostCount(new Long(postCount.intValue()));

        // Last post of forum
        String[] whereField = { "topic.forum.idForum" };
        String[] whereValue = { "" + forum.getIdForum() };
        String[] orderField = { "postDate" };
        String[] orderValue = { "desc" };

        Post lastPost = null;
        List lstPosts = findAll(Post.class, whereField, whereValue, orderField, orderValue, 0, 1);

        if (!lstPosts.isEmpty()) {
            lastPost = (Post) lstPosts.get(0);
        }
        lstPosts = null;

        User usr = (User) load(lastPost.getUser());

        forum.setLastPostId(lastPost.getId());
        forum.setLastPostUserId(usr.getIdUser());

        forum.setLastPostUserName(usr.getUser());
        forum.setLastPostDate(lastPost.getPostDate());

        usr = null;

        // past page by topic
        String[] whereFieldPage = { "topic.idTopic" };
        String[] whereValuePage = { topic.getIdTopic().toString() };

        // PAGING ** Obtendo informações
        int rowsPerPage = ConfigurationFactory.getConf().postsPage.intValue();
        int nroRecords = countRowsByWhere("Post", "idPost", whereFieldPage, whereValuePage).intValue();
        int totalPages = Paging.getNroPages(rowsPerPage, nroRecords);

        forum.setLastPagePost(new Long(totalPages));
        
        log.debug("Posts: ok!");
    }


	/**
	 **TODO CHANGE THIS METHOD TO HQL
	 * Get all id foruns with unread topics
	 * @param readTopics - Topics reads
	 * @param lastUserVisit - Last user visit of user
	 * @param userId - id of user
	 * @see org.javabb.dao.entity.IForumDAO#obtainUnreadForuns(Set, Date)
	 */
	public List obtainUnreadForuns(Set readTopics, Date lastUserVisit, Long userId) throws Exception{
		Iterator itTopics = readTopics.iterator();
		List forumIds = new ArrayList();
		
		String lastUserStr = DateUtil.dateFormat(lastUserVisit, "yyyy-MM-dd HH:mm:ss");
		
		if(userId == null){
			userId = new Long(0);
		}
		
		/*TODO CHANGE TO HQL*/
		String sql = " SELECT distinct(t.id_forum) " +
					"  FROM jbb_posts p, jbb_topics t " +
					"  WHERE p.id_post = last_post_id " +
					"  AND p.data_post > '" + lastUserStr + "'" +
					"  AND t.last_post_user_id <> " + userId;

		while(itTopics.hasNext()){
			Long topicId = (Long)itTopics.next();
			sql += " AND t.id_topic <> " +  topicId;
		}


		logger.debug("native:" + sql);
		
		ResultSet rs = this.getSession().connection().createStatement().executeQuery(sql);
		while(rs.next()){
			forumIds.add(new Long(rs.getLong(1)));
		}
		return forumIds;
	}


	
}
