package org.javabb.dao.hibernate;

import java.sql.SQLException;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javabb.dao.entity.IUserDAO;
import org.javabb.dao.entity.IUserSecurityDAO;
import org.javabb.infra.Utils;
import org.javabb.vo.User;
import org.javabb.vo.UserRank;
import org.springframework.orm.hibernate.HibernateCallback;

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
 * $Id: UserHibernateDAO.java,v 1.1 2009/05/11 20:26:59 daltoncamargo Exp $
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class UserHibernateDAO extends HibernateDAO implements IUserDAO {
	
	protected final Log log = LogFactory.getLog(UserHibernateDAO.class);
	
	private IUserSecurityDAO userSecurityDAO;
    public void setUserSecurityDAO(IUserSecurityDAO userSecurityDAO) {
		this.userSecurityDAO = userSecurityDAO;
	}

	/**
     * @see org.javabb.dao.entity.IUserDAO#loadUser(java.lang.Long)
     */
    public User loadUser(Long userId) {
        return (User) getHibernateTemplate().load(User.class, userId);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#update(org.javabb.vo.User)
     */
    public void update(User user) {
        getHibernateTemplate().update(user);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#create(org.javabb.vo.User)
     */
    public Long create(User user) throws Exception{
		Transaction tx = null;
		tx = getSession().beginTransaction();
		Long userId = (Long) getHibernateTemplate().save(user);
		getSession().flush();
		tx.commit();
//        Setting the userCode for this user...
        userSecurityDAO.createHashCode(userId, Utils.getCodeUser(userId.toString()));
        counterCache.invalidateCache();
        return userId;
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#lastUserRegistered()
     */
    public User lastUserRegistered() {
        User retornUser = null;
        List list = getList("from {vo}User order by data_registro desc", 0, 1);

        if (!list.isEmpty()) {
        	retornUser = (User) list.get(0);
        }

        return retornUser;
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#verificaLogin(java.lang.String, java.lang.String)
     */
    public User verificaLogin(final String name, final String passwd) {
        return (User) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                User returnUsuario = null;
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Expression.eq("user", name));
                criteria.add(Expression.eq("passwordHash", passwd));
                returnUsuario = (User) criteria.uniqueResult();
                session.clear();

                return returnUsuario;
            }
        });
    }

    /**
     * Delete an user and move your messages to user Anonymous - ID=1
     * @see org.javabb.dao.entity.IUserDAO#deleteUser(org.javabb.vo.User)
     */
    public void deleteUser(User user) {
        user = (User) getHibernateTemplate().load(User.class, user.getIdUser());
    	long idUser = user.getIdUser().longValue();
    	
    	//Obtain the userName of user with ID=1 (Anonymous user)
    	User anomUser = (User) getHibernateTemplate().load(User.class, new Long(1));
        
    	String sqlForum = "update jbb_forum set last_post_user_id=1, last_post_user_name='"+ anomUser.getUser() +"'" +
    						" where last_post_user_id=" + idUser;
        String sqlPosts = "update jbb_posts set id_user=1 " + "where id_user=" + idUser;
        String sqlTopics = "update jbb_topics set id_user=1 " + "where id_user=" + idUser;

        executeSQL(sqlForum);
        executeSQL(sqlPosts);
        executeSQL(sqlTopics);
        getHibernateTemplate().delete(user);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#findByEmail(java.lang.String, java.lang.String)
     */
    public List findByEmail(final String email, final Long userId) {
        return find(User.class,
            "o.email = ? and o.idUser != ?",
            new Object[] { email, userId },
            "o.idUser",
            ALL_PAGES,
            ALL_PAGES);
    }
    
    /**
     * @see org.javabb.dao.entity.IUserDAO#findByEmail(java.lang.String)
     */
    public List findByEmail(final String email) {
        return find(User.class,
            "o.email = ? ",
            new Object[] { email },
            "o.idUser",
            ALL_PAGES,
            ALL_PAGES);
    }    

    /**
     * @see org.javabb.dao.entity.IUserDAO#loadByUsername(java.lang.String)
     */
    public User loadByUsername(final String username) {
        return (User) loadByUniqueAttribute(User.class, "o.user", username);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#loadByUsercode(java.lang.String)
     */
    public User loadByUsercode(final String usercode) {
    	User user = userCache.getLoadByUsercode(usercode);
    	user = null;
    	if(user == null){
    		user = (User) loadByUniqueAttribute(User.class, "o.userCode", usercode);
    		userCache.setLoadByUsercode(usercode, user);
    	}
    	return user;
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#countAllUsers()
     */
    public int countAllUsers() {
        return countRowsWhere(User.class, "o.idUser", "o.idUser != ?", new Object[] { new Long(0) });
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#findAllUsernameLike(java.lang.String, int, int)
     */
    public List findAllUsernameLike(String userName, int pageNumber, int itemsPerPage) {
        return find(User.class, "o.idUser != 0 AND o.user LIKE ?", //
            new Object[] { "%" + userName + "%" }, //
            "o.idUser ASC", pageNumber, itemsPerPage);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#findAllUserAdmin(int, int)
     */
    public List findAllUserAdmin(int pageNumber, int itemsPerPage) {
    	return findAll(User.class,
    			new String[]{"admin"},
    			new String[]{"1"},
				new String[]{"user"},
				new String[]{"asc"},
				pageNumber-1, itemsPerPage);
    }
    
    /**
     * @see org.javabb.dao.entity.IUserDAO#findAllSortedBy(int, int, java.lang.String, java.lang.String)
     */
    public List findAllSortedBy(int pageNumber, int itemsPerPage, String orderBy, String ascDesc) {
        return findAll(User.class, orderBy + " " + ascDesc, pageNumber, itemsPerPage);
    }

    /**
     * @see org.javabb.dao.entity.IUserDAO#findAll(int, int)
     */
    public List findAll(int pageNumber, int itemsPerPage) {
        return find(User.class,
            "o.idUser != ?",
            new Object[] { new Long(0) },
            "o.idUser",
            pageNumber,
            itemsPerPage);
    }
    
    public List loadListByUsername(String username) {
    	username = username.replaceAll("'","\"");
    	return getList("from User user where user.user like '"+username+"%'");
    }

	public List loadUserRanks() {
		return getList("from UserRank u order by u.rankMax desc");
	}

	public UserRank getUserRank(Long postCount) {
		List lst =  getList("from UserRank");
		if(lst != null && lst.size() > 0){
			return (UserRank)lst.get(0);
		} else {
			return null;
		}
	}
	
}