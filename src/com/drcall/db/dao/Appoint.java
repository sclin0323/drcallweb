package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Appoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APPOINT", catalog = "drcalldb")
public class Appoint implements java.io.Serializable {

	// Fields

	private Long appointId;
	private Member member;
	private Schedule schedule;
	private String name;
	private String tel;
	private Integer status;
	private Integer shift;
	private Boolean notifyTake;
	private Timestamp crtTime;
	private Integer type;
	private Integer appNumber;
	private Set<Notify> notifies = new HashSet<Notify>(0);

	// Constructors

	/** default constructor */
	public Appoint() {
	}

	/** minimal constructor */
	public Appoint(Schedule schedule, String name, String tel, Integer status,
			Integer shift, Boolean notifyTake, Timestamp crtTime, Integer type,
			Integer appNumber) {
		this.schedule = schedule;
		this.name = name;
		this.tel = tel;
		this.status = status;
		this.shift = shift;
		this.notifyTake = notifyTake;
		this.crtTime = crtTime;
		this.type = type;
		this.appNumber = appNumber;
	}

	/** full constructor */
	public Appoint(Member member, Schedule schedule, String name, String tel,
			Integer status, Integer shift, Boolean notifyTake,
			Timestamp crtTime, Integer type, Integer appNumber,
			Set<Notify> notifies) {
		this.member = member;
		this.schedule = schedule;
		this.name = name;
		this.tel = tel;
		this.status = status;
		this.shift = shift;
		this.notifyTake = notifyTake;
		this.crtTime = crtTime;
		this.type = type;
		this.appNumber = appNumber;
		this.notifies = notifies;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "APPOINT_ID", unique = true, nullable = false)
	public Long getAppointId() {
		return this.appointId;
	}

	public void setAppointId(Long appointId) {
		this.appointId = appointId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHEDULE_ID", nullable = false)
	public Schedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@Column(name = "NAME", nullable = false, length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TEL", nullable = false, length = 10)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "SHIFT", nullable = false)
	public Integer getShift() {
		return this.shift;
	}

	public void setShift(Integer shift) {
		this.shift = shift;
	}

	@Column(name = "NOTIFY_TAKE", nullable = false)
	public Boolean getNotifyTake() {
		return this.notifyTake;
	}

	public void setNotifyTake(Boolean notifyTake) {
		this.notifyTake = notifyTake;
	}

	@Column(name = "CRT_TIME", nullable = false, length = 19)
	public Timestamp getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(Timestamp crtTime) {
		this.crtTime = crtTime;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "APP_NUMBER", nullable = false)
	public Integer getAppNumber() {
		return this.appNumber;
	}

	public void setAppNumber(Integer appNumber) {
		this.appNumber = appNumber;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appoint")
	public Set<Notify> getNotifies() {
		return this.notifies;
	}

	public void setNotifies(Set<Notify> notifies) {
		this.notifies = notifies;
	}

}