package com.drcall.db.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Division entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DIVISION", catalog = "drcalldb")
public class Division implements java.io.Serializable {

	// Fields

	private String divisionId;
	private String cnName;
	private String enName;
	private String note;
	private Boolean active;
	private Set<Schedule> schedules = new HashSet<Schedule>(0);
	private Set<Doctor> doctors = new HashSet<Doctor>(0);
	private Set<Hospital> hospitals = new HashSet<Hospital>(0);

	// Constructors

	/** default constructor */
	public Division() {
	}

	/** minimal constructor */
	public Division(String divisionId, String cnName, Boolean active) {
		this.divisionId = divisionId;
		this.cnName = cnName;
		this.active = active;
	}

	/** full constructor */
	public Division(String divisionId, String cnName, String enName,
			String note, Boolean active, Set<Schedule> schedules,
			Set<Doctor> doctors, Set<Hospital> hospitals) {
		this.divisionId = divisionId;
		this.cnName = cnName;
		this.enName = enName;
		this.note = note;
		this.active = active;
		this.schedules = schedules;
		this.doctors = doctors;
		this.hospitals = hospitals;
	}

	// Property accessors
	@Id
	@Column(name = "DIVISION_ID", unique = true, nullable = false, length = 5)
	public String getDivisionId() {
		return this.divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	@Column(name = "CN_NAME", nullable = false, length = 40)
	public String getCnName() {
		return this.cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	@Column(name = "EN_NAME", length = 40)
	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Column(name = "NOTE", length = 80)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "ACTIVE", nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "division")
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "division")
	public Set<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "divisions")
	public Set<Hospital> getHospitals() {
		return this.hospitals;
	}

	public void setHospitals(Set<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

}