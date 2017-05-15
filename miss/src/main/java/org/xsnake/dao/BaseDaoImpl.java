package org.xsnake.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

@SuppressWarnings("unchecked")
public class BaseDaoImpl implements BaseDao {

	HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	private Query createQuery(Session session, String hql, final Object[] args) {
		Query query = session.createQuery(hql);
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		return query;
	}

	private SQLQuery createSQLQuery(Session session, String sql, Object[] args) {
		SQLQuery query = session.createSQLQuery(sql);
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		return query;
	}

	public <T> void deleteAll(List<T> list) {
		getHibernateTemplate().deleteAll(list);
	}

	public <T> void deleteById(Class<T> c, Serializable id) {
		T t = this.load(c, id);
		this.delete(t);
	}

	public <T> void save(List<T> l) {
		for (T t : l) {
			getHibernateTemplate().save(t);
		}
	}

	public <T> T get(Class<T> c, Serializable k) {
		if (k == null) {
			return null;
		}
		return (T) this.getHibernateTemplate().get(c, k);
	}

	public <T> T load(Class<T> c, Serializable k) {
		return (T) this.getHibernateTemplate().load(c, k);
	}

	public <T> T save(T t) {
		return (T) this.getHibernateTemplate().save(t);
	}

	public <T> T update(T t) {
		this.getHibernateTemplate().update(t);
		return t;
	}

	public <T> void update(List<T> l) {
		for (T t : l) {
			getHibernateTemplate().update(t);
		}
	}

	public void saveOrUpdate(Object t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	public <T> T findUniqueEntity(final String hql, final Object[] args) {
		HibernateCallback<T> callback = new HibernateCallback<T>() {

			public T doInHibernate(Session session) throws HibernateException {
				try {
					Query query = createQuery(session, hql, args);
					return (T) query.uniqueResult();
				} finally {
					session.close();
				}
			}

		};
		return getHibernateTemplate().execute(callback);
	}

	public void delete(Object o) {
		this.getHibernateTemplate().delete(o);
	}

	public <T> List<T> findEntity(String hql) {
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	public <T> List<T> findEntity(String hql, Object obj) {
		return findEntity(hql, new Object[] { obj });
	}

	public <T> List<T> findEntity(final String hql, final Object[] args) {

		HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session) throws HibernateException {
				try {
					Query query = createQuery(session, hql, args);
					return query.list();
				} finally {
					session.close();
				}
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	public <T> List<T> findEntity(final String hql, final int start, final int num) {
		return findEntity(hql, null, start, num);
	}

	public <T> List<T> findEntity(final String hql, final Object[] args, final int start, final int num) {

		HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {

			public List<T> doInHibernate(Session session) throws HibernateException {
				try {
					Query query = createQuery(session, hql, args);
					query.setFirstResult(start).setMaxResults(num);
					return query.list();
				} finally {
					session.close();
				}
			}
		};

		return getHibernateTemplate().execute(callback);
	}

	public void executeHQL(final String hql) {
		executeHQL(hql, null);
	}

	public void executeHQL(String hql, Object args) {
		executeHQL(hql, new Object[] { args });
	}

	public void executeHQL(final String hql, final Object[] args) {

		HibernateCallback<Object> callback = new HibernateCallback<Object>() {

			public Object doInHibernate(Session session) throws HibernateException {
				try {
					Query query = createQuery(session, hql, args);
					return query.executeUpdate();
				} finally {
					session.close();
				}
			}
		};

		getHibernateTemplate().execute(callback);
	}

	public void executeSQL(String sql) {
		executeSQL(sql, null);
	}

	public void executeSQL(final String sql, final Object args) {
		executeSQL(sql, new Object[] { args });
	}

	public void executeSQL(final String sql, final Object[] args) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {

			public Object doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					return query.executeUpdate();
				} finally {
					session.close();
				}
			}
		};
		getHibernateTemplate().execute(callback);
	}

	public List<Object[]> findBySql(final String sql) {
		return findBySql(sql, null);
	}

