package org.javabb.dao.hibernate;

import java.util.List;

import org.javabb.dao.entity.IBadWordDAO;
import org.javabb.vo.BadWord;

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

/* $Id: BadWordHibernateDAO.java,v 1.1 2009/05/11 20:26:59 daltoncamargo Exp $ */
/**
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class BadWordHibernateDAO extends HibernateDAO implements IBadWordDAO {

    /**
     * @see org.javabb.dao.entity.IBadWordDAO#findAll()
     */
    public List findAll() {
        return getHibernateTemplate().loadAll(BadWord.class);
    }

    /**
     * @see org.javabb.dao.entity.IBadWordDAO#load(java.lang.Long)
     */
    public BadWord load(final Long id) {
        return (BadWord) getHibernateTemplate().load(BadWord.class, id);
    }

    /**
     * @see org.javabb.dao.entity.IBadWordDAO#delete(org.javabb.vo.BadWord)
     */
    public void delete(BadWord badword) {
        getHibernateTemplate().delete(badword);
    }

    /**
     * @see org.javabb.dao.entity.IBadWordDAO#save(org.javabb.vo.BadWord)
     */
    public void save(BadWord badword) {
        getHibernateTemplate().save(badword);
    }
}