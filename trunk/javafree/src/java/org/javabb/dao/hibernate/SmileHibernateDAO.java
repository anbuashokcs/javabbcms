package org.javabb.dao.hibernate;

import java.util.List;

import org.javabb.dao.entity.ISmileDAO;
import org.javabb.vo.Smile;

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
 * $Id: SmileHibernateDAO.java,v 1.1 2009/05/11 20:26:58 daltoncamargo Exp $
 * @author Ronald Tetsuo Miura
 */
public class SmileHibernateDAO extends HibernateDAO implements ISmileDAO {

    /**
     * @see org.javabb.dao.entity.ISmileDAO#findAll()
     */
    public List findAll() {
		return getHibernateTemplate().find(
				"FROM " + Smile.class.getName() + " o ORDER BY length(o.symbol) desc");
    }

    /**
     * @see org.javabb.dao.entity.ISmileDAO#load(java.lang.Long)
     */
    public Smile load(Long id) {
        return (Smile) getHibernateTemplate().load(Smile.class, id);
    }

    /**
     * @see org.javabb.dao.entity.ISmileDAO#create(org.javabb.vo.Smile)
     */
    public Long create(Smile smile) {
        return (Long) getHibernateTemplate().save(smile);
    }

    /**
     * @see org.javabb.dao.entity.ISmileDAO#delete(java.lang.Long)
     */
    public void delete(Long id) {
        getHibernateTemplate().delete(getHibernateTemplate().load(Smile.class, id));
    }

    /**
     * @see org.javabb.dao.entity.ISmileDAO#update(org.javabb.vo.Smile)
     */
    public void update(Smile s) {
        getHibernateTemplate().update(s);
    }
}
