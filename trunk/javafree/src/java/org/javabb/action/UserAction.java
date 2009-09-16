package org.javabb.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javabb.action.infra.BaseAction;
import org.javabb.component.ImageUtils;
import org.javabb.component.Spy;
import org.javabb.infra.FileTransfer;
import org.javabb.infra.JbbConfig;
import org.javabb.infra.UserContext;
import org.javabb.infra.Utils;
import org.javabb.transaction.CategoryTransaction;
import org.javabb.transaction.UserTransaction;
import org.javabb.vh.SpyVH;
import org.javabb.vh.Stats;
import org.javabb.vo.Partner;
import org.javabb.vo.PostFile;
import org.javabb.vo.User;

import com.opensymphony.webwork.ServletActionContext;

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
 * $Id: UserAction.java,v 1.35.2.1.2.2.2.14 2007/09/06 21:12:25 daltoncamargo
 * Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura <br>
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org
 *         </a> <br>
 */

public class UserAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * User to be bind with Action
	 */
	private User _user = new User();

	/**
	 * {@link org.javabb.transaction.Transaction Transaction}object to perform
	 * operations with and for users.
	 */
	private UserTransaction userTransaction;

	private CategoryTransaction _categoryTransaction;

	private List lstUsersPortal = new ArrayList();

	private int cookie = 1;

	private String sortBy;

	private String sortOrder;

	private Stats stats = new Stats();

	private String username;

	private String userHash;

	private List userRanks = new ArrayList();

	private String confirmPassword;

	// Upload Parameters
	private File file;

	private String contentType;

	private String filename;
	
	

	/**
	 * @param cookie
	 *            The cookie to set.
	 */
	public void setCookie(int cookie) {
		this.cookie = cookie;
	}

	/**
	 * @param sortBy
	 *            The sortBy to set.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @param sortOrder
	 *            The sortOrder to set.
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	// ####################################################################
	// Dependencies
	// ####################################################################

	/**
	 * @param userTransaction
	 *            The model to set.
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	/**
	 * @param categoryTransaction
	 */
	public void setCategoryTransaction(CategoryTransaction categoryTransaction) {
		this._categoryTransaction = categoryTransaction;
	}

	// ####################################################################
	// Actions
	// ####################################################################

	/**
	 * @return Action status
	 */
	public String loadUsersPortal() {
		stats.setTotalPostCount(new Integer((int) _categoryTransaction
				.countAllPosts()));
		_user = userTransaction.getUser(_userId);
		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String deleteUsersPortal() {
		userTransaction.deleteUser(_user);
		setUrl("user_management.jbb");
		return SUCCESS;
	}

	public String viewLegend() {
		userRanks = userTransaction.getUserRanks();
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String loadNewMember() throws Exception{
		partners = userTransaction.loadAll(new Partner());
		return SUCCESS;
	}
	
	/**
	 * @return Action status
	 */
	@SuppressWarnings("unchecked")
	public String loadEditUsersPortal() throws Exception{
		partners = userTransaction.loadAll(new Partner());
		_user = UserContext.getContext().getUser();
		_user = userTransaction.getUser(_user.getId());
		return SUCCESS;
	}

	private String validateEditUser(boolean editByAdmin) {

		if (_user.getEmail() == null || "".equals(_user.getEmail().trim())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("profile.required.email"));
			return "REG_ERROR";
		} else {
			if (!Utils.validateEmail(_user.getEmail())) {
				msgErrors = new ArrayList();
				msgErrors.add(getText("invalid_mail"));
				return "REG_ERROR";
			} else {
				String usrMail = UserContext.getContext().getUser().getEmail();
				if (!usrMail.equals(_user.getEmail())) {
					if (userTransaction.isEmailValid(_user.getEmail())) {
						msgErrors = new ArrayList();
						msgErrors.add(getText("email_already_in_use"));
						return "REG_ERROR";
					}
				}
			}
		}
		if (!editByAdmin) {
			if (_user.getPasswordHash() != null
					&& !_user.getPasswordHash().equals(getConfirmPassword())) {
				msgErrors = new ArrayList();
				msgErrors.add(getText("pwd_are_not_equals"));
				return "REG_ERROR";
			}
		}

		return "";
	}

	/**
	 * @return Action status
	 */
	public String updateUsersPortal() {
		// Checking user's data
		String validateUserData = validateEditUser(false);
		if (!"".equals(validateUserData)) {
			return validateUserData;
		}

		_user = userTransaction.updateProfileUser(_user, false);

		setUrl("show_edit_profile.jbb");

		// Updating the changes of user on the session
		UserContext.getContext().setUser(_user);

		return SUCCESS;
	}

	public String updateUserByAdmin() {
		// Checking user's data
		String validateUserData = validateEditUser(true);
		if (!"".equals(validateUserData)) {
			return validateUserData;
		}

		_user = userTransaction.updateProfileUser(_user, true);

		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String manageAdmRights() {
		userTransaction.updateAmRights(_user);
		return SUCCESS;
	}

	/**
	 * Validator in insert new users at JavaBB
	 * 
	 * @param user
	 * @return
	 */
	private String validateInsertUser() {
		if (_user.getUser() == null || "".equals(_user.getUser().trim())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("profile.required.username"));
			return "REG_ERROR";
		} else if (_user.getUser().length() < 4
				|| _user.getUser().length() > 16) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("profile.required.user.short"));
			return "REG_ERROR";
		} else if (userTransaction.findUserByUserName(_user.getUser().trim()) != null) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("user_already_in_use"));
			return "REG_ERROR";
		}
		if (_user.getEmail() == null || "".equals(_user.getEmail().trim())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("profile.required.email"));
			return "REG_ERROR";
		}
		if (!Utils.validateEmail(_user.getEmail())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("invalid_mail"));
			return "REG_ERROR";
		}
		if (userTransaction.isEmailValid(_user.getEmail())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("email_already_in_use"));
			return "REG_ERROR";
		}

		if (_user.getPasswordHash() == null
				|| "".equals(_user.getPasswordHash().trim())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("profile.required.password"));
			return "REG_ERROR";
		} else if (_user.getPasswordHash() != null
				&& !_user.getPasswordHash().equals(getConfirmPassword())) {
			msgErrors = new ArrayList();
			msgErrors.add(getText("pwd_are_not_equals"));
			return "REG_ERROR";
		}

		// if Captcha Code is Active
		if ("1".equals(JbbConfig.getConfig().getForumConfig()
				.getActiveCaptcha())) {
			if (!checkCaptcha()) {
				msgErrors = new ArrayList();
				msgErrors.add(getText("invalidCaptcha"));
				return "REG_ERROR";
			}
		}

		return "";
	}

	/**
	 * @return Action status
	 */
	public String insertUsersPortal() throws Exception {

		// Checking user's data
		String validateUserData = validateInsertUser();
		if (!"".equals(validateUserData)) {
			return validateUserData;
		}

		_user.setWebsite(Utils.validateWebSite(_user.getWebsite()));
		_user.setDataRegistro(new Date());
		_user.setPasswordHash(Utils.encrypt(_user.getPasswordHash()));
		_user.setAdmin(new Integer(0));
		_user.setUser_posts(new Long(0));

		Long userId = userTransaction.createUser(_user);
		_user.setId(userId);
		//UserContext.getContext().setUser(_user);

		return SUCCESS;
	}

	public String uploadAvatar() throws Exception {
		User usr = UserContext.getContext().getUser();
		// Uploading all files

		List files = FileTransfer.uploadFiles(
				ServletActionContext.getRequest(), usr.getUser_avatar(), true);
		if (files != null && files.size() > 0) {
			// Delete current avatar
			FileTransfer.deleteFile(usr.getAvatarPath(), usr.getUser_avatar());

			// now the system works in a new avatar to be parsed
			PostFile fBean = (PostFile) files.get(0);
			ImageUtils.resizeImage(fBean);

			// Process the name of the avatar
			int indexAvatar = fBean.getFilePath().indexOf("files_user");
			String exibition = fBean.getFilePath().substring(indexAvatar);
			exibition = exibition.replace('\\', '/') + fBean.getFileName();

			usr = userTransaction.updateAvatar(fBean.getFileName(), usr
					.getIdUser(), fBean.getFilePath(), exibition);
			UserContext.getContext().setUser(usr);
		}
		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String verifyLogin() {
		log.debug("Verify Login");

		if (_user.getAdmin() != null) {
			_user.setAdmin(null);
		}

		if (_user.getUser() != null && "".equals(_user.getUser().trim())) {
			return LOGIN;
		}

		// DI by Spring
		// model = new UserTransaction();
		_user.setPasswordHash(Utils.encrypt(_user.getPasswordHash()));
		_user = userTransaction.verifyLogin(_user.getUser(), _user
				.getPasswordHash());

		if (_user == null) {
			// Login inválido, vai inserir o erro na tela
			return LOGIN;
		}

		// Login OK, bean do usuário vai pra sessão
		UserContext.getContext().setUser(_user);
		setSessionAttribute("jbbRemoveCookie", "0");

		// Usuário optou por permanecer logado
		// será inserido um cookie para isso
		if (cookie == 1) {
			/*
			 * JbbCookie jbbCookie = new JbbCookie();
			 * jbbCookie.addCookie(usersPortal,
			 * ServletActionContext.getResponse());
			 * jbbCookie.getCookie(ServletActionContext.getRequest());
			 */
		}

		if (getSessionAttribute("jbbUrlBeforeLogin") != null) {
			// Verifica se o usuário clicou em algum link
			// e foi solicitado para que ele se logasse
			return INPUT;
		}

		// Caso contrário, ele manda para o index do fórum
		return SUCCESS;
	}

	/**
	 * @return Action status
	 */
	public String logout() {
		// Remove o bean do usuário da sessão
		setSessionAttribute("jbbRemoveCookie", "1");

		// TODO A linha acima não deveria ser
		// insertSession("jbbRemoveCookie", String.valueOf(cookie));
		UserContext.getContext().deauthenticate();
		removeSessionAttribute("pages");

		setUrl("forum.jbb?javaBBId=" + System.currentTimeMillis());

		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String listUsersByWhere() {

		lstUsersPortal = userTransaction.listUsersByWhere(_user, getPage());

		// usado para exibir ou não a paginação
		if (_user.getUser() != null && !_user.getUser().equals("")) {
			setTemp(null);
		} else {
			setTemp("!");
		}

		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String findUserByUserName() {

		/* i18n */
		Map mp = new HashMap();
		mp.put("password_request", getText("password_request"));
		mp.put("password_request_complete",
				getText("password_request_complete"));

		// Captcha Code is Active
		if ("1".equals(JbbConfig.getConfig().getForumConfig()
				.getActiveCaptcha())) {
			if (!checkCaptcha()) {
				// Code captcha is invalid
				return INPUT;
			}
		}

		User u = userTransaction.findUserByUserName(_user.getName());
		if (u == null) {
			// se não encontrar nenhum user seto o id pra -1 para exibir a
			// menssagem de user invalido
			_user.setIdUser(new Long(-1));
		} else {
			userTransaction.sendSecurityCode(u, mp);
		}
		return SUCCESS;
	}

	public String verifyForgetPwd() {
		/* i18n */
		Map mp = new HashMap();
		mp.put("password_request", getText("password_request"));
		mp.put("password_request_your_new_pwd",
				getText("password_request_your_new_pwd"));

		if (userTransaction.verifyForgetPwd(_userId, userHash, mp)) {
			setUrl("forum.jbb");
		} else {
			// Invalid HashCode
			this.setTemp(getText("invalidCaptcha"));
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * @return result
	 */
	public String listAllRegisteredUsers() {
		lstUsersPortal = userTransaction.listAllRegisteredUsers(getSortBy(),
				getSortOrder(), getPage());

		return SUCCESS;
	}

	public String listByUserName() {
		if (username != null && !"".equals(username)) {
			lstUsersPortal = userTransaction.findUserListByUserName(username);
		}
		return SUCCESS;
	}

	public String uploadProfileImage() throws Exception {
		log.debug(this.filename);
		setUrl("show_edit_profile.jbb?");
		return SUCCESS;
	}

	public SpyVH getSpyVH() {
		return (Spy._SPYVH != null) ? Spy._SPYVH : new SpyVH();
	}

	// ####################################################################
	// View objects accessors
	// ####################################################################

	/**
	 * @return Returns the lstUsersPortal.
	 */
	public List getLstUsersPortal() {
		return lstUsersPortal;
	}

	/**
	 * @return Returns the usersPortal.
	 */
	public User getUser() {
		return _user;
	}

	/**
	 * @return Returns the cookie.
	 */
	public int getCookie() {
		return cookie;
	}

	/**
	 * @return Returns the sortBy.
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @return Returns the sortOrder.
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @return Returns the stats.
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the userHash.
	 */
	public String getUserHash() {
		return userHash;
	}

	/**
	 * @param userHash
	 *            The userHash to set.
	 */
	public void setUserHash(String userHash) {
		this.userHash = userHash;
	}

	public List getUserRanks() {
		return userRanks;
	}

	public void setUserRanks(List userRanks) {
		this.userRanks = userRanks;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


}