package org.javabb.interceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javabb.infra.UserContext;
import org.javabb.infra.Utils;
import org.javabb.transaction.UserTransaction;
import org.javabb.vo.User;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

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
 * $Id: VerifyCookieInterceptor.java,v 1.17.2.3.2.3.2.5 2006/10/27 20:25:39
 * daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class VerifyCookieInterceptor extends AroundInterceptor {

	private static final String AUTOMATIC_LOGIN_COOKIE = "automatic_cookie_login";
	private UserTransaction userTransaction;

	/**
	 * @param userTransaction
	 *            the new userTransaction value
	 */
	public void setUserTransaction(UserTransaction userTransaction) {
		this.userTransaction = userTransaction;
	}

	/**
	 * @param invocation
	 * @param result
	 * @throws Exception
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#after(com.opensymphony.xwork.ActionInvocation,
	 *      java.lang.String)
	 */
	protected void after(ActionInvocation invocation, String result)
			throws Exception {

	}

	/**
	 * @param invocation
	 * @throws Exception
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#before(com.opensymphony.xwork.ActionInvocation)
	 */
	protected void before(ActionInvocation invocation) throws Exception {
		try {
			ActionContext ctx = ActionContext.getContext();
			Map session = ctx.getSession();
			User user = UserContext.getContext().getUser();
			String removeCookie = (String) session.get("jbbRemoveCookie");

			if ((removeCookie != null) && "1".equalsIgnoreCase(removeCookie)) {
				removeCookie();
			} else {
				User userCookie = getCookie();

				if ((userCookie != null) && (userCookie.getUserCode() != null)) {
					String idSession = Utils.randomNumber();

					if ((user != null)
							&& (user.getIdUser() != null && user.getUserCode() != null)) {
						// userTransaction.verifyUserCode(user.getIdUser(),
						// user.getUserCode());
						// Trying to crack
						if (!userCookie.getUserCode()
								.equals(user.getUserCode())) {
							UserContext.getContext().deauthenticate();
							return;
						}

						userCookie = UserContext.getContext().getUser();
					} else {
						userCookie = userTransaction.verifyUserCode(userCookie
								.getId(), userCookie.getUserCode());

						// Setting the last date visit of this user!
						userTransaction.updateVisitTimestamp();

						log.debug("Updating last visit of user "
								+ userCookie.getUser());
						log.debug(userCookie.getUser()
								+ "`s IP is "
								+ ServletActionContext.getRequest()
										.getRemoteAddr());
					}

					if (userCookie != null) {
						addCookie(userCookie);
					}
					ctx.getSession().put("jbbguest", idSession);

				} else if ((user != null) && (user.getUserCode() != null)) {
					// user = userTransaction.verifyUserCode(user.getIdUser(),
					// user.getUserCode());
					user = UserContext.getContext().getUser();
					addCookie(user);
				}
			}
		} catch (Exception e) {
			removeCookie();
		}
	}

	public void addCookie(User u) {

		HttpServletResponse r = ServletActionContext.getResponse();

		String domain = "";
		Cookie cookieCode = new Cookie(AUTOMATIC_LOGIN_COOKIE, u.getId() + "|"
				+ u.getUserCode());
		cookieCode.setMaxAge(2243200);
		// cookieCode.setDomain(domain);
		r.addCookie(cookieCode);
		r.setContentType("text/html");

		domain = "uol.com.br";
		Cookie cookieCode2 = new Cookie(AUTOMATIC_LOGIN_COOKIE, u.getId() + "|"
				+ u.getUserCode());
		cookieCode2.setMaxAge(2243200);
		cookieCode2.setDomain(domain);
		r.addCookie(cookieCode2);
		r.setContentType("text/html");

		domain = "javafree.org";
		Cookie cookieCode3 = new Cookie(AUTOMATIC_LOGIN_COOKIE, u.getId() + "|"
				+ u.getUserCode());
		cookieCode3.setMaxAge(2243200);
		cookieCode3.setDomain(domain);
		r.addCookie(cookieCode3);
		r.setContentType("text/html");

		domain = "javafree.com.br";
		Cookie cookieCode4 = new Cookie(AUTOMATIC_LOGIN_COOKIE, u.getId() + "|"
				+ u.getUserCode());
		cookieCode4.setMaxAge(2243200);
		cookieCode4.setDomain(domain);
		r.addCookie(cookieCode4);
		r.setContentType("text/html");

	}

	/**
	 * @return User bind with cookie
	 */
	public User getCookie() {
		HttpServletRequest a = ServletActionContext.getRequest();
		User u = null;
		Cookie[] c = a.getCookies();
		Cookie cAt = null;

		for (int i = 0; (c != null) && (i < c.length); i++) {
			cAt = c[i];
			if (AUTOMATIC_LOGIN_COOKIE.equals(cAt.getName())) {
				String value = cAt.getValue();
				String userId = value.substring(0, value.indexOf('|'));
				String userCode = value.substring(value.indexOf('|') + 1);
				if (userCode != null) {
					u = new User();
					u.setId(new Long(userId));
					u.setUserCode(userCode);
				}
			}
		}
		return u;
	}

	/**
     *
     */
	public void removeCookie() {
		HttpServletResponse r = ServletActionContext.getResponse();
		Cookie cookie1 = new Cookie(AUTOMATIC_LOGIN_COOKIE, null);
		cookie1.setMaxAge(0); // One month

		// cookie1.setDomain(domain);
		r.addCookie(cookie1);
		r.setContentType("text/html");
	}
}