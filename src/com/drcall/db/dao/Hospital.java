package com.drcall.db.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Hospital entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "HOSPITAL", catalog = "drcalldb")
public class Hospital implements java.io.Serializable {

	// Fields

	private String hospitalId;
	private String name;
	private String address;
	private String phone;
	private Boolean active;
	private String city;
	private Boolean isContract;
	private Date contractDate;
	private String message;
	private Set<Doctor> doctors = new HashSet<Doctor>(0);
	private Set<Schedule> schedules = new HashSet<Schedule>(0);
	private Set<Division> divisions = new HashSet<Division>(0);
	private Set<Usr> usrs = new HashSet<Usr>(0);

	// Constructors

	/** default constructor */
	public Hospital() {
	}

	/** minimal constructor */
	public Hospital(String hospitalId, String name, Boolean active,
			Boolean isContract) {
		this.hospitalId = hospitalId;
		this.name = name;
		this.active = active;
		this.isContract = isContract;
	}

	/** full constructor */
	public Hospital(String hospitalId, String name, String address,
			String phone, Boolean active, String city, Boolean isContract,
			Date contractDate, String message, Set<Doctor> doctors,
			Set<Schedule> schedules, Set<Division> divisions, Set<Usr> usrs) {
		this.hospitalId = hospitalId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.active = active;
		this.city = city;
		this.isContract = isContract;
		this.contractDate = contractDate;
		this.message = message;
		this.doctors = doctors;
		this.schedules = schedules;
		this.divisions = divisions;
		this.usrs = usrs;
	}

	// Property accessors
	@Id
	@Column(name = "HOSPITAL_ID", unique = true, nullable = false, length = 10)
	public String getHospitalId() {
		return this.hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Column(name = "NAME", nullable = false, length = 80)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "PHONE", length = 15)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "ACTIVE", nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "CITY", length = 10)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "IS_CONTRACT", nullable = false)
	public Boolean getIsContract() {
		return this.isContract;
	}

	public void setIsContract(Boolean isContract) {
		this.isContract = isContract;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTRACT_DATE", length = 10)
	public Date getContractDate() {
		return this.contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Column(name = "MESSAGE", length = 65535)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospital")
	public Set<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospital")
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}

	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "HOSPITAL_DIVISION", catalog = "drcalldb", joinColumns = { @JoinColumn(name = "HOSPITAL_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "DIVISION_ID", nullable = false, updatable = false) })
	public Set<Division> getDivisions() {
		return this.divisions;
	}

	public void setDivisions(Set<Division> divisions) {
		this.divisions = divisions;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hospitals")
	public Set<Usr> getUsrs() {
		return this.usrs;
	}

	public void setUsrs(Set<Usr> usrs) {
		this.usrs = usrs;
	}

}