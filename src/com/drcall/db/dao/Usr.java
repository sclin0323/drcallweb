package com.drcall.db.dao;

import java.sql.Timestamp;
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

/**
 * Usr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "USR", catalog = "drcalldb")
public class Usr implements java.io.Serializable {

	// Fields

	private String usrId;
	private String passwd;
	private String name;
	private Timestamp crtDate;
	private String note;
	private Boolean active;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<Question> questions = new HashSet<Question>(0);
	private Set<Hospital> hospitals = new HashSet<Hospital>(0);

	// Constructors

	/** default constructor */
	public Usr() {
	}

	/** minimal constructor */
	public Usr(String usrId) {
		this.usrId = usrId;
	}

	/** full constructor */
	public Usr(String usrId, String passwd, String name, Timestamp crtDate,
			String note, Boolean active, Set<Role> roles,
			Set<Question> questions, Set<Hospital> hospitals) {
		this.usrId = usrId;
		this.passwd = passwd;
		this.name = name;
		this.crtDate = crtDate;
		this.note = note;
		this.active = active;
		this.roles = roles;
		this.questions = questions;
		this.hospitals = hospitals;
	}

	// Property accessors
	@Id
	@Column(name = "USR_ID", unique = true, nullable = false, length = 40)
	public String getUsrId() {
		return this.usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	@Column(name = "PASSWD", length = 40)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "NAME", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CRT_DATE", length = 19)
	public Timestamp getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Timestamp crtDate) {
		this.crtDate = crtDate;
	}

	@Column(name = "NOTE", length = 60)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "ACTIVE")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "USR_ROLE", catalog = "drcalldb", joinColumns = { @JoinColumn(name = "USR_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usr")
	public Set<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "HOSPITAL_USR", catalog = "drcalldb", joinColumns = { @JoinColumn(name = "USR_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "HOSPITAL_ID", nullable = false, updatable = false) })
	public Set<Hospital> getHospitals() {
		return this.hospitals;
	}

	public void setHospitals(Set<Hospital> hospitals) {
		this.hospitals = hospitals;
	}

}