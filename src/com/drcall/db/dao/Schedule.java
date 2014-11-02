package com.drcall.db.dao;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Schedule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SCHEDULE", catalog = "drcalldb")
public class Schedule implements java.io.Serializable {

	// Fields

	private Long scheduleId;
	private Hospital hospital;
	private Division division;
	private Doctor doctor;
	private Date date;
	private Boolean morningShift;
	private Boolean afternoonShift;
	private Boolean nightShift;
	private Integer morningShiftCallingno;
	private Integer morningShiftWaitingnum;
	private Integer nightShiftCallingno;
	private Integer nightShiftWaitingnum;
	private Integer afternoonShiftWaitingnum;
	private Integer afternoonShiftCallingno;
	private String updateTime;
	private String cnDate;
	private Integer afternoonShiftRoom;
	private Integer morningShiftRoom;
	private Integer nightShiftRoom;
	private Set<Appoint> appoints = new HashSet<Appoint>(0);

	// Constructors

	/** default constructor */
	public Schedule() {
	}

	/** minimal constructor */
	public Schedule(Hospital hospital, Division division, Doctor doctor,
			Date date, Boolean morningShift, Boolean afternoonShift,
			Boolean nightShift, Integer morningShiftCallingno,
			Integer morningShiftWaitingnum, Integer nightShiftCallingno,
			Integer nightShiftWaitingnum, Integer afternoonShiftWaitingnum,
			Integer afternoonShiftCallingno, String updateTime) {
		this.hospital = hospital;
		this.division = division;
		this.doctor = doctor;
		this.date = date;
		this.morningShift = morningShift;
		this.afternoonShift = afternoonShift;
		this.nightShift = nightShift;
		this.morningShiftCallingno = morningShiftCallingno;
		this.morningShiftWaitingnum = morningShiftWaitingnum;
		this.nightShiftCallingno = nightShiftCallingno;
		this.nightShiftWaitingnum = nightShiftWaitingnum;
		this.afternoonShiftWaitingnum = afternoonShiftWaitingnum;
		this.afternoonShiftCallingno = afternoonShiftCallingno;
		this.updateTime = updateTime;
	}

