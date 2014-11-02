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
 * Consume entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CONSUME", catalog = "drcalldb")
public class Consume implements java.io.Serializable {

	// Fields

	private Long consumeId;
	private Member member;
	private Timestamp crtDatetime;
	private boolean status;
	private Integer sum;
	private Integer point;
	private Integer type;

	// Constructors

	/** default constructor */
	public Consume() {
	}

	/** minimal constructor */
	public Consume(Timestamp crtDatetime, boolean status, Integer sum,
			Integer point, Integer type) {
		this.crtDatetime = crtDatetime;
		this.status = status;
		this.sum = sum;
		this.point = point;
		this.type = type;
	}

	/** full constructor */
	public Consume(Member member, Timestamp crtDatetime, boolean status,
			Integer sum, Integer point, Integer type) {
		this.member = member;
		this.crtDatetime = crtDatetime;
		this.status = status;
		this.sum = sum;
		this.point = point;
		this.type = type;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CONSUME_ID", unique = true, nullable = false)
	public Long getConsumeId() {
		return this.consumeId;
	}

	public void setConsumeId(Long consumeId) {
		this.consumeId = consumeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "CRT_DATETIME", nullable = false, length = 19)
	public Timestamp getCrtDatetime() {
		return this.crtDatetime;
	}

	public void setCrtDatetime(Timestamp crtDatetime) {
		this.crtDatetime = crtDatetime;
	}

	@Column(name = "STATUS", nullable = false)
	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "SUM", nullable = false)
	public Integer getSum() {
		return this.sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	@Column(name = "POINT", nullable = false)
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}