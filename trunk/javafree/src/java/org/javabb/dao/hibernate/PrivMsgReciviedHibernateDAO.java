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

package org.javabb.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.javabb.dao.entity.IPrivMsgReciviedDAO;
import org.javabb.vo.PrivMsg;
import org.javabb.vo.PrivMsgRecivied;
import org.javabb.vo.User;

/**
 * @author Lucas Teixeira - <a
 *         href="mailto:lucas@javabb.org">lucas@javabb.org</a>
 */

public class PrivMsgReciviedHibernateDAO extends HibernateDAO implements
	IPrivMsgReciviedDAO {

    public void invalidateCache() {
	counterCache.invalidateCache();
    }

    @SuppressWarnings("unchecked")
    public List retrieveUserInbox(User u) throws Exception {
	return find(PrivMsgRecivied.class, "o.userTo.idUser=?",
		new Object[] { u.getId() }, "o.data DESC", ALL_PAGES, 0);
    }

    public int countMessagesByUser(User u) {

	Integer nmbMessages = counterCache.getCountMessagesByUser(u.getId());
	if (nmbMessages == null) {
	    nmbMessages = countRowsWhere(PrivMsgRecivied.class, "o.id",
		    "o.userTo.idUser=? and o.read=0",
		    new Object[] { u.getId() });
	    counterCache.setCountMessagesByUser(u.getId(), nmbMessages);
	}
	return nmbMessages;
    }

    public PrivMsgRecivied load(Long id) throws Exception {
	return (PrivMsgRecivied) getHibernateTemplate().load(
		PrivMsgRecivied.class, id);
    }

    public Long save(PrivMsgRecivied p) throws Exception {
	invalidateCache();
	return (Long) getHibernateTemplate().save(p);
    }

    public void delete(PrivMsgRecivied p) throws Exception {
	invalidateCache();
	getHibernateTemplate().update(p);
    }

    public void delete(List l, Long userId) throws Exception {
	String sql = "delete from jbb_privmsg_inbox where id_privmsg in (";
	for (Iterator i = l.iterator(); i.hasNext();)
	    sql += ((PrivMsg) i.next()).getId() + ",";
	sql = sql.substring(0, sql.length() - 1);
	sql += ") and id_user_to = " + userId;
	this.executeSQL(sql);
    }

}
