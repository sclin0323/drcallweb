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
 * Member entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Member
 * @author MyEclipse Persistence Tools
 */

public class MemberDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(MemberDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PASSWD = "passwd";
	public static final String ACTIVE = "active";
	public static final String IS_IDENTIFY = "isIdentify";
	public static final String IDENTIFY_CODE = "identifyCode";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String IS_RECOMMEND = "isRecommend";
	public static final String RECOMMEND_ID = "recommendId";
	public static final String BIRTH_DAY = "birthDay";
	public static final String BIRTH_MONTH = "birthMonth";
	public static final String BIRTH_YEAR = "birthYear";
	public static final String GENDER = "gender";
	public static final String ID_NUMBER = "idNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(Member transientInstance) {
		log.debug("saving Member instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Member persistentInstance) {
		log.debug("deleting Member instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Member findById(java.lang.String id) {
		log.debug("getting Member instance with id: " + id);
		try {
			Member instance = (Member) getHibernateTemplate().get(
					"com.drcall.db.dao.Member", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Member> findByExample(Member instance) {
		log.debug("finding Member instance by example");
		try {
			List<Member> results = (List<Member>) getHibernateTemplate()
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
		log.debug("finding Member instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Member as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Member> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<Member> findByPasswd(Object passwd) {
		return findByProperty(PASSWD, passwd);
	}

	public List<Member> findByActive(Object active) {
		return findByProperty(ACTIVE, active);
	}

	public List<Member> findByIsIdentify(Object isIdentify) {
		return findByProperty(IS_IDENTIFY, isIdentify);
	}

	public List<Member> findByIdentifyCode(Object identifyCode) {
		return findByProperty(IDENTIFY_CODE, identifyCode);
	}

	public List<Member> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List<Member> findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List<Member> findByIsRecommend(Object isRecommend) {
		return findByProperty(IS_RECOMMEND, isRecommend);
	}

	public List<Member> findByRecommendId(Object recommendId) {
		return findByProperty(RECOMMEND_ID, recommendId);
	}

	public List<Member> findByBirthDay(Object birthDay) {
		return findByProperty(BIRTH_DAY, birthDay);
	}

	public List<Member> findByBirthMonth(Object birthMonth) {
		return findByProperty(BIRTH_MONTH, birthMonth);
	}

	public List<Member> findByBirthYear(Object birthYear) {
		return findByProperty(BIRTH_YEAR, birthYear);
	}

	public List<Member> findByGender(Object gender) {
		return findByProperty(GENDER, gender);
	}

	public List<Member> findByIdNumber(Object idNumber) {
		return findByProperty(ID_NUMBER, idNumber);
	}

	public List findAll() {
		log.debug("finding all Member instances");
		try {
			String queryString = "from Member";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Member merge(Member detachedInstance) {
		log.debug("merging Member instance");
		try {
			Member result = (Member) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Member instance) {
		log.debug("attaching dirty Member instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Member instance) {
		log.debug("attaching clean Member instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MemberDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MemberDAO) ctx.getBean("MemberDAO");
	}
}