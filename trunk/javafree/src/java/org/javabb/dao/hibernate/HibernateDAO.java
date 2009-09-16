package org.javabb.dao.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.javabb.cache.local.CategorySingleCache;
import org.javabb.cache.local.CounterSingleCache;
import org.javabb.cache.local.NoticiasSingleCache;
import org.javabb.cache.local.PostSingleCache;
import org.javabb.cache.local.TopicSingleCache;
import org.javabb.cache.local.UserSingleCache;
import org.javabb.dao.DAOConstants;
import org.javabb.infra.Parser;
import org.javabb.vo.VOObject;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;

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
 * $Id: HibernateDAO.java,v 1.2 2009/06/22 22:54:07 daltoncamargo Exp $
 * 
 * @author Dalton Camargo - <a href="mailto:dalton@javabb.org">dalton@javabb.org
 *         </a> <br>
 * @author Ronald Tetsuo Miura
 */
public class HibernateDAO extends HibernateDaoSupport implements DAOConstants {

	// public Session session = HibernateTransactionInterceptor.session;
	protected String pathVO = "org.javabb.vo.";

	protected CategorySingleCache categoryCache;

	public void setCategoryCache(CategorySingleCache categoryCache) {
		this.categoryCache = categoryCache;
	}

	protected CounterSingleCache counterCache;

	public void setCounterCache(CounterSingleCache counterCache) {
		this.counterCache = counterCache;
	}

	protected UserSingleCache userCache;

	public void setUserCache(UserSingleCache userCache) {
		this.userCache = userCache;
	}

	protected PostSingleCache postCache;

	public void setPostCache(PostSingleCache postCache) {
		this.postCache = postCache;
	}

	protected NoticiasSingleCache noticiasCache;

	public void setNoticiasCache(NoticiasSingleCache noticiasCache) {
		this.noticiasCache = noticiasCache;
	}

	protected TopicSingleCache topicCache;

	public void setTopicCache(TopicSingleCache topicCache) {
		this.topicCache = topicCache;
	}

	protected void invalidateAllCaches() {
		categoryCache.invalidateCategoryCache();
		counterCache.invalidateCache();
		userCache.invalidateCache();
		postCache.invalidateCache();
		topicCache.invalidateCache();
	}

	/**
	 * Exclusão especificada por uma query
	 * 
	 * @param query
	 *            - Query a ser verificada para a exclusão
	 */
	public void deleteFrom(String query) {
		getHibernateTemplate().delete(Parser.replaceHQL(query));
	}

