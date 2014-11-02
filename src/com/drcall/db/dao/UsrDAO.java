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
 * A data access object (DAO) providing persistence and search support for Usr
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.drcall.db.dao.Usr
 * @author MyEclipse Persistence Tools
 */

public class UsrDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(UsrDAO.class);
	// property constants
	public static final String PASSWD = "passwd";
	public static final String NAME = "name";
	public static final String NOTE = "note";
	public static final String ACTIVE = "active";

	protected void initDao() {
		// do nothing
	}

	public void save(Usr transientInstance) {
		log.debug("saving Usr instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Usr persistentInstance) {
		log.debug("deleting Usr instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Usr findById(java.lang.String id) {
		log.debug("getting Usr instance with id: " + id);
		try {
			Usr instance = (Usr) getHibernateTemplate().get(
					"com.drcall.db.dao.Usr", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Usr> findByExample(Usr instance) {
		log.debug("finding Usr instance by example");
		try {
			List<Usr> results = (List<Usr>) getHibernateTemplate()
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
		log.debug("finding Usr instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Usr as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Usr> findByPasswd(Object passwd) {
		return findByProperty(PASSWD, passwd);
	}

	public List<Usr> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Usr> findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List<Usr> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findAll() {
		log.debug("finding all Usr instances");
		try {
			String queryString = "from Usr";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Usr merge(Usr detachedInstance) {
		log.debug("merging Usr instance");
		try {
			Usr result = (Usr) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Usr instance) {
		log.debug("attaching dirty Usr instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Usr instance) {
		log.debug("attaching clean Usr instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UsrDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UsrDAO) ctx.getBean("UsrDAO");
	}
}