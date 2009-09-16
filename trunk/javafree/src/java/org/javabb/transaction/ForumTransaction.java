package org.javabb.transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.javabb.component.feed.ItenFeed;
import org.javabb.component.feed.ReadRSS;
import org.javabb.dao.entity.IForumDAO;
import org.javabb.dao.entity.IRefreshStatsDAO;
import org.javabb.dao.entity.IUserRankDAO;
import org.javabb.infra.Configuration;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.vh.ForumConfigView;
import org.javabb.vh.integration.FeedVH;
import org.javabb.vo.Category;
import org.javabb.vo.Forum;

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
 * $Id: ForumTransaction.java,v 1.38.2.2.2.2.2.7 2008/10/07 23:06:12
 * daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a>
 */
public class ForumTransaction extends Transaction {

	private IForumDAO _forumDAO;

	private IRefreshStatsDAO _refreshStatsDAO;

	private IUserRankDAO _userRankDAO;

	public void setForumDAO(IForumDAO forumDAO) {
		this._forumDAO = forumDAO;
	}

	public void setUserRankDAO(IUserRankDAO rankDAO) {
		_userRankDAO = rankDAO;
	}

	/**
	 * @param refreshStatsDAO
	 *            The refreshStatsDAO to set.
	 */
	public void setRefreshStatsDAO(IRefreshStatsDAO refreshStatsDAO) {
		_refreshStatsDAO = refreshStatsDAO;
	}

	/**
	 * @param id
	 * @return forum
	 */
	public Forum loadForum(Long id) {
		return _forumDAO.load(id);
	}

	/**
	 * Obtém todos fórums e seus últimos posts
	 * 
	 * @return .
	 * @throws Exception
	 */
	public List findAll() throws Exception {
		return _forumDAO.findAll();
	}

	/**
	 * Obtém todos fórums e seus últimos posts
	 * 
	 * @param category
	 *            - categoria do forum
	 * @return - Lista contendo os foruns de uma categoria
	 * @throws Exception
	 */
	public List findAll(Category category) throws Exception {
		return _forumDAO.findByCategory(category.getId());
	}

	/**
	 * @return .
	 * @throws Exception
	 */
	public Long findNroTotalForuns() throws Exception {
		return new Long(_forumDAO.countAllForums());
	}

	/**
	 * Deleta posts, tópicos e fórum
	 * 
	 * @param forum
	 * @throws Exception
	 */
	public void deleteForum(Forum forum) throws Exception {
		_forumDAO.deleteForum(forum);
	}

	/**
	 * @param forum
	 * @param forumTo
	 * @throws Exception
	 */
	
	public void transferForum(Forum forum, int forumTo) throws Exception {
		_forumDAO.transferForum(forum, forumTo);
	}

	/**
	 * Refresh the information of Forum
	 * 
	 * @param forumId
	 */
	
	public void refreshForum(Long forumId) {
		if (forumId != null) {
			_refreshStatsDAO.refreshForum(forumId);
		}
	}

	/**
	 * Refresh the information of Topic count at forum
	 * 
	 * @param topicId
	 */
	
	public void refreshTopic(Long topicId) {
		if (topicId != null) {
			_refreshStatsDAO.refreshTopic(topicId);

		}
	}

	/**
	 * Refresh the information of Post count at forum
	 * 
	 * @param postId
	 */
	
	public void refreshPost(Long postId) {
		if (postId != null) {
			_refreshStatsDAO.refreshPost(postId);
		}
	}

	/**
	 * @param forum
	 */
	
	public void update(Long forumId, Forum forum) {
		Forum forumToUpdate = this.loadForum(forumId);
		forumToUpdate.setDescricao(forum.getDescricao());
		forumToUpdate.setCategory(forum.getCategory());
		forumToUpdate.setNome(forum.getNome());
	}

	/**
	 * Insert forum
	 * 
	 * @param forum
	 * @return
	 */
	
	public Forum insertForum(Forum forum) {
		Integer orderForum = new Integer(_forumDAO.countAllForums() + 1);
		forum.setForumOrder(orderForum);
		forum.setForumStatus(new Integer(0));
		forum = _forumDAO.insertForum(forum);

		return forum;
	}

	/**
	 * List all buttons languages available
	 * 
	 * @return
	 */
	public List listButtons() {
		List languages = new ArrayList();

		String btnPath = Configuration.realPath + File.separator + "forum"
				+ File.separator + "images" + File.separator + "buttons";

		File[] btnDir = new File(btnPath).listFiles();
		for (int i = 0; i < btnDir.length; i++) {
			String btnDirName = btnDir[i].getName();
			if (!"CVS".equalsIgnoreCase(btnDirName)) {
				languages.add(btnDirName);
			}
		}

		return languages;
	}

