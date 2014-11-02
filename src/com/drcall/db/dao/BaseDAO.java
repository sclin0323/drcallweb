package com.drcall.db.dao;


import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.drcall.client.command.BaseCommand;

public abstract class BaseDAO extends HibernateDaoSupport {

	private static final Log log = LogFactory.getLog(BaseDAO.class);
	
	protected DataSource dataSource;
	
	
	public List findByCriteria(DetachedCriteria criteria,
			int startRow, int endRow) {
		log.debug("finding by search criteria");

		try {
			return getHibernateTemplate().findByCriteria(criteria, startRow,
					endRow);
		} catch (RuntimeException re) {
			log.error("find by criteria failed", re);
			throw re;
		}
	}
	
	public List findByCriteria(DetachedCriteria criteria) {
		log.debug("finding by search criteria");

		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (RuntimeException re) {
			log.error("find by criteria failed", re);
			throw re;
		}
	}
	
	
	
	
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	abstract protected DetachedCriteria getDetachedCriteria(
			BaseCommand baseCommand);

	public int getCountByCriteria(BaseCommand command) {
		return getCountByCriteria(getDetachedCriteria(command));
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		if(detachedCriteria == null){
			return 0;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(org.hibernate.Session session)
							throws HibernateException, SQLException {
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				});

		return count.intValue();
	}

	protected Date getEndDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public List<?> findByNativeSql(final String sql) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws HibernateException, SQLException {
				List<Object> ls = (List<Object>) session.createSQLQuery(sql).list();
				return ls;
			}
		});
	}
	
	protected int eq(DetachedCriteria criteria, BaseCommand command, String name, Object value){
		if(value instanceof Integer){
			return eq(criteria, command, name, (Integer)value);
		} else if(value instanceof String){
			return eq(criteria, command, name, (String)value);
		} else if(value instanceof Long){
			return eq(criteria, command, name, (Long)value);
		} else if(value instanceof Date){
			return eq(criteria, command, name, (Date)value);
		} else if(value instanceof Double){
			return eq(criteria, command, name, (Double)value);
		}
		return 0;
	}

	protected int eq(DetachedCriteria criteria, BaseCommand command,
			String name, String value) {
		if (value != null && value.length() > 0) {
			if (command.isPreciseMatch()) {
				criteria.add(Restrictions.eq(name, value));
			} else {
				criteria.add(Restrictions.like(name, "%" + value + "%"));
			}
			return 1;
		}
		return 0;
	}

	protected int eq(DetachedCriteria criteria, BaseCommand command,
			String name, Integer value) {

		if (value != null) {
			criteria.add(Restrictions.eq(name, value));
			return 1;
		}
		return 0;
	}
	
	protected int eq(DetachedCriteria criteria, BaseCommand command,
			String name, Double value) {
		if (value != null) {
			criteria.add(Restrictions.eq(name, value));
			return 1;
		}
		return 0;
	}
	
	protected int eq(DetachedCriteria criteria, BaseCommand command,
			String name, Long value) {

		if (value != null && value > 0) {
			criteria.add(Restrictions.eq(name, value));
			return 1;
		}
		return 0;
	}
	
	
	protected int eq(DetachedCriteria criteria, BaseCommand command,
			String name, Date value) {

		if (value != null) {
			criteria.add(Restrictions.ge(name, value));
			criteria.add(Restrictions.le(name, getEndDate(value)));
			return 1;
		}
		return 0;
	}
	
	public abstract List findByCommand(BaseCommand command);
}
