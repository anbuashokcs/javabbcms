package org.javabb.action.infra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.component.Spy;
import org.javabb.infra.ApplicationContext;
import org.javabb.infra.JbbConfig;
import org.javabb.infra.Online;
import org.javabb.infra.PagedList;
import org.javabb.infra.UserContext;
import org.javabb.vo.Category;
import org.javabb.vo.Forum;
import org.javabb.vo.Topic;
import org.javabb.vo.User;
import org.springframework.web.util.HtmlUtils;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.util.Timer;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

/*
 * Copyright 2004 JavaFree.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * $Id: ActionSuper.java,v 1.1 2009/05/11 20:27:17 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class ActionSuper extends ActionSupport {
	/** Logger. */
	protected final Log log = LogFactory.getLog(getClass());

	/** Tempo de load da execução. */
	private Timer timer = new Timer();

	/** Módulo "quem está online". */
	private Online online;

	/** Configurações gerais do fórum. */
	private JbbConfig jbbConfig;

	/** Url usada para redirecionamento de uma action para um jsp. */
	private String url;

	/** Numero da pagina a ser usada. */
	protected int _page = 1;

	/** Variavel para guardar valores temporarios. */
	private String temp;

	private String captchafield;

	private PagedList _pagedResult;

	/**
	 * @return the application context object
	 */
	public ApplicationContext getApplication() {
		return ApplicationContext.getContext();
	}

	/**
	 * @param online
	 *            the new online value
	 */
	public void setOnline(Online online) {
		this.online = online;
	}

	/**
	 * @param jbbConfig
	 *            the new jbbConfig value
	 */
	public void setJbbConfig(JbbConfig jbbConfig) {
		this.jbbConfig = jbbConfig;
	}

	/**
	 * All Forum's configuration. This include total messsage, registered users
	 * and so on.
	 * 
	 * @return Forum's configuration.
	 */
	public JbbConfig getJbbConfig() {
		return jbbConfig;
	}

	/**
	 * @return O tempo de processamento da página.
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * Módulo de quem está online
	 * 
	 * @return Returns a Online object
	 */
	public Online getOnline() {
		return online;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return Returns the page.
	 */
	public int getPage() {
		return _page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(int page) {
		if (page > 0) {
			this._page = page;
		} else {
			this._page = 1;
		}

	}

	/**
	 * @return Conteúdo das páginas disponíveis para a view
	 */
	public List getPages() {
		return (List) getSessionAttribute("pages");
	}

	/**
	 * @return A última página da paginação
	 */
	public Integer getLastPage() {
		return (Integer) getSessionAttribute("last_page");
	}

	/**
	 * @return Returns the temp.
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * @param temp
	 *            The temp to set.
	 */
	public void setTemp(String temp) {
		this.temp = temp;
	}

	/**
	 * @param text
	 * @return htmp-escaped text
	 */
	public String htmlEscape(String text) {
		return HtmlUtils.htmlEscape(text);
	}

	/**
	 * Sets a parameter in the next chained action.
	 * 
	 * @param name
	 * @param value
	 */
	protected void setParameter(String name, Object value) {
		Map params = new HashMap(ActionContext.getContext().getParameters());
		params.put(name, value);
		ActionContext.getContext().setParameters(params);
	}

	/**
	 * Sets a variable to be used in the template.
	 * 
	 * @param name
	 * @param value
	 */
	protected void setViewObject(String name, Object value) {
		Map map = new HashMap();
		map.put(name, value);
		ActionContext.getContext().getValueStack().push(map);
	}

	/**
	 * Retrieves an object from session.
	 * 
	 * @param attributeName
	 * @return Object in session with name equals nameSession
	 * @see #setSessionAttribute(String, Object)
	 * @see ActionContext#getContext()
	 * @see ActionContext#getSession()
	 */
	public Object getSessionAttribute(String attributeName) {
		return ActionContext.getContext().getSession().get(attributeName);
	}

	/**
	 * Sets a new object in session.
	 * 
	 * @param nameSession
	 * @param objectSession
	 * @see #removeSessionAttribute(String)
	 * @see #getSessionAttribute(String)
	 * @see ActionContext#getContext()
	 * @see ActionContext#getSession()
	 */
	public void setSessionAttribute(String nameSession, Object objectSession) {
		ActionContext ctx = ActionContext.getContext();
		Map session = ctx.getSession();
		session.put(nameSession, objectSession);
	}

	/**
	 * Removes an object from session.
	 * 
	 * @param attributeName
	 * @see #setSessionAttribute(String, Object)
	 * @see ActionContext#getContext()
	 * @see ActionContext#getSession()
	 */
	public void removeSessionAttribute(String attributeName) {
		ActionContext.getContext().getSession().remove(attributeName);
	}

	public PagedList getPagedResult() {
		return _pagedResult;
	}

	public void setPagedResult(PagedList pagedResult) {
		_pagedResult = pagedResult;
	}

	public boolean isTopicRead(Topic topic) {
		try {
			return UserContext.getContext().isTopicRead(topic);
		} catch (RuntimeException e) {
			log.debug(e.getMessage(), e);
			return true;
		}
	}

	public boolean isForumRead(Forum forum) {
		try {
			return UserContext.getContext().isForumRead(forum);
		} catch (RuntimeException e) {
			log.debug(e.getMessage(), e);
			return false;
		}
	}

	public int getNmbUnreadsInCat(Category cat) {
		try {
			return UserContext.getContext().getNmbUnreadsInCat(cat);
		} catch (RuntimeException e) {
			log.debug(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * Check if in Request Captcha is true or false;
	 * 
	 * @return captcha validation
	 */
	public boolean checkCaptcha() {
		HttpSession ss = ServletActionContext.getRequest().getSession();
		if (ss != null) {
			String c = (String) ss
					.getAttribute(nl.captcha.servlet.Constants.SIMPLE_CAPCHA_SESSION_KEY);
			if (c != null && getCaptchafield() != null) {
				if (c.equals(getCaptchafield())) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Get the user of session
	 * 
	 * @return
	 * @throws Exception
	 */
	public User getUserLogged() throws Exception {
		return (User) this.getSessionAttribute("jbbuser");
	}

	public boolean checkUserLogged() throws Exception {
		return (getUserLogged() != null && getUserLogged().getId() != null);
	}

	public String getCaptchafield() {
		return captchafield;
	}

	public void setCaptchafield(String captchafield) {
		this.captchafield = captchafield;
	}

	public String getTopicViews() {
		return Spy.topicViews;
	}

}
