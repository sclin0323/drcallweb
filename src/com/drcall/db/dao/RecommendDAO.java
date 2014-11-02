package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Recommend entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Recommend
 * @author MyEclipse Persistence Tools
 */

public class RecommendDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(RecommendDAO.class);
	// property constants
	public static final String EMAIL = "email";
	public static final String ACTIVE = "active";
	public static final String STATUS = "status";
	public static final String NAME = "name";
	public static final String TEL = "tel";

	protected void initDao() {
		// do nothing
	}

	public void save(Recommend transientInstance) {
		log.debug("saving Recommend instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Recommend persistentInstance) {
		log.debug("deleting Recommend instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Recommend findById(java.lang.String id) {
		log.debug("getting Recommend instance with id: " + id);
		try {
			Recommend instance = (Recommend) getHibernateTemplate().get(
					"com.drcall.db.dao.Recommend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Recommend> findByExample(Recommend instance) {
		log.debug("finding Recommend instance by example");
		try {
			List<Recommend> results = (List<Recommend>) getHibernateTemplate()
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
		log.debug("finding Recommend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Recommend as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Recommend> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<Recommend> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List<Recommend> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<Recommend> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Recommend> findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List findAll() {
		log.debug("finding all Recommend instances");
		try {
			String queryString = "from Recommend";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Recommend merge(Recommend detachedInstance) {
		log.debug("merging Recommend instance");
		try {
			Recommend result = (Recommend) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Recommend instance) {
		log.debug("attaching dirty Recommend instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Recommend instance) {
		log.debug("attaching clean Recommend instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RecommendDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RecommendDAO) ctx.getBean("RecommendDAO");
	}
}