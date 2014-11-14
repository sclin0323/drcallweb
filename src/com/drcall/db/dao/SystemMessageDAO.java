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
 * SystemMessage entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.drcall.db.dao.SystemMessage
 * @author MyEclipse Persistence Tools
 */

public class SystemMessageDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(SystemMessageDAO.class);
	// property constants
	public static final String MOBILE = "mobile";
	public static final String STATUS = "status";
	public static final String SUBJECT = "subject";
	public static final String CONTENT = "content";

	protected void initDao() {
		// do nothing
	}

	public void save(SystemMessage transientInstance) {
		log.debug("saving SystemMessage instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(SystemMessage persistentInstance) {
		log.debug("deleting SystemMessage instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SystemMessage findById(java.lang.Long id) {
		log.debug("getting SystemMessage instance with id: " + id);
		try {
			SystemMessage instance = (SystemMessage) getHibernateTemplate()
					.get("com.drcall.db.dao.SystemMessage", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<SystemMessage> findByExample(SystemMessage instance) {
		log.debug("finding SystemMessage instance by example");
		try {
			List<SystemMessage> results = (List<SystemMessage>) getHibernateTemplate()
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
		log.debug("finding SystemMessage instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from SystemMessage as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<SystemMessage> findByMobile(Object mobile) {
		return findByProperty(MOBILE, mobile);
	}

	public List<SystemMessage> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<SystemMessage> findBySubject(Object subject) {
		return findByProperty(SUBJECT, subject);
	}

	public List<SystemMessage> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all SystemMessage instances");
		try {
			String queryString = "from SystemMessage";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SystemMessage merge(SystemMessage detachedInstance) {
		log.debug("merging SystemMessage instance");
		try {
			SystemMessage result = (SystemMessage) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SystemMessage instance) {
		log.debug("attaching dirty SystemMessage instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SystemMessage instance) {
		log.debug("attaching clean SystemMessage instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SystemMessageDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (SystemMessageDAO) ctx.getBean("SystemMessageDAO");
	}
}