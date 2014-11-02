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
 * Schedule entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.drcall.db.dao.Schedule
 * @author MyEclipse Persistence Tools
 */

public class ScheduleDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(ScheduleDAO.class);
	// property constants
	public static final String MORNING_SHIFT = "morningShift";
	public static final String AFTERNOON_SHIFT = "afternoonShift";
	public static final String NIGHT_SHIFT = "nightShift";
	public static final String MORNING_SHIFT_CALLINGNO = "morningShiftCallingno";
	public static final String MORNING_SHIFT_WAITINGNUM = "morningShiftWaitingnum";
	public static final String NIGHT_SHIFT_CALLINGNO = "nightShiftCallingno";
	public static final String NIGHT_SHIFT_WAITINGNUM = "nightShiftWaitingnum";
	public static final String AFTERNOON_SHIFT_WAITINGNUM = "afternoonShiftWaitingnum";
	public static final String AFTERNOON_SHIFT_CALLINGNO = "afternoonShiftCallingno";
	public static final String UPDATE_TIME = "updateTime";
	public static final String CN_DATE = "cnDate";
	public static final String AFTERNOON_SHIFT_ROOM = "afternoonShiftRoom";
	public static final String MORNING_SHIFT_ROOM = "morningShiftRoom";
	public static final String NIGHT_SHIFT_ROOM = "nightShiftRoom";

	protected void initDao() {
		// do nothing
	}

	public void save(Schedule transientInstance) {
		log.debug("saving Schedule instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Schedule persistentInstance) {
		log.debug("deleting Schedule instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Schedule findById(java.lang.Long id) {
		log.debug("getting Schedule instance with id: " + id);
		try {
			Schedule instance = (Schedule) getHibernateTemplate().get(
					"com.drcall.db.dao.Schedule", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Schedule> findByExample(Schedule instance) {
		log.debug("finding Schedule instance by example");
		try {
			List<Schedule> results = (List<Schedule>) getHibernateTemplate()
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
		log.debug("finding Schedule instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Schedule as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Schedule> findByMorningShift(Object morningShift) {
		return findByProperty(MORNING_SHIFT, morningShift);
	}

	public List<Schedule> findByAfternoonShift(Object afternoonShift) {
		return findByProperty(AFTERNOON_SHIFT, afternoonShift);
	}

	public List<Schedule> findByNightShift(Object nightShift) {
		return findByProperty(NIGHT_SHIFT, nightShift);
	}

	public List<Schedule> findByMorningShiftCallingno(
			Object morningShiftCallingno) {
		return findByProperty(MORNING_SHIFT_CALLINGNO, morningShiftCallingno);
	}

	public List<Schedule> findByMorningShiftWaitingnum(
			Object morningShiftWaitingnum) {
		return findByProperty(MORNING_SHIFT_WAITINGNUM, morningShiftWaitingnum);
	}

	public List<Schedule> findByNightShiftCallingno(Object nightShiftCallingno) {
		return findByProperty(NIGHT_SHIFT_CALLINGNO, nightShiftCallingno);
	}

	public List<Schedule> findByNightShiftWaitingnum(Object nightShiftWaitingnum) {
		return findByProperty(NIGHT_SHIFT_WAITINGNUM, nightShiftWaitingnum);
	}

	public List<Schedule> findByAfternoonShiftWaitingnum(
			Object afternoonShiftWaitingnum) {
		return findByProperty(AFTERNOON_SHIFT_WAITINGNUM,
				afternoonShiftWaitingnum);
	}

	public List<Schedule> findByAfternoonShiftCallingno(
			Object afternoonShiftCallingno) {
		return findByProperty(AFTERNOON_SHIFT_CALLINGNO,
				afternoonShiftCallingno);
	}

	public List<Schedule> findByUpdateTime(Object updateTime) {
		return findByProperty(UPDATE_TIME, updateTime);
	}

	public List<Schedule> findByCnDate(Object cnDate) {
		return findByProperty(CN_DATE, cnDate);
	}

	public List<Schedule> findByAfternoonShiftRoom(Object afternoonShiftRoom) {
		return findByProperty(AFTERNOON_SHIFT_ROOM, afternoonShiftRoom);
	}

	public List<Schedule> findByMorningShiftRoom(Object morningShiftRoom) {
		return findByProperty(MORNING_SHIFT_ROOM, morningShiftRoom);
	}

	public List<Schedule> findByNightShiftRoom(Object nightShiftRoom) {
		return findByProperty(NIGHT_SHIFT_ROOM, nightShiftRoom);
	}

	public List findAll() {
		log.debug("finding all Schedule instances");
		try {
			String queryString = "from Schedule";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Schedule merge(Schedule detachedInstance) {
		log.debug("merging Schedule instance");
		try {
			Schedule result = (Schedule) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Schedule instance) {
		log.debug("attaching dirty Schedule instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Schedule instance) {
		log.debug("attaching clean Schedule instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ScheduleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ScheduleDAO) ctx.getBean("ScheduleDAO");
	}
}