	public void saveOrUpdate(Object obj) throws Exception {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	/**
	 * @param vo
	 *            - Nome da VO
	 * @param index
	 *            Nome da coluna index a ser pesquisada
	 * @return - quantidade de registros encontrados na classe
	 */
	protected Integer countRowsOfTable(final String vo, final String index) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return (Integer) session.iterate( //
								MessageFormat.format(
										"select count({0}) from {1}", //
										new String[] { index, pathVO + vo }))
								.next();
					}
				});
	}

	/**
	 * @param nmClass
	 *            Nome da classe a ser pesquisada
	 * @param index
	 *            Nome da coluna index a ser pesquisada
	 * @param whereEqualField
	 *            Campo a ser comparado
	 * @param whereEqualValue
	 *            Valor do campo a ser comparado
	 * @return Retorna o número de registros encontrados
	 */
	protected Integer countRowsByWhere(String nmClass, String index,
			String[] whereEqualField, String[] whereEqualValue) {

		String sql = MessageFormat.format(
				"select count(tbl.{0}) from {1} as tbl ", new Object[] { index,
						pathVO + nmClass });

		/**
		 * Monta a cláusula where
		 */
		String where = " where ";

		for (int i = 0; i < whereEqualField.length; i++) {
			where += (" tbl." + whereEqualField[i] + " = " + whereEqualValue[i] + " and");
		}

		where = where.substring(0, where.length() - 3);
		sql += where;

		final String finalSql = sql;

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.iterate(finalSql).next();
					}
				});
	}

	/**
	 * Carrega um VO que seja filho de VOObject
	 * 
	 * @param obj
	 *            - Vo a ser carregado
	 * @return - VO populado
	 */
	protected VOObject load(VOObject obj) {
		return (VOObject) getHibernateTemplate().load(obj.getClass(),
				obj.getId());
	}

	/**
	 * Executa uma consulta através de uma condição
	 * 
	 * @param condicao
	 *            - Query a ser pesquisada
	 * @return - Lista de objetos pesquisados
	 */
	protected List getList(String condicao) {
		final String finalCondicao = Parser.replaceHQL(condicao);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(finalCondicao).list();
			}
		});
	}

	/**
	 * Executa uma query com parâmetros de paginação
	 * 
	 * @param condicao
	 *            - Query a ser pesquisada
	 * @param firstRes
	 *            - Registro inicial
	 * @param maxRes
	 *            - Máximo de registros
	 * @return - Lista contendo os objetos pesquisados
	 */
	protected List getList(final String condicao, final int firstRes,
			final int maxRes) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String cond = Parser.replaceHQL(condicao);
				Query q = session.createQuery(cond);
				q.setFirstResult(firstRes);
				q.setMaxResults(maxRes);
				return q.list();
			}
		});
	}

	/**
	 * Faz uma busca por Order By usando a paginação
	 * 
	 * @param nmClass
	 *            - Nome da classe a ser pesquisada
	 * @param orderBy
	 *            - Array contendo os campos do objeto a serem ordenados
	 * @param ascDesc
	 *            - Ordem que os arrays serão ordenados
	 * @param firstRes
	 *            - Primeiro registro a ser selecionado
	 * @param maxRes
	 *            - Máximo de registros que será retornado
	 * @return - Retorna todos objetos da classe
	 */
	protected List findAll(Class nmClass, String[] orderBy, String[] ascDesc,
			int firstRes, int maxRes) {

		String sql = "from " + nmClass.getName() + " as tbl ";
		String sqlOrderBy = " order by ";

		for (int i = 0; i < orderBy.length; i++) {
			sqlOrderBy += ("  tbl." + orderBy[i] + " " + ascDesc[i] + ",");
		}

		sqlOrderBy = sqlOrderBy.substring(0, sqlOrderBy.length() - 1);
		sql += sqlOrderBy;

		return getList(sql, firstRes, maxRes);
	}

	/**
	 * @param nmClass
	 *            - Nome da classe a ser pesquisada
	 * @param whereEqualField
	 *            - Campo a ser comparado
	 * @param whereEqualValue
	 *            - Valor do campo a ser comparado
	 * @param orderBy
	 *            - Array contendo os campos do objeto a serem ordenados
	 * @param ascDesc
	 *            - Ordem que os arrays serão ordenados
	 * @return - Retorna todos objetos da classe
	 */
	protected List findAll(Class nmClass, String[] whereEqualField,
			String[] whereEqualValue, String[] orderBy, String[] ascDesc) {

		String sql = "from " + nmClass.getName() + " as tbl ";

		/**
		 * Monta a cláusula where
		 */
		String where = " where ";

		for (int i = 0; i < whereEqualField.length; i++) {
			where += (" tbl." + whereEqualField[i] + " = " + whereEqualValue[i] + " and");
		}

		where = where.substring(0, where.length() - 3);
		sql += where;

		/**
		 * Monta a cláusula OrderBy
		 */
		String sqlOrderBy = " order by ";

		for (int i = 0; i < orderBy.length; i++) {
			sqlOrderBy += ("  tbl." + orderBy[i] + " " + ascDesc[i] + ",");
		}

		sqlOrderBy = sqlOrderBy.substring(0, sqlOrderBy.length() - 1);

		sql += sqlOrderBy;

		return getList(sql);
	}

	/**
	 * @param nmClass
	 *            - Nome da classe a ser pesquisada
	 * @param whereEqualField
	 *            - Campo a ser comparado
	 * @param whereEqualValue
	 *            - Valor do campo a ser comparado
	 * @param orderBy
	 *            - Array contendo os campos do objeto a serem ordenados
	 * @param ascDesc
	 *            - Ordem que os arrays serão ordenados
	 * @param firstRes
	 *            - Primeiro registro a ser selecionado
	 * @param maxRes
	 *            - Máximo de registros que será retornado
	 * @return - Retorna todos objetos da classe
	 */
	protected List findAll(Class nmClass, String[] whereEqualField,
			String[] whereEqualValue, String[] orderBy, String[] ascDesc,
			int firstRes, int maxRes) {

		String sql = "from " + nmClass.getName() + " as tbl ";

		/**
		 * Monta a cláusula where
		 */
		String where = " where ";

		for (int i = 0; i < whereEqualField.length; i++) {
			where += (" tbl." + whereEqualField[i] + " = " + whereEqualValue[i] + " and");
		}

		where = where.substring(0, where.length() - 3);
		sql += where;

		/**
		 * Monta a cláusula OrderBy
		 */
		String sqlOrderBy = " order by ";

		for (int i = 0; i < orderBy.length; i++) {
			sqlOrderBy += ("  tbl." + orderBy[i] + " " + ascDesc[i] + ",");
		}

		sqlOrderBy = sqlOrderBy.substring(0, sqlOrderBy.length() - 1);

		sql += sqlOrderBy;

		List lst = this.getList(sql, firstRes, maxRes);

		return lst;
	}

	/**
	 * Executa um comando SQL nativamente
	 * 
	 * @param sql
	 *            - Comando sql a ser executado
	 */
	protected void executeSQL(final String sql) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.clear();
				Connection cnn = session.connection();
				Statement stmt = cnn.createStatement();
				stmt.executeUpdate(sql);
				cnn.commit();
				return null;
			}
		});
	}

	/**
	 * @param c
	 * @param attributeName
	 * @param attributeValue
	 * @return object loaded
	 */
	protected Object loadByUniqueAttribute(final Class c,
			final String attributeName, final Object attributeValue) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryString = MessageFormat.format(
						"FROM {0} o WHERE {1} = :param", new Object[] {
								c.getName(), attributeName });
				Query query = session.createQuery(queryString);
				query.setParameter("param", attributeValue);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * @param c
	 * @param attributeName
	 * @param attributeValue
	 * @return object loaded
	 */
	protected List findByAttribute(final Class c, final String attributeName,
			final Object attributeValue) {

		String queryString = MessageFormat.format(
				"FROM {0} o WHERE {1} = :param", new Object[] { c.getName(),
						attributeName });
		return getHibernateTemplate().findByNamedParam(queryString, "param",
				attributeValue);
	}

	/**
	 * @param c
	 * @param indexAttributeName
	 *            pk field name
	 * @return - quantidade de registros encontrados na classe
	 */
	protected int countRows(final Class c, final String indexAttributeName) {
		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String query = MessageFormat.format(
								"SELECT COUNT(o.{0}) FROM {1} o", new String[] {
										indexAttributeName, c.getName() });
						return (Integer) session.createQuery(query)
								.uniqueResult();
					}
				})).intValue();
	}

	/**
	 * @param c
	 * @param indexAttributeName
	 *            pk field name
	 * @param whereClause
	 * @param params
	 *            TODO
	 * @return - quantidade de registros encontrados na classe
	 */
	protected int countRowsWhere(final Class c,
			final String indexAttributeName, final String whereClause,
			final Object[] params) {

		return ((Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String queryString = MessageFormat.format(
								"SELECT COUNT({0}) FROM {1} o WHERE {2}", //
								new String[] { indexAttributeName, c.getName(),
										whereClause });
						Query query = session.createQuery(queryString);
						if (params != null) {
							for (int i = 0; i < params.length; i++) {
								query.setParameter(i, params[i]);
							}
						}

						return (Integer) query.iterate().next();
					}
				})).intValue();
	}

	/**
	 * @param c
	 * @param where
	 * @param params
	 * @param orderBy
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return result list
	 */
	protected List find(final Class c, final String where,
			final Object[] params, final String orderBy, final int pageNumber,
			final int itemsPerPage) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String hql = MessageFormat.format(
						"FROM {0} o WHERE {1} ORDER BY {2}", new Object[] {
								c.getName(), where, orderBy });

				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				if (pageNumber >= 0 && itemsPerPage > 0) {
					// avoid negative numbers
					int firstResult = Math.max(0, pageNumber - 1)
							* itemsPerPage;
					query.setFirstResult(firstResult);
					query.setMaxResults(itemsPerPage);
				}
				return query.list();
			}
		});
	}

	protected List find(final String hql, final Object[] params,
			final int pageNumber, final int itemsPerPage) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				if (pageNumber >= 0 && itemsPerPage > 0) {
					// avoid negative numbers
					int firstResult = Math.max(0, pageNumber - 1)
							* itemsPerPage;
					query.setFirstResult(firstResult);
					query.setMaxResults(itemsPerPage);
				}
				return query.list();
			}
		});
	}

	/**
	 * @param c
	 * @param orderBy
	 * @param pageNumber
	 * @param itemsPerPage
	 * @return result list
	 */
	protected List findAll(final Class c, final String orderBy,
			final int pageNumber, final int itemsPerPage) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String hql = MessageFormat.format("FROM {0} o ORDER BY {1}",
						new Object[] { c.getName(), orderBy });

				Query query = session.createQuery(hql);
				if (pageNumber >= 0 && itemsPerPage > 0) {
					query.setFirstResult(Math.max(0, pageNumber - 1)
							* itemsPerPage);
					query.setMaxResults(itemsPerPage);
				}
				return query.list();
			}
		});
	}

	protected String getFieldName(int i) {
		return "";
	}

	/**
	 * @param c
	 * @param sortingFields
	 * @return
	 */
	protected List findAll(Class c, int[] sortingFields) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sortingFields.length; i++) {
			if (sortingFields[i] > 0) {
				sb.append("o.");
				sb.append(getFieldName(sortingFields[i]));
				sb.append(" ASC");
			} else if (sortingFields[i] < 0) {
				sb.append("o.");
				sb.append(getFieldName(-sortingFields[i]));
				sb.append(" DESC");
			} else {
				throw new IllegalArgumentException("");
			}
			if (i + 1 < sortingFields.length) {
				sb.append(", ");
			}
		}
		return findAll(c, sb.toString(), ALL_PAGES, ALL_PAGES);
	}

	/**
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected Object load(Class cls, Long id) {
		try {
			return getSession().load(cls, id);
		} catch (Exception e) {
			System.out.println("Error at load of HibernateDAO:" + e);
			return null;
		}
	}

	public Object add(Object obj) throws Exception {
		return getHibernateTemplate().save(obj);
	}

	public List loadAll(Object obj) throws Exception {
		return getHibernateTemplate().loadAll(obj.getClass());
	}

	public Object load(Object obj, Long id) throws Exception {
		return getHibernateTemplate().load(obj.getClass(), id);
	}

	public void delete(Object obj) throws Exception {
		getHibernateTemplate().delete(obj);
	}

	public void invalidateCache() {
	}

	@SuppressWarnings("unchecked")
	public void persistentCacheOnMap(HashMap map, String tableName,
			String clChange, String clKey) throws Exception {

		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			Long key = (Long) it.next();
			Integer value = (Integer) map.get(key);
			String sql = MessageFormat.format(
					"update {0} set {1} = {2} where {3} = {4}", new Object[] {
							tableName, clChange, value, clKey, key });

			this.executeSQL(sql);
		}
	}

	public List findByQueryParam(final String hqlQuery, final Map paramValues,
			final int init, final int maxRows) throws Exception {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(hqlQuery);

				Iterator iter = paramValues.keySet().iterator();
				while (iter.hasNext()) {
					String name = (String) iter.next();
					Object value = paramValues.get(name);
					query.setParameter(name, value);
				}
				query.setFirstResult(init);
				query.setMaxResults(maxRows);
				return query.list();
			}
		});
	}

}
