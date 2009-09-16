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

import org.javabb.dao.entity.IPrivMsgSentDAO;
import org.javabb.vo.PrivMsg;
import org.javabb.vo.PrivMsgSent;
import org.javabb.vo.User;

/**
 * @author Lucas Teixeira - <a
 *         href="mailto:lucas@javabb.org">lucas@javabb.org</a>
 */

public class PrivMsgSentHibernateDAO extends HibernateDAO implements
	IPrivMsgSentDAO {

    public List retrieveUserOutbox(User u) throws Exception {
	return find(PrivMsgSent.class, "o.userFrom.idUser=?", new Object[] { u
		.getId() }, "o.data DESC", ALL_PAGES, 0);
    }

    public PrivMsgSent load(Long id) throws Exception {
	return (PrivMsgSent) getHibernateTemplate().load(PrivMsgSent.class, id);
    }

    public void save(PrivMsgSent p) throws Exception {
	getHibernateTemplate().save(p);
    }

    public void delete(PrivMsgSent p) throws Exception {
	getHibernateTemplate().delete(p);
    }

    public void delete(List l, Long userId) throws Exception {
	String sql = "delete from jbb_privmsg_outbox where id_privmsg in (";
	for (Iterator i = l.iterator(); i.hasNext();)
	    sql += ((PrivMsg) i.next()).getId() + ",";
	sql = sql.substring(0, sql.length() - 1);
	sql += ") and id_user_from = " + userId;
	;
	this.executeSQL(sql);
    }
}
