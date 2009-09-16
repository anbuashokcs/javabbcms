package org.javabb.transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.javabb.component.VelocityTemplate;
import org.javabb.dao.entity.IUserDAO;
import org.javabb.dao.entity.IUserSecurityDAO;
import org.javabb.infra.CacheUtils;
import org.javabb.infra.Configuration;
import org.javabb.infra.ConfigurationFactory;
import org.javabb.infra.Constants;
import org.javabb.infra.Email;
import org.javabb.infra.JbbConfig;
import org.javabb.infra.Paging;
import org.javabb.infra.UserContext;
import org.javabb.infra.Utils;
import org.javabb.vo.User;
import org.javabb.vo.UserRank;

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
 * $Id: UserTransaction.java,v 1.34.2.4.2.1.2.12 2007/09/06 21:12:24
 * daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a>
 * @author Ronald Tetsuo Miura
 */
public class UserTransaction extends Transaction {

	private IUserDAO _userDAO;

	// UserRank Caches
	private static List userRanksCache;

	public void setUserDAO(IUserDAO userDAO) {
		_userDAO = userDAO;
	}

	public User getUser(Long userId) {
		return _userDAO.loadUser(userId);
	}

	private IUserSecurityDAO userSecurityDAO;

	public void setUserSecurityDAO(IUserSecurityDAO userSecurityDAO) {
		this.userSecurityDAO = userSecurityDAO;
	}

	/**
	 * @param userId
	 */

