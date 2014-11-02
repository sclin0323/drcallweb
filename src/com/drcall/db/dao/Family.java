package com.drcall.db.dao;

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
 * Family entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "FAMILY", catalog = "drcalldb")
public class Family implements java.io.Serializable {

	// Fields

	private Long familyId;
	private Member member;
	private String name;
	private String tel;
	private Boolean active;
	private Integer birthDay;
	private Integer birthMonth;
	private Integer birthYear;
	private Integer gender;
	private String idNumber;

	// Constructors

	/** default constructor */
	public Family() {
	}

	/** minimal constructor */
	public Family(Member member, String name, String tel, Boolean active,
			Integer birthDay, Integer birthMonth, Integer birthYear,
			Integer gender) {
		this.member = member;
		this.name = name;
		this.tel = tel;
		this.active = active;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.gender = gender;
	}

	/** full constructor */
	public Family(Member member, String name, String tel, Boolean active,
			Integer birthDay, Integer birthMonth, Integer birthYear,
			Integer gender, String idNumber) {
		this.member = member;
		this.name = name;
		this.tel = tel;
		this.active = active;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.gender = gender;
		this.idNumber = idNumber;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FAMILY_ID", unique = true, nullable = false)
	public Long getFamilyId() {
		return this.familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	@Column(name = "ACTIVE", nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "BIRTH_DAY", nullable = false)
	public Integer getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "BIRTH_MONTH", nullable = false)
	public Integer getBirthMonth() {
		return this.birthMonth;
	}

	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}

	@Column(name = "BIRTH_YEAR", nullable = false)
	public Integer getBirthYear() {
		return this.birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Column(name = "GENDER", nullable = false)
	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Column(name = "ID_NUMBER", length = 10)
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}