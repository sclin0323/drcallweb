package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Recommend entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "RECOMMEND", catalog = "drcalldb")
public class Recommend implements java.io.Serializable {

	// Fields

	private String recommendId;
	private Member member;
	private String email;
	private Timestamp recommendDate;
	private Date deadline;
	private Boolean active;
	private Integer status;
	private String name;
	private String tel;

	// Constructors

	/** default constructor */
	public Recommend() {
	}

	/** minimal constructor */
	public Recommend(String recommendId, Member member, String email,
			Timestamp recommendDate, Date deadline, Boolean active,
			Integer status) {
		this.recommendId = recommendId;
		this.member = member;
		this.email = email;
		this.recommendDate = recommendDate;
		this.deadline = deadline;
		this.active = active;
		this.status = status;
	}

	/** full constructor */
	public Recommend(String recommendId, Member member, String email,
			Timestamp recommendDate, Date deadline, Boolean active,
			Integer status, String name, String tel) {
		this.recommendId = recommendId;
		this.member = member;
		this.email = email;
		this.recommendDate = recommendDate;
		this.deadline = deadline;
		this.active = active;
		this.status = status;
		this.name = name;
		this.tel = tel;
	}

	// Property accessors
	@Id
	@Column(name = "RECOMMEND_ID", unique = true, nullable = false, length = 15)
	public String getRecommendId() {
		return this.recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "EMAIL", nullable = false, length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "RECOMMEND_DATE", nullable = false, length = 19)
	public Timestamp getRecommendDate() {
		return this.recommendDate;
	}

	public void setRecommendDate(Timestamp recommendDate) {
		this.recommendDate = recommendDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEADLINE", nullable = false, length = 10)
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Column(name = "ACTIVE", nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TEL", length = 25)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}