	/**
	 * Save the JavaBB basic configurations through of Adm Painel
	 * 
	 * @param forum
	 */
	public void saveConfigForum(ForumConfigView forum) {
		try {
			Properties properties = new Properties();

			String javabbProperties = Configuration.realPath + File.separator
					+ "WEB-INF" + File.separator + "appconf" + File.separator
					+ "javabb.properties";

			properties.load(new FileInputStream(javabbProperties));
			FileOutputStream out = new FileOutputStream(javabbProperties);

			// Update the property file

			String url = forum.getDomain();
			if ("/".equals(url.substring(url.length() - 1, url.length()))) {
				properties.put("config.forum.domain", forum.getDomain());
			} else {
				properties.put("config.forum.domain", forum.getDomain() + "/");
			}
			properties.put("config.forum.forum.name", forum.getForumName());
			properties.put("config.forum.date.format", forum.getDateFormat());
			properties.put("config.forum.time.format", forum.getTimeFormat());
			properties.put("config.forum.topics.page", forum.getTopicsPage());
			properties.put("config.forum.posts.page", forum.getPostsPage());
			properties.put("config.forum.button.lang", forum.getButtonLang());

			properties.put("config.forum.admin.mail", forum.getAdminMail());
			properties.put("config.email.notify.topic", forum.getNotifyTopic());
			properties
					.put("config.forum.smtp.server.host", forum.getSmtpHost());
			properties
					.put("config.forum.smtp.server.user", forum.getSmtpUser());
			properties.put("config.forum.smtp.server.senha", forum
					.getSmtpPassword());
			properties.put("config.forum.flood_control", forum
					.getFloodControl());
			properties.put("config.forum.posts.announce.text", forum
					.getForumAnnounceText());
			properties.put("config.forum.captcha.active", forum
					.getActiveCaptcha());

			properties.store(out, "JavaBB Property File");
			out.close();

			// Refreshing the static instance of Configuration
			ConfigurationFactory.refreshConfig();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sort foruns
	 * 
	 * @param idCategory
	 *            - Id of category that forum has
	 * @param destOrder
	 *            - Position destination of forum
	 * @param position
	 *            - Current position of fórum
	 * @throws Exception
	 */
	
	public void sortForuns(Long idCategory, Integer destOrder, Integer position)
			throws Exception {

		List foruns = _forumDAO.findByCategoryOrderAsc(idCategory);
		LinkedList sortForum = new LinkedList();
		for (int i = 0; i < foruns.size(); i++) {
			Forum forumAdd = (Forum) foruns.get(i);
			forumAdd.setForumOrder(new Integer(i));
			sortForum.add(forumAdd);
		}

		// GC helping
		foruns.clear();
		foruns = null;

		Forum t = (Forum) sortForum.get(position.intValue());

		sortForum.remove(position.intValue());
		sortForum.add(destOrder.intValue(), t);

		Forum forumUpd = null;
		for (int i = 0; i < sortForum.size(); i++) {
			Forum forum = (Forum) sortForum.get(i);
			// New instance of this object to reflect the update on the
			// transaction of spring
			forumUpd = new Forum();
			forumUpd = _forumDAO.load(forum.getIdForum());
			forumUpd.setForumOrder(new Integer(i));
		}

		// GC helping
		sortForum.clear();
		sortForum = null;
	}

	/**
	 * Set at UserContext all forum ids
	 */
	public void setUnreadForumIds() throws Exception {
		/*
		 * Set topics = UserContext.getContext().getReadTopicIds(); Date
		 * lastVisit = UserContext.getContext().getLastVisitTimestamp(); Long
		 * userId = new Long(0); if (UserContext.getContext().getUser() != null
		 * && UserContext.getContext().getUser().getIdUser() != null) { userId =
		 * UserContext.getContext().getUser().getIdUser(); }
		 * 
		 * List unreadForumIds = _forumDAO.obtainUnreadForuns(topics, lastVisit,
		 * userId); UserContext.getContext().setUnreadForumIds(unreadForumIds);
		 */
	}

	/**
	 * Refresh all userRank table of all forums
	 * 
	 * @throws Exception
	 */
	
	public void refreshForumUserRank() throws Exception {
		_userRankDAO.cleanAllUserRank();
		List forums = findAll();
		for (int i = 0; i < forums.size(); i++) {
			Forum forum = (Forum) forums.get(i);
			_userRankDAO.refreshUserRankByForum(forum.getId());
		}
	}

	// Feed integration
	@SuppressWarnings("unchecked")
	public List feedRefresh(String feedURL) throws Exception {
		List lstFeedsRet = new ArrayList();
		List feeds = ReadRSS.getFeeds(feedURL);
		if (feeds != null) {
			for (int i = 0; i < feeds.size(); i++) {
				ItenFeed feed = (ItenFeed) feeds.get(i);
				FeedVH cnt = new FeedVH();
				cnt.setFeedTitle(feed.getTitle());
				cnt.setFeedURL(feed.getLink());
				lstFeedsRet.add(cnt);
			}
		}
		return lstFeedsRet;
	}

	public List findForumByCatId(Long catId) throws Exception {
		return _forumDAO.findByCategoryOrderAsc(catId);
	}

}