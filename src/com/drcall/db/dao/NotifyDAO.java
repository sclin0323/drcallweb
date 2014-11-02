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
 * Notify entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Notify
 * @author MyEclipse Persistence Tools
 */

public class NotifyDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(NotifyDAO.class);
	// property constants
	public static final String TYPE = "type";
	public static final String CONTENT = "content";

	protected void initDao() {
		// do nothing
	}

	public void save(Notify transientInstance) {
		log.debug("saving Notify instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Notify persistentInstance) {
		log.debug("deleting Notify instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Notify findById(java.lang.Long id) {
		log.debug("getting Notify instance with id: " + id);
		try {
			Notify instance = (Notify) getHibernateTemplate().get(
					"com.drcall.db.dao.Notify", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Notify> findByExample(Notify instance) {
		log.debug("finding Notify instance by example");
		try {
			List<Notify> results = (List<Notify>) getHibernateTemplate()
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
		log.debug("finding Notify instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Notify as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Notify> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List<Notify> findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findAll() {
		log.debug("finding all Notify instances");
		try {
			String queryString = "from Notify";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Notify merge(Notify detachedInstance) {
		log.debug("merging Notify instance");
		try {
			Notify result = (Notify) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Notify instance) {
		log.debug("attaching dirty Notify instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Notify instance) {
		log.debug("attaching clean Notify instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static NotifyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (NotifyDAO) ctx.getBean("NotifyDAO");
	}
}