package com.drcall.db.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Family entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Family
 * @author MyEclipse Persistence Tools
 */

public class FamilyDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(FamilyDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String TEL = "tel";
	public static final String ACTIVE = "active";
	public static final String BIRTH_DAY = "birthDay";
	public static final String BIRTH_MONTH = "birthMonth";
	public static final String BIRTH_YEAR = "birthYear";
	public static final String GENDER = "gender";
	public static final String ID_NUMBER = "idNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(Family transientInstance) {
		log.debug("saving Family instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Family persistentInstance) {
		log.debug("deleting Family instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Family findById(java.lang.Long id) {
		log.debug("getting Family instance with id: " + id);
		try {
			Family instance = (Family) getHibernateTemplate().get(
					"com.drcall.db.dao.Family", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Family> findByExample(Family instance) {
		log.debug("finding Family instance by example");
		try {
			List<Family> results = (List<Family>) getHibernateTemplate()
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
		log.debug("finding Family instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Family as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Family> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Family> findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List<Family> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List<Family> findByBirthDay(Object birthDay) {
		return findByProperty(BIRTH_DAY, birthDay);
	}

	public List<Family> findByBirthMonth(Object birthMonth) {
		return findByProperty(BIRTH_MONTH, birthMonth);
	}

	public List<Family> findByBirthYear(Object birthYear) {
		return findByProperty(BIRTH_YEAR, birthYear);
	}

	public List<Family> findByGender(Object gender) {
		return findByProperty(GENDER, gender);
	}

	public List<Family> findByIdNumber(Object idNumber) {
		return findByProperty(ID_NUMBER, idNumber);
	}

	public List findAll() {
		log.debug("finding all Family instances");
		try {
			String queryString = "from Family";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Family merge(Family detachedInstance) {
		log.debug("merging Family instance");
		try {
			Family result = (Family) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Family instance) {
		log.debug("attaching dirty Family instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Family instance) {
		log.debug("attaching clean Family instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static FamilyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (FamilyDAO) ctx.getBean("FamilyDAO");
	}
}