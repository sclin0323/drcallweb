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
 * FreeExperience entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.drcall.db.dao.FreeExperience
 * @author MyEclipse Persistence Tools
 */

public class FreeExperienceDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(FreeExperienceDAO.class);
	// property constants
	public static final String CHECK_NUMBER = "checkNumber";
	public static final String STATUS = "status";
	public static final String TEL = "tel";
	public static final String IDENTITY_ID = "identityId";
	public static final String NAME = "name";
	public static final String BIRTH_YEAR = "birthYear";
	public static final String BIRTH_MONTH = "birthMonth";
	public static final String BIRTH_DAY = "birthDay";
	public static final String ADDRESS = "address";

	protected void initDao() {
		// do nothing
	}

	public void save(FreeExperience transientInstance) {
		log.debug("saving FreeExperience instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FreeExperience persistentInstance) {
		log.debug("deleting FreeExperience instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FreeExperience findById(java.lang.Long id) {
		log.debug("getting FreeExperience instance with id: " + id);
		try {
			FreeExperience instance = (FreeExperience) getHibernateTemplate()
					.get("com.drcall.db.dao.FreeExperience", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<FreeExperience> findByExample(FreeExperience instance) {
		log.debug("finding FreeExperience instance by example");
		try {
			List<FreeExperience> results = (List<FreeExperience>) getHibernateTemplate()
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
		log.debug("finding FreeExperience instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from FreeExperience as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<FreeExperience> findByCheckNumber(Object checkNumber) {
		return findByProperty(CHECK_NUMBER, checkNumber);
	}

	public List<FreeExperience> findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List<FreeExperience> findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List<FreeExperience> findByIdentityId(Object identityId) {
		return findByProperty(IDENTITY_ID, identityId);
	}

	public List<FreeExperience> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<FreeExperience> findByBirthYear(Object birthYear) {
		return findByProperty(BIRTH_YEAR, birthYear);
	}

	public List<FreeExperience> findByBirthMonth(Object birthMonth) {
		return findByProperty(BIRTH_MONTH, birthMonth);
	}

	public List<FreeExperience> findByBirthDay(Object birthDay) {
		return findByProperty(BIRTH_DAY, birthDay);
	}

	public List<FreeExperience> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findAll() {
		log.debug("finding all FreeExperience instances");
		try {
			String queryString = "from FreeExperience";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FreeExperience merge(FreeExperience detachedInstance) {
		log.debug("merging FreeExperience instance");
		try {
			FreeExperience result = (FreeExperience) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FreeExperience instance) {
		log.debug("attaching dirty FreeExperience instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FreeExperience instance) {
		log.debug("attaching clean FreeExperience instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FreeExperienceDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (FreeExperienceDAO) ctx.getBean("FreeExperienceDAO");
	}
}