	public List<Object[]> findBySql(final String sql, final Object[] args) {
		HibernateCallback<List<Object[]>> callback = new HibernateCallback<List<Object[]>>() {

			public List<Object[]> doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					return query.list();
				} finally {
					session.close();
				}
			}
		};

		return getHibernateTemplate().execute(callback);
	}

	public List<Object[]> findBySql(String sql, Object args) {
		return findBySql(sql, new Object[] { args });
	}

	public List<Object[]> findBySql(final String sql, final Object[] args, final int start, final int num) {
		HibernateCallback<List<Object[]>> callback = new HibernateCallback<List<Object[]>>() {

			public List<Object[]> doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					query.setFirstResult(start).setMaxResults(num);
					return query.list();
				} finally {
					session.close();
				}
			}
		};

		return getHibernateTemplate().execute(callback);
	}

	public List<Object[]> findBySql(String sql, int start, int num) {
		return findBySql(sql, null, start, num);
	}

	public List<Object[]> findBySql(String sql, Object obj, int start, int num) {
		return findBySql(sql, new Object[] { obj }, start, num);
	}

	public <T> T findUniqueBySql(final String sql, final Object[] args) {
		HibernateCallback<T> callback = new HibernateCallback<T>() {

			public T doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					return (T) query.uniqueResult();
				} finally {
					session.close();
				}

			}
		};
		return getHibernateTemplate().execute(callback);
	}

	public <T> T findUniqueBySql(String sql, Object obj) {
		return findUniqueBySql(sql, new Object[] { obj });
	}

	public <T> T findUniqueBySql(String sql) {
		return findUniqueBySql(sql, null);
	}

	public <T> T findUniqueEntity(String hql) {
		return findUniqueEntity(hql, null);
	}

	public <T> T findUniqueEntity(String hql, Object args) {
		return findUniqueEntity(hql, new Object[] { args });
	}

	public <T> T uniqueFindBySql(final String sql, final Object[] args) {

		HibernateCallback<T> callback = new HibernateCallback<T>() {

			public T doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					return (T) query.uniqueResult();
				} finally {
					session.close();
				}

			}
		};

		return getHibernateTemplate().execute(callback);
	}

	public IPage search(String hql, int size, int goPage) {
		return this.searchForPager(hql, null, size, goPage);
	}

	public IPage search(String hql, Object[] args, int size, int goPage) {
		return this.searchForPager(hql, args, size, goPage);
	}

	/**
	 * 此方法相比searchForPager方法，可以再hql语句中使用SELECT子句(2013/8/3)
	 */
	public IPage searchForPager(String hql, Object[] args, int pageLength, int currentPage) {
		try {
			String thql = null;
			if (hql.indexOf("select") == -1) {
				thql = "select count(*) " + hql;
			} else {
				thql = "select count(*) " + hql.substring(hql.indexOf("from"));
			}
			if (hql.indexOf("order by") > -1) {
				thql = thql.substring(0, thql.indexOf("order by"));
			}
			Long _count = findUniqueEntity(thql, args);
			int count = _count.intValue();
			return searchForPager(hql, args, pageLength, currentPage, count);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private IPage searchForPager(String hql, Object[] args, int pageLength, int currentPage, int count) {
		try {
			List<Object> results = findEntity(hql, args, (currentPage - 1) * pageLength, pageLength);
			return new PageImpl(results, currentPage, pageLength, count);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public IPage searchBySql(String sql, int size, int goPage) {
		return searchForPagerBySql(sql, null, size, goPage);
	}

	public IPage searchBySql(String sql, Object[] args, int size, int goPage) {
		return searchForPagerBySql(sql, args, size, goPage);
	}

	private static class ListMapResultTransformer implements ResultTransformer {
		private static final long serialVersionUID = 1L;
		public Object transformTuple(Object[] values, String[] columns) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			int i = 0;
			for (String column : columns) {
				map.put(column, values[i++]);
			}
			return map;
		}
		@SuppressWarnings("rawtypes")
		public List transformList(List collection) {
			return collection;
		}
	}
	
	public Map<String,Object> findUniqueBySql4Map(final String sql, final Object args) {
		return findUniqueBySql4Map(sql, new Object[]{args});
	}

	public Map<String,Object> findUniqueBySql4Map(final String sql, final Object[] args) {
		HibernateCallback<Object> callback = new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException {
				try {
					SQLQuery query = createSQLQuery(session, sql, args);
					query.setResultTransformer(new ListMapResultTransformer());
					return query.uniqueResult();
				} finally {
					session.close();
				}
			}
		};
		return (Map<String,Object>)getHibernateTemplate().execute(callback);
	}
	
	//TODO 实现其他的Map<String,Object>方式的查询

	private IPage searchForPagerBySql(String sql, Object[] args, int pageLength, int currentPage) {
		try {
			String thql = null;
			thql = "select count(1) from (" + sql + ") t ";
			BigDecimal _count = findUniqueBySql(thql, args);
			int count = _count.intValue();
			return searchForPagerBySql(sql, args, pageLength, currentPage, count);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private IPage searchForPagerBySql(String sql, Object[] args, int pageLength, int currentPage, int count) {
		try {
			List<Object[]> results = findBySql(sql, args, (currentPage - 1) * pageLength, pageLength);
			return new PageImpl(results, currentPage, pageLength, count);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object[] executeProcedure(final String sql, final ProcedureParam[] args) {
		HibernateCallback<Object[]> callback = new HibernateCallback<Object[]>() {

			public Object[] doInHibernate(Session session) throws HibernateException {
				try {
					final List<Object> result = new ArrayList<Object>();
					session.doWork(new Work() {

						public void execute(Connection connection) throws SQLException {
							CallableStatement sta = null;
							try {
								sta = connection.prepareCall(sql);
								for (int i = 1; i < args.length + 1; i++) {
									ProcedureParam p = args[(i - 1)];
									if (p instanceof InProcedureParam) {
										sta.setObject(i, p.value);
									} else if (p instanceof OutProcedureParam) {
										sta.registerOutParameter(i, p.type);
									}
								}
								sta.execute();
								for (int i = 1; i < args.length + 1; i++) {
									ProcedureParam p = args[(i - 1)];
									if (p instanceof OutProcedureParam) {
										result.add(sta.getString(i));
									} else {
										result.add(null);
									}
								}
							} finally {
								if (sta != null) {
									sta.close();
								}
							}
						}
					});
					return result.toArray();
				} finally {
					session.close();
				}
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
