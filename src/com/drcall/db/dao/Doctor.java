package com.drcall.db.dao;

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
 * Doctor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCTOR", catalog = "drcalldb")
public class Doctor implements java.io.Serializable {

	// Fields

	private Integer doctorId;
	private Hospital hospital;
	private Division division;
	private String name;
	private String identityCode;
	private Set<Schedule> schedules = new HashSet<Schedule>(0);

	// Constructors

	/** default constructor */
	public Doctor() {
	}

	/** minimal constructor */
	public Doctor(Hospital hospital, Division division, String name,
			String identityCode) {
		this.hospital = hospital;
		this.division = division;
		this.name = name;
		this.identityCode = identityCode;
	}

	/** full constructor */
	public Doctor(Hospital hospital, Division division, String name,
			String identityCode, Set<Schedule> schedules) {
		this.hospital = hospital;
		this.division = division;
		this.name = name;
		this.identityCode = identityCode;
		this.schedules = schedules;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DOCTOR_ID", unique = true, nullable = false)
	public Integer getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
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

	@Column(name = "NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "IDENTITY_CODE", nullable = false, length = 10)
	public String getIdentityCode() {
		return this.identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctor")
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

}