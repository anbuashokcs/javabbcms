package org.javabb.dao.entity;

import java.util.List;

import org.javabb.dao.DAOConstants;
import org.javabb.vo.Forum;
import org.javabb.vo.Post;
import org.javabb.vo.PostFile;
import org.javabb.vo.PostText;
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
 * $Id: IPostDAO.java,v 1.1 2009/05/11 20:26:57 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 * @author <a href="mailto:jackganzha@dev.java.net">Marcos Silva Pereira</a>
 */
public interface IPostDAO extends DAOConstants {

    /**
     * @param id
     * @return post
     */
    public PostText load(Long id);

    /**
     * @return last post
     */
    public Post findLastPost();

    public List findLastPosts(int limit);
    public List findLastPostUsingSQL(int limit) throws Exception;
    
    /**
     * @param topic
     * @return list
     */
    public List findByTopicDesc(Topic topic);
    
    /**
     * @param forum
     * @return list
     */
    public List findByForumDesc(Forum forum);

    /**
     * @param forum
     * @return count
     */
    public Integer findCountOfPostsByForum(Forum forum);

    
    /**
     * Number of rows found into Lucene index
     * @param query - keywords to search rows
     * @return number of rows
     */
    public int getTotalRowsOfLucene(String query, Long forumId);
    
    /**
     * @param query
     * @return post list
     */
    public List search(String query, Long forumId, int pageNumber, int numItems);
    
    
    /**
     * Search using the String with space parameters
     * @param query
     * @return
     */
    public List search(final String query, final int page);

    /**
     * @param idTopic
     * @return post count
     */
    public int countPostsByTopic(Long idTopic);

    /**
     * @param topicId
     * @param rowsPerPage
     * @param pageNumber
     * @return post list
     */
    public List findByTopic(Long topicId, int pageNumber, int rowsPerPage);

    /**
     * @param id
     * @return post list
     */
    public List findByUser( Long userId, int pageNumber, int itemsPerPage );

    public int countPostsByUser(Long userId);
    
    /**
     * @return post list
     */
    public List findUnanswered(int pageNumber, int itemsPerPage);

    public int countUnanswered();    
    
    /**
     * @param post
     */
    public void update(Post post);
    public void updateState(Post post);

    /**
     * @param post
     * @return new post id
     */
    public Long create(Post post);

    /**
     * @param postId
     */
    public void delete(Long postId);

    /**
     * @return post total count
     */
    public int countAllPosts();
    
    
    public PostFile loadPostFile(Long fileId) throws Exception;
    public void deletePostFile(Long fileId) throws Exception;
    public List findEmailRepliesWhenReplyPost(Topic topic, User user) throws Exception;
}
