package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * SystemEmail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.drcall.db.dao.SystemEmail
 * @author MyEclipse Persistence Tools
 */

public class SystemEmailDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(SystemEmailDAO.class);
	// property constants
	public static final String SEND_TO = "sendTo";
	public static final String TITLE = "title";
	public static final String TEXT = "text";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(SystemEmail transientInstance) {
		log.debug("saving SystemEmail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SystemEmail persistentInstance) {
		log.debug("deleting SystemEmail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SystemEmail findById(java.lang.Long id) {
		log.debug("getting SystemEmail instance with id: " + id);
		try {
			SystemEmail instance = (SystemEmail) getHibernateTemplate().get(
					"com.drcall.db.dao.SystemEmail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SystemEmail> findByExample(SystemEmail instance) {
		log.debug("finding SystemEmail instance by example");
		try {
			List<SystemEmail> results = (List<SystemEmail>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SystemEmail instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SystemEmail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SystemEmail> findBySendTo(Object sendTo) {
		return findByProperty(SEND_TO, sendTo);
	}

	public List<SystemEmail> findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List<SystemEmail> findByText(Object text) {
		return findByProperty(TEXT, text);
	}

	public List<SystemEmail> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all SystemEmail instances");
		try {
			String queryString = "from SystemEmail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SystemEmail merge(SystemEmail detachedInstance) {
		log.debug("merging SystemEmail instance");
		try {
			SystemEmail result = (SystemEmail) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SystemEmail instance) {
		log.debug("attaching dirty SystemEmail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SystemEmail instance) {
		log.debug("attaching clean SystemEmail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SystemEmailDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (SystemEmailDAO) ctx.getBean("SystemEmailDAO");
	}
}