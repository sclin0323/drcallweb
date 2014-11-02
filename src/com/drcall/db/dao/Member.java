package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * Member entity. @author MyEclipse Persistence Tools
 * 
 * Update By Sam in 2014-07-10 for getAppoints Sort
 */
@Entity
@Table(name = "MEMBER", catalog = "drcalldb")
public class Member implements java.io.Serializable {

	// Fields

	private String memberId;
	private String name;
	private String passwd;
	private Boolean active;
	private Boolean isIdentify;
	private String identifyCode;
	private String email;
	private String address;
	private Boolean isRecommend;
	private String recommendId;
	private Timestamp crtDate;
	private Integer birthDay;
	private Integer birthMonth;
	private Integer birthYear;
	private Integer gender;
	private String idNumber;
	private Set<Account> accounts = new HashSet<Account>(0);
	private Set<Recommend> recommends = new HashSet<Recommend>(0);
	private Set<Appoint> appoints = new HashSet<Appoint>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<Family> families = new HashSet<Family>(0);
	private Set<Consume> consumes = new HashSet<Consume>(0);

	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(String memberId, Boolean isIdentify, String identifyCode,
			Timestamp crtDate) {
		this.memberId = memberId;
		this.isIdentify = isIdentify;
		this.identifyCode = identifyCode;
		this.crtDate = crtDate;
	}

	/** full constructor */
	public Member(String memberId, String name, String passwd, Boolean active,
			Boolean isIdentify, String identifyCode, String email,
			String address, Boolean isRecommend, String recommendId,
			Timestamp crtDate, Integer birthDay, Integer birthMonth,
			Integer birthYear, Integer gender,
			String idNumber, Set<Account> accounts,
			Set<Recommend> recommends, Set<Appoint> appoints,
			Set<Message> messages, Set<Family> families, Set<Consume> consumes) {
		this.memberId = memberId;
		this.name = name;
		this.passwd = passwd;
		this.active = active;
		this.isIdentify = isIdentify;
		this.identifyCode = identifyCode;
		this.email = email;
		this.address = address;
		this.isRecommend = isRecommend;
		this.recommendId = recommendId;
		this.crtDate = crtDate;
		this.birthDay = birthDay;
		this.birthMonth = birthMonth;
		this.birthYear = birthYear;
		this.gender = gender;
		this.idNumber = idNumber;
		this.accounts = accounts;
		this.recommends = recommends;
		this.appoints = appoints;
		this.messages = messages;
		this.families = families;
		this.consumes = consumes;
	}

	// Property accessors
	@Id
	@Column(name = "MEMBER_ID", unique = true, nullable = false, length = 10)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWD", length = 20)
	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(name = "ACTIVE")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "IS_IDENTIFY", nullable = false)
	public Boolean getIsIdentify() {
		return this.isIdentify;
	}

	public void setIsIdentify(Boolean isIdentify) {
		this.isIdentify = isIdentify;
	}

	@Column(name = "IDENTIFY_CODE", nullable = false, length = 20)
	public String getIdentifyCode() {
		return this.identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS", length = 60)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "IS_RECOMMEND")
	public Boolean getIsRecommend() {
		return this.isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	@Column(name = "RECOMMEND_ID", length = 15)
	public String getRecommendId() {
		return this.recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	@Column(name = "CRT_DATE", nullable = false, length = 19)
	public Timestamp getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Timestamp crtDate) {
		this.crtDate = crtDate;
	}

	@Column(name = "BIRTH_DAY")
	public Integer getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}

	@Column(name = "BIRTH_MONTH")
	public Integer getBirthMonth() {
		return this.birthMonth;
	}

	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}

	@Column(name = "BIRTH_YEAR")
	public Integer getBirthYear() {
		return this.birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Column(name = "GENDER")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	@OrderBy("datetime")
	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	public Set<Recommend> getRecommends() {
		return this.recommends;
	}

	public void setRecommends(Set<Recommend> recommends) {
		this.recommends = recommends;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	@OrderBy("crtTime DESC")
	public Set<Appoint> getAppoints() {
		return this.appoints;
	}

	public void setAppoints(Set<Appoint> appoints) {
		this.appoints = appoints;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	public Set<Family> getFamilies() {
		return this.families;
	}

	public void setFamilies(Set<Family> families) {
		this.families = families;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
	public Set<Consume> getConsumes() {
		return this.consumes;
	}

	public void setConsumes(Set<Consume> consumes) {
		this.consumes = consumes;
	}

}