	/** full constructor */
	public Schedule(Hospital hospital, Division division, Doctor doctor,
			Date date, Boolean morningShift, Boolean afternoonShift,
			Boolean nightShift, Integer morningShiftCallingno,
			Integer morningShiftWaitingnum, Integer nightShiftCallingno,
			Integer nightShiftWaitingnum, Integer afternoonShiftWaitingnum,
			Integer afternoonShiftCallingno, String updateTime, String cnDate,
			Integer afternoonShiftRoom, Integer morningShiftRoom,
			Integer nightShiftRoom, Set<Appoint> appoints) {
		this.hospital = hospital;
		this.division = division;
		this.doctor = doctor;
		this.date = date;
		this.morningShift = morningShift;
		this.afternoonShift = afternoonShift;
		this.nightShift = nightShift;
		this.morningShiftCallingno = morningShiftCallingno;
		this.morningShiftWaitingnum = morningShiftWaitingnum;
		this.nightShiftCallingno = nightShiftCallingno;
		this.nightShiftWaitingnum = nightShiftWaitingnum;
		this.afternoonShiftWaitingnum = afternoonShiftWaitingnum;
		this.afternoonShiftCallingno = afternoonShiftCallingno;
		this.updateTime = updateTime;
		this.cnDate = cnDate;
		this.afternoonShiftRoom = afternoonShiftRoom;
		this.morningShiftRoom = morningShiftRoom;
		this.nightShiftRoom = nightShiftRoom;
		this.appoints = appoints;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SCHEDULE_ID", unique = true, nullable = false)
	public Long getScheduleId() {
		return this.scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITAL_ID", nullable = false)
	public Hospital getHospital() {
		return this.hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIVISION_ID", nullable = false)
	public Division getDivision() {
		return this.division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOCTOR_ID", nullable = false)
	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE", nullable = false, length = 10)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "MORNING_SHIFT", nullable = false)
	public Boolean getMorningShift() {
		return this.morningShift;
	}

	public void setMorningShift(Boolean morningShift) {
		this.morningShift = morningShift;
	}

	@Column(name = "AFTERNOON_SHIFT", nullable = false)
	public Boolean getAfternoonShift() {
		return this.afternoonShift;
	}

	public void setAfternoonShift(Boolean afternoonShift) {
		this.afternoonShift = afternoonShift;
	}

	@Column(name = "NIGHT_SHIFT", nullable = false)
	public Boolean getNightShift() {
		return this.nightShift;
	}

	public void setNightShift(Boolean nightShift) {
		this.nightShift = nightShift;
	}

	@Column(name = "MORNING_SHIFT_CALLINGNO", nullable = false)
	public Integer getMorningShiftCallingno() {
		return this.morningShiftCallingno;
	}

	public void setMorningShiftCallingno(Integer morningShiftCallingno) {
		this.morningShiftCallingno = morningShiftCallingno;
	}

	@Column(name = "MORNING_SHIFT_WAITINGNUM", nullable = false)
	public Integer getMorningShiftWaitingnum() {
		return this.morningShiftWaitingnum;
	}

	public void setMorningShiftWaitingnum(Integer morningShiftWaitingnum) {
		this.morningShiftWaitingnum = morningShiftWaitingnum;
	}

	@Column(name = "NIGHT_SHIFT_CALLINGNO", nullable = false)
	public Integer getNightShiftCallingno() {
		return this.nightShiftCallingno;
	}

	public void setNightShiftCallingno(Integer nightShiftCallingno) {
		this.nightShiftCallingno = nightShiftCallingno;
	}

	@Column(name = "NIGHT_SHIFT_WAITINGNUM", nullable = false)
	public Integer getNightShiftWaitingnum() {
		return this.nightShiftWaitingnum;
	}

	public void setNightShiftWaitingnum(Integer nightShiftWaitingnum) {
		this.nightShiftWaitingnum = nightShiftWaitingnum;
	}

	@Column(name = "AFTERNOON_SHIFT_WAITINGNUM", nullable = false)
	public Integer getAfternoonShiftWaitingnum() {
		return this.afternoonShiftWaitingnum;
	}

	public void setAfternoonShiftWaitingnum(Integer afternoonShiftWaitingnum) {
		this.afternoonShiftWaitingnum = afternoonShiftWaitingnum;
	}

	@Column(name = "AFTERNOON_SHIFT_CALLINGNO", nullable = false)
	public Integer getAfternoonShiftCallingno() {
		return this.afternoonShiftCallingno;
	}

	public void setAfternoonShiftCallingno(Integer afternoonShiftCallingno) {
		this.afternoonShiftCallingno = afternoonShiftCallingno;
	}

	@Column(name = "UPDATE_TIME", nullable = false, length = 20)
	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "CN_DATE", length = 7)
	public String getCnDate() {
		return this.cnDate;
	}

	public void setCnDate(String cnDate) {
		this.cnDate = cnDate;
	}

	@Column(name = "AFTERNOON_SHIFT_ROOM")
	public Integer getAfternoonShiftRoom() {
		return this.afternoonShiftRoom;
	}

	public void setAfternoonShiftRoom(Integer afternoonShiftRoom) {
		this.afternoonShiftRoom = afternoonShiftRoom;
	}

	@Column(name = "MORNING_SHIFT_ROOM")
	public Integer getMorningShiftRoom() {
		return this.morningShiftRoom;
	}

	public void setMorningShiftRoom(Integer morningShiftRoom) {
		this.morningShiftRoom = morningShiftRoom;
	}

	@Column(name = "NIGHT_SHIFT_ROOM")
	public Integer getNightShiftRoom() {
		return this.nightShiftRoom;
	}

	public void setNightShiftRoom(Integer nightShiftRoom) {
		this.nightShiftRoom = nightShiftRoom;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "schedule")
	public Set<Appoint> getAppoints() {
		return this.appoints;
	}

	public void setAppoints(Set<Appoint> appoints) {
		this.appoints = appoints;
	}

}