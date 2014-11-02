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
 * Doctor entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Doctor
 * @author MyEclipse Persistence Tools
 */

public class DoctorDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(DoctorDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String IDENTITY_CODE = "identityCode";

	protected void initDao() {
		// do nothing
	}

	public void save(Doctor transientInstance) {
		log.debug("saving Doctor instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Doctor persistentInstance) {
		log.debug("deleting Doctor instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Doctor findById(java.lang.Integer id) {
		log.debug("getting Doctor instance with id: " + id);
		try {
			Doctor instance = (Doctor) getHibernateTemplate().get(
					"com.drcall.db.dao.Doctor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Doctor> findByExample(Doctor instance) {
		log.debug("finding Doctor instance by example");
		try {
			List<Doctor> results = (List<Doctor>) getHibernateTemplate()
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
		log.debug("finding Doctor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Doctor as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Doctor> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Doctor> findByIdentityCode(Object identityCode) {
		return findByProperty(IDENTITY_CODE, identityCode);
	}

	public List findAll() {
		log.debug("finding all Doctor instances");
		try {
			String queryString = "from Doctor";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Doctor merge(Doctor detachedInstance) {
		log.debug("merging Doctor instance");
		try {
			Doctor result = (Doctor) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Doctor instance) {
		log.debug("attaching dirty Doctor instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Doctor instance) {
		log.debug("attaching clean Doctor instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DoctorDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DoctorDAO) ctx.getBean("DoctorDAO");
	}
}