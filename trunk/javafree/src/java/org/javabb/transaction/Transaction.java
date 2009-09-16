package org.javabb.transaction;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.hibernate.HibernateDAO;

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
 * $Id: Transaction.java,v 1.1 2009/05/11 20:27:03 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class Transaction {
	protected final Log log = LogFactory.getLog(getClass());

	protected HibernateDAO genericDAO;

	public void setGenericDAO(HibernateDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	
	public void add(Object instance) throws Exception {
		genericDAO.add(instance);
	}

	public List loadAll(Object instance) throws Exception {
		return genericDAO.loadAll(instance);
	}

	
	public Object load(Object instance, Long id) throws Exception {
		return genericDAO.load(instance, id);
	}

	
	public void delete(Object instance) throws Exception {
		genericDAO.delete(instance);
	}

}
