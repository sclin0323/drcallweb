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
 * Consume entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Consume
 * @author MyEclipse Persistence Tools
 */

public class ConsumeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(ConsumeDAO.class);
	// property constants
	public static final String STATUS = "status";
	public static final String SUM = "sum";
	public static final String POINT = "point";
	public static final String TYPE = "type";

	protected void initDao() {
		// do nothing
	}

	public void save(Consume transientInstance) {
		log.debug("saving Consume instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Consume persistentInstance) {
		log.debug("deleting Consume instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Consume findById(java.lang.Long id) {
		log.debug("getting Consume instance with id: " + id);
		try {
			Consume instance = (Consume) getHibernateTemplate().get(
					"com.drcall.db.dao.Consume", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Consume> findByExample(Consume instance) {
		log.debug("finding Consume instance by example");
		try {
			List<Consume> results = (List<Consume>) getHibernateTemplate()
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
		log.debug("finding Consume instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Consume as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Consume> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<Consume> findBySum(Object sum) {
		return findByProperty(SUM, sum);
	}

	public List<Consume> findByPoint(Object point) {
		return findByProperty(POINT, point);
	}

	public List<Consume> findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	public List findAll() {
		log.debug("finding all Consume instances");
		try {
			String queryString = "from Consume";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Consume merge(Consume detachedInstance) {
		log.debug("merging Consume instance");
		try {
			Consume result = (Consume) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Consume instance) {
		log.debug("attaching dirty Consume instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Consume instance) {
		log.debug("attaching clean Consume instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ConsumeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ConsumeDAO) ctx.getBean("ConsumeDAO");
	}
}