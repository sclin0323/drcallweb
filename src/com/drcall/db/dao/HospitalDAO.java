package com.drcall.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Hospital entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Hospital
 * @author MyEclipse Persistence Tools
 */

public class HospitalDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(HospitalDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String ACTIVE = "active";
	public static final String CITY = "city";
	public static final String IS_CONTRACT = "isContract";
	public static final String MESSAGE = "message";

	protected void initDao() {
		// do nothing
	}

	public void save(Hospital transientInstance) {
		log.debug("saving Hospital instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Hospital persistentInstance) {
		log.debug("deleting Hospital instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Hospital findById(java.lang.String id) {
		log.debug("getting Hospital instance with id: " + id);
		try {
			Hospital instance = (Hospital) getHibernateTemplate().get(
					"com.drcall.db.dao.Hospital", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Hospital> findByExample(Hospital instance) {
		log.debug("finding Hospital instance by example");
		try {
			List<Hospital> results = (List<Hospital>) getHibernateTemplate()
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
		log.debug("finding Hospital instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Hospital as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Hospital> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Hospital> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List<Hospital> findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List<Hospital> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List<Hospital> findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List<Hospital> findByIsContract(Object isContract) {
		return findByProperty(IS_CONTRACT, isContract);
	}

	public List<Hospital> findByMessage(Object message) {
		return findByProperty(MESSAGE, message);
	}

	public List findAll() {
		log.debug("finding all Hospital instances");
		try {
			String queryString = "from Hospital";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Hospital merge(Hospital detachedInstance) {
		log.debug("merging Hospital instance");
		try {
			Hospital result = (Hospital) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Hospital instance) {
		log.debug("attaching dirty Hospital instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Hospital instance) {
		log.debug("attaching clean Hospital instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static HospitalDAO getFromApplicationContext(ApplicationContext ctx) {
		return (HospitalDAO) ctx.getBean("HospitalDAO");
	}
}