	public void sumNumberMsgUser(Long userId) {
		try {
			User user = _userDAO.loadUser(userId);
			long count = user.getUser_posts().longValue();
			count++;
			user.setUser_posts(new Long(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param userId
	 */

	public void subNumberMsgUser(Long userId) {
		User u = _userDAO.loadUser(userId);

		long count = u.getUser_posts().longValue();
		count--;
		u.setUser_posts(new Long(count));
	}

	/**
	 * @param name
	 * @param passwd
	 * @return user
	 */
	public User verifyLogin(String name, String passwd) {
		User user = _userDAO.verificaLogin(name, passwd);
		if (!UserContext.getContext().isAuthenticated()) {
			UserContext.getContext().setUser(user);
		}
		return user;
	}

	/**
	 * @param userId
	 * @param usercode
	 * @return user
	 */
	public User verifyUserCode(Long userId, String usercode) {
		User user = _userDAO.loadByUsercode(usercode);
		if (!UserContext.getContext().isAuthenticated()) {
			UserContext.getContext().setUser(user);
		}
		return user;
	}

	/**
	 * @param username
	 * @return user
	 */
	public User findUserByUserName(String username) {
		return _userDAO.loadByUsername(username);
	}

	public List findUserListByUserName(String username) {
		return _userDAO.loadListByUsername(username);
	}

	/**
	 * @param email
	 * @return user
	 */
	public boolean isEmailValid(String email, Long userId) {
		List users = _userDAO.findByEmail(email, userId);
		return !users.isEmpty();
	}

	/**
	 * @param email
	 * @return user
	 */
	public boolean isEmailValid(String email) {
		List users = _userDAO.findByEmail(email);
		return !users.isEmpty();
	}

	/**
	 * @return user
	 */
	public User lastUserRegistered() {
		return _userDAO.lastUserRegistered();
	}

	/**
	 * Delete an user and move your messages to user Guest - ID=0
	 * 
	 * @param user
	 */

	public void deleteUser(User user) {
		_userDAO.deleteUser(user);
	}

	/**
	 * @param sortBy
	 * @param sortOrder
	 * @param pageNumber
	 * @return list of registered users
	 */
	public List listAllRegisteredUsers(String sortBy, String sortOrder,
			int pageNumber) {

		// PAGING ** Obtendo informações
		int rowsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();
		long nroRecords = _userDAO.countAllUsers();
		long totalPages = Paging.getNroPages(rowsPerPage, nroRecords);
		Paging.setPageList(pageNumber, totalPages);

		List users = _userDAO.findAllSortedBy(pageNumber, rowsPerPage, "o."
				+ StringUtils.defaultString(sortBy, "idUser"), StringUtils
				.defaultString(sortOrder, "asc"));

		return users;
	}

	/**
	 * This method search or not(case userName parameter has null) users by
	 * username
	 * 
	 * @param userName
	 *            - Can be null, case this, the method return all users
	 * @param pageNumber
	 *            - For paging
	 * @return list of users
	 */
	public List listUsersByWhere(User user, int pageNumber) {

		int itemsPerPage = ConfigurationFactory.getConf().topicsPage.intValue();

		List users = null;

		if (user != null && user.getUser() != null
				&& !user.getUser().equals("")) {
			users = _userDAO.findAllUsernameLike(user.getUser(), pageNumber,
					itemsPerPage);
		} else if (user != null && user.getAdmin() != null
				&& user.getAdmin().intValue() == 1) {
			users = _userDAO.findAllUserAdmin(pageNumber, itemsPerPage);
		} else {
			long userCount = _userDAO.countAllUsers();
			int pageCount = Paging.getNroPages(itemsPerPage, userCount);
			Paging.setPageList(pageNumber, pageCount);
			users = _userDAO.findAll(pageNumber, itemsPerPage);
		}
		return users;
	}

	/**
	 * @param user
	 */

	public User updateProfileUser(User _user, boolean byAdmin) {

		User loggedUser = null;

		// user updated by Admin Panel control
		if (byAdmin) {
			loggedUser = _user;
		} else {
			loggedUser = UserContext.getContext().getUser();
		}

		Long userId = loggedUser.getId();

		User userToUpdate = getUser(userId);
		if ((_user.getEmail() != null) && !"".equals(_user.getEmail())) {
			userToUpdate.setEmail(_user.getEmail());
		}

		userToUpdate.setName(_user.getName());
		userToUpdate.setUser_icq(_user.getUser_icq());
		userToUpdate.setUser_aim(_user.getUser_aim());
		userToUpdate.setUser_msnm(_user.getUser_msnm());
		userToUpdate.setUser_yim(_user.getUser_yim());
		userToUpdate.setWebsite(Utils.validateWebSite(_user.getWebsite()));
		userToUpdate.setLocalizacao(_user.getLocalizacao());
		userToUpdate.setOccupation(_user.getOccupation());
		userToUpdate.setUser_interests(_user.getUser_interests());
		userToUpdate.setUserSig(_user.getUserSig());
		userToUpdate.setShow_mail(_user.getShow_mail());
		userToUpdate.setReceiveNews(_user.getReceiveNews());
		userToUpdate.setShow_signature(_user.getShow_signature());
		userToUpdate.setPartnerId(_user.getPartnerId());

		if ((_user.getPasswordHash() != null)
				&& !"".equals(_user.getPasswordHash())) {
			String psw = Utils.encrypt(_user.getPasswordHash());
			userToUpdate.setPasswordHash(psw);
		}
		return userToUpdate;
	}

	public void updateAmRights(User _user) {
		User userToUpdate = this.getUser(_user.getIdUser());
		userToUpdate.setAdmin(_user.getAdmin());
	}

	/**
	 * @param user
	 */

	public Long createUser(User user) throws Exception {
		return _userDAO.create(user);
	}

	/**
     */

	public void updateVisitTimestamp() {

		User user = UserContext.getContext().getUser();
		if (user == null) {
			return;
		}
		Long userId = user.getId();
		if (userId == null) {
			return;
		}
		//user = _userDAO.loadUser(userId);
		//user.setLastVisitTimestamp(new Date());

		if (UserContext.getContext().isAuthenticated()) {
			CacheUtils.setLastVisitUser(UserContext.getContext().getUser()
					.getId(), new Date());
		}

	}

	/**
	 * Persist all user cache
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void persistLastTimeVisitCache(HashMap users) throws Exception {

		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

		try {
			lock.writeLock().lock();
			Iterator it = users.keySet().iterator();
			while (it.hasNext()) {
				Long userId = (Long) it.next();
				Date view = (Date) users.get(userId);
				User user = _userDAO.loadUser(userId);
				user.setLastVisitTimestamp(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}

	@SuppressWarnings("unchecked")
	public void sendSecurityCode(User user, Map lang) {
		Configuration conf = new Configuration();

		user = _userDAO.loadUser(user.getId());

		// cria o hash para ser enviado ao user
		String hash = Utils.encrypt(System.currentTimeMillis()
				+ user.getUserCode());

		// salva o hash no BD
		user.setHash_fpwd(hash);
		_userDAO.update(user);

		// url q redireciona o user para receber sua nova senha
		String url = "verify_forget_pwd.jbb?u=" + user.getIdUser()
				+ "&userHash=" + hash;

		// envia o meail para o user confirmando sua solicitação de nova senha
		Map mailMap = new HashMap();
		mailMap.put("conf", conf);
		mailMap.put("url", url);
		mailMap.put("user", user);
		mailMap.put("password_request_complete", lang
				.get("password_request_complete"));
		mailMap.put("siteName", JbbConfig.getConfig().getForumConfig()
				.getForumName());
		mailMap.put("siteDomain", JbbConfig.getConfig().getForumConfig()
				.getDomain());

		String message = VelocityTemplate.makeTemplate(mailMap,
				Constants.mailForgetPwd);
		Email.sendMail(conf.adminMail, user.getEmail(), (String) lang
				.get("password_request"), message, true);

	}

	public boolean verifyForgetPwd(Long userId, String hash, Map lang) {
		User user = new User();
		user.setIdUser(userId);
		user = _userDAO.loadUser(user.getId());
		if (user.getHash_fpwd() != null && hash != null
				&& hash.equals(user.getHash_fpwd())) {

			// Generate the new password that is gonna send to user
			String novaSenha = Utils.encrypt(user.getUserCode()
					+ System.currentTimeMillis());
			novaSenha = novaSenha.substring(12);

			// Makes the new password encrypted and put at DataBase
			user.setPasswordHash(Utils.encrypt(novaSenha));
			_userDAO.update(user);

			// Send an email to user with a new password
			Configuration conf = new Configuration();

			Map mailMap = new HashMap();
			mailMap.put("conf", conf);
			mailMap.put("user", user);
			mailMap.put("pwd", novaSenha);
			mailMap.put("password_request_your_new_pwd", lang
					.get("password_request_your_new_pwd"));
			mailMap.put("password_request", lang.get("password_request"));
			mailMap.put("siteName", JbbConfig.getConfig().getForumConfig()
					.getForumName());
			mailMap.put("siteDomain", JbbConfig.getConfig().getForumConfig()
					.getDomain());

			String message = VelocityTemplate.makeTemplate(mailMap,
					Constants.sendNewPassword);
			Email.sendMail(conf.adminMail, user.getEmail(), (String) lang
					.get("password_request"), message, true);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Get a list of UserRanks in the cache
	 * 
	 * @return
	 */
	public List getUserRanks() {
		if (userRanksCache == null) {
			userRanksCache = _userDAO.loadUserRanks();
		}
		return userRanksCache;
	}

	/**
	 * Load the UserRank by a Long value
	 * 
	 * @param postCount
	 * @return
	 */
	public UserRank getUserRank(Long postCount) {
		return getUniqueUserRankByCache(postCount);
	}

	/**
	 * Use Long value to search by the UserRank
	 * 
	 * @param postCount
	 * @return
	 */
	private UserRank getUniqueUserRankByCache(Long postCount) {
		if (postCount == null || getUserRanks() == null) {
			return null;
		}

		int pCount = postCount.intValue();
		for (int i = 0; i < userRanksCache.size(); i++) {
			UserRank usrRank = (UserRank) userRanksCache.get(i);
			if (pCount > usrRank.getRankMin().intValue()
					&& pCount < usrRank.getRankMax().intValue()) {
				return usrRank;
			}
		}
		return null;
	}

	/**
	 * Used by Quartz task to renew all hashs security codes
	 */

	public void renewUserSecurityCode() {
		List users = _userDAO.findAll(0, Integer.MAX_VALUE);
		if (users != null) {
			for (int i = 0; i < users.size(); i++) {
				try {
					User user = (User) users.get(i);
					log.debug("Setting new SecurityCode to user "
							+ user.getUser());

					String hashCode = Utils.getCodeUser(user.getUser());
					Long userId = user.getId();
					userSecurityDAO.createHashCode(userId, hashCode);

					log.debug("SecurityCode setted!");
				} catch (Exception e) {
					log.debug("Hidden the Exception on SecurityCode update");
				}
			}
		}
	}

	public User updateAvatar(String imgAvatar, Long userId, String avatarPath,
			String avatarExibition) throws Exception {
		User user = _userDAO.loadUser(userId);
		user.setUser_avatar(imgAvatar);
		user.setAvatarPath(avatarPath);
		user.setAvatarExibition(avatarExibition);
		return user;
	}

}