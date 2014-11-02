package com.drcall.db.dao;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Division entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Division
 * @author MyEclipse Persistence Tools
 */

public class DivisionDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(DivisionDAO.class);
	// property constants
	public static final String CN_NAME = "cnName";
	public static final String EN_NAME = "enName";
	public static final String NOTE = "note";
	public static final String ACTIVE = "active";

	protected void initDao() {
		// do nothing
	}

	public void save(Division transientInstance) {
		log.debug("saving Division instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Division persistentInstance) {
		log.debug("deleting Division instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Division findById(java.lang.String id) {
		log.debug("getting Division instance with id: " + id);
		try {
			Division instance = (Division) getHibernateTemplate().get(
					"com.drcall.db.dao.Division", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Division> findByExample(Division instance) {
		log.debug("finding Division instance by example");
		try {
			List<Division> results = (List<Division>) getHibernateTemplate()
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
		log.debug("finding Division instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Division as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Division> findByCnName(Object cnName) {
		return findByProperty(CN_NAME, cnName);
	}

	public List<Division> findByEnName(Object enName) {
		return findByProperty(EN_NAME, enName);
	}

	public List<Division> findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List<Division> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List findAll() {
		log.debug("finding all Division instances");
		try {
			String queryString = "from Division";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Division merge(Division detachedInstance) {
		log.debug("merging Division instance");
		try {
			Division result = (Division) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Division instance) {
		log.debug("attaching dirty Division instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Division instance) {
		log.debug("attaching clean Division instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DivisionDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DivisionDAO) ctx.getBean("DivisionDAO");
	}
}