package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Appoint entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Appoint
 * @author MyEclipse Persistence Tools
 */

public class AppointDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(AppointDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String TEL = "tel";
	public static final String STATUS = "status";
	public static final String SHIFT = "shift";
	public static final String NOTIFY_TAKE = "notifyTake";
	public static final String TYPE = "type";
	public static final String APP_NUMBER = "appNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(Appoint transientInstance) {
		log.debug("saving Appoint instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Appoint persistentInstance) {
		log.debug("deleting Appoint instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Appoint findById(java.lang.Long id) {
		log.debug("getting Appoint instance with id: " + id);
		try {
			Appoint instance = (Appoint) getHibernateTemplate().get(
					"com.drcall.db.dao.Appoint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Appoint> findByExample(Appoint instance) {
		log.debug("finding Appoint instance by example");
		try {
			List<Appoint> results = (List<Appoint>) getHibernateTemplate()
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
		log.debug("finding Appoint instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Appoint as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Appoint> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Appoint> findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List<Appoint> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<Appoint> findByShift(Object shift) {
		return findByProperty(SHIFT, shift);
	}

	public List<Appoint> findByNotifyTake(Object notifyTake) {
		return findByProperty(NOTIFY_TAKE, notifyTake);
	}

	public List<Appoint> findByType(Object type) {
		return findByProperty(TYPE, type);
	}
	
	public List<Appoint> findByAppNumber(Object appNumber) {
		return findByProperty(APP_NUMBER, appNumber);
	}

	public List findAll() {
		log.debug("finding all Appoint instances");
		try {
			String queryString = "from Appoint";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Appoint merge(Appoint detachedInstance) {
		log.debug("merging Appoint instance");
		try {
			Appoint result = (Appoint) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Appoint instance) {
		log.debug("attaching dirty Appoint instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Appoint instance) {
		log.debug("attaching clean Appoint instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AppointDAO getFromApplicationContext(ApplicationContext ctx) {
		return (AppointDAO) ctx.getBean("AppointDAO");
	}
}