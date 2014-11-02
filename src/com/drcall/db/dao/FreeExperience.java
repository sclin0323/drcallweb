package com.drcall.db.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FreeExperience entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FREE_EXPERIENCE", catalog = "drcalldb")
public class FreeExperience implements java.io.Serializable {

	// Fields

	private Long freeExperienceId;
	private Appoint appoint;
	private String checkNumber;
	private Integer status;
	private String tel;
	private Timestamp crtDatetime;
	private String identityId;
	private String name;
	private Integer birthYear;
	private Integer birthMonth;
	private Integer birthDay;
	private String address;

	// Constructors

	/** default constructor */
	public FreeExperience() {
	}

	/** minimal constructor */
	public FreeExperience(String checkNumber, Integer status, String tel,
			Timestamp crtDatetime) {
		this.checkNumber = checkNumber;
		this.status = status;
		this.tel = tel;
		this.crtDatetime = crtDatetime;
	}

	/** full constructor */
	public FreeExperience(Appoint appoint, String checkNumber, Integer status,
			String tel, Timestamp crtDatetime, String identityId, String name,
			Integer birthYear, Integer birthMonth, Integer birthDay,
			String address) {
		this.appoint = appoint;
		this.checkNumber = checkNumber;
		this.status = status;
		this.tel = tel;
		this.crtDatetime = crtDatetime;
		this.identityId = identityId;
		this.name = name;
		this.birthYear = birthYear;
		this.birthMonth = birthMonth;
		this.birthDay = birthDay;
		this.address = address;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FREE_EXPERIENCE_ID", unique = true, nullable = false)
	public Long getFreeExperienceId() {
		return this.freeExperienceId;
	}

	public void setFreeExperienceId(Long freeExperienceId) {
		this.freeExperienceId = freeExperienceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPOINT_ID")
	public Appoint getAppoint() {
		return this.appoint;
	}

	public void setAppoint(Appoint appoint) {
		this.appoint = appoint;
	}

	@Column(name = "CHECK_NUMBER", nullable = false, length = 15)
	public String getCheckNumber() {
		return this.checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "TEL", nullable = false, length = 10)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "CRT_DATETIME", nullable = false, length = 19)
	public Timestamp getCrtDatetime() {
		return this.crtDatetime;
	}

	public void setCrtDatetime(Timestamp crtDatetime) {
		this.crtDatetime = crtDatetime;
	}

	@Column(name = "IDENTITY_ID", length = 10)
	public String getIdentityId() {
		return this.identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	@Column(name = "NAME", length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BIRTH_YEAR")
	public Integer getBirthYear() {
		return this.birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Column(name = "BIRTH_MONTH")
	public Integer getBirthMonth() {
		return this.birthMonth;
	}

	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}

	@Column(name = "BIRTH_DAY")
	public Integer getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "ADDRESS", length = 80)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}