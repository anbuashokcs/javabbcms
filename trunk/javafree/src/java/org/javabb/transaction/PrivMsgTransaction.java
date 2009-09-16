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
package org.javabb.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javabb.component.VelocityTemplate;
import org.javabb.dao.entity.IPrivMsgReciviedDAO;
import org.javabb.dao.entity.IPrivMsgSentDAO;
import org.javabb.infra.Configuration;
import org.javabb.infra.Constants;
import org.javabb.infra.Email;
import org.javabb.infra.UserContext;
import org.javabb.vo.PrivMsg;
import org.javabb.vo.PrivMsgRecivied;
import org.javabb.vo.PrivMsgSent;
import org.javabb.vo.User;

/**
 * @author Lucas Teixeira - <a href="mailto:lucas@javabb.org">lucas@javabb.org
 *         </a>
 */

public class PrivMsgTransaction extends Transaction {

	private IPrivMsgReciviedDAO _privMsgReciviedDAO = null;

	private IPrivMsgSentDAO _privMsgSentDAO = null;

	private UserTransaction _userTransaction;

	public PrivMsgTransaction() {
	}

	public void setPrivMsgReciviedDAO(IPrivMsgReciviedDAO privMsgReciviedDAO) {
		this._privMsgReciviedDAO = privMsgReciviedDAO;
	}

	public void setPrivMsgSentDAO(IPrivMsgSentDAO privMsgSentDAO) {
		this._privMsgSentDAO = privMsgSentDAO;
	}

	public void setUserTransaction(UserTransaction userTransaction) {
		_userTransaction = userTransaction;
	}

	public List getUserInbox(User u) throws Exception {
		return _privMsgReciviedDAO.retrieveUserInbox(u);
	}

	public int countMsgByUser(User u) throws Exception {
		if (u != null && u.getIdUser() != null) {
			return _privMsgReciviedDAO.countMessagesByUser(u);
		} else {
			return 0;
		}

	}

	public List getUserOutbox(User u) throws Exception {
		return _privMsgSentDAO.retrieveUserOutbox(u);
	}

	
	public Long send(PrivMsg p) throws Exception {
		Long mpId = _privMsgReciviedDAO.save(new PrivMsgRecivied(p));
		_privMsgSentDAO.save(new PrivMsgSent(p));
		return mpId;
	}

	
	public void deleteSent(PrivMsg p) throws Exception {
		_privMsgSentDAO.delete((PrivMsgSent) p);
	}

	
	public void deleteRecivied(PrivMsg p) throws Exception {
		_privMsgReciviedDAO.delete((PrivMsgRecivied) p);
	}

	public PrivMsg loadSent(PrivMsg p) throws Exception {
		return _privMsgSentDAO.load(p.getId());
	}

	
	public PrivMsg loadRecivied(PrivMsg p) throws Exception {
		PrivMsgRecivied _p = _privMsgReciviedDAO.load(p.getId());
		_p.setRead(new Integer(1));
		return _p;
	}

	
	public void deleteSelectedInbox(List list) throws Exception {
		if (list != null && list.size() > 0) {
			_privMsgReciviedDAO.delete(list, UserContext.getContext().getUser()
					.getId());
		}
	}

	
	public void deleteSelectedOutbox(List list) throws Exception {
		if (list != null && list.size() > 0) {
			_privMsgSentDAO.delete(list, UserContext.getContext().getUser()
					.getId());
		}
	}

	public List asPrivMsgList(Long[] id) {
		List l = new ArrayList();
		for (int i = 0; i < id.length; i++)
			l.add(new PrivMsg(id[i]));
		return l;
	}

	
	public void delegateMail(String message_18n, Long idUserTo, Long mpId)
			throws Exception {
		// String mailUser = p.getUserTo();
		User usr = _userTransaction.getUser(idUserTo);
		String mailUser = usr.getEmail();
		sendMailToUser(message_18n, mpId, mailUser);
	}

	/**
	 * Send an email to user notify about his mp
	 * 
	 * @param message_18n
	 * @param pmId
	 * @param userMail
	 * @throws Exception
	 */
	private void sendMailToUser(String message_18n, Long pmId, String userMail)
			throws Exception {
		// Send mail to user
		Configuration conf = new Configuration();

		Map mailMap = new HashMap();
		mailMap.put("conf", conf);
		mailMap.put("pm_message", message_18n);
		mailMap.put("pmId", pmId);

		String template = VelocityTemplate.makeTemplate(mailMap,
				Constants.mpMailTemplate);

		Email
				.sendMail(conf.adminMail, userMail, conf.forumName, template,
						true);
	}

}