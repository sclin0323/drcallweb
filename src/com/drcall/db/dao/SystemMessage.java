package com.drcall.db.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYSTEM_MESSAGE", catalog = "drcalldb")
public class SystemMessage implements java.io.Serializable {

	// Fields

	private Long systemMessageId;
	private String mobile;
	private Timestamp crtDate;
	private Integer status;
	private String subject;
	private String content;

	// Constructors

	/** default constructor */
	public SystemMessage() {
	}

	/** full constructor */
	public SystemMessage(String mobile, Timestamp crtDate, Integer status,
			String subject, String content) {
		this.mobile = mobile;
		this.crtDate = crtDate;
		this.status = status;
		this.subject = subject;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SYSTEM_MESSAGE_ID", unique = true, nullable = false)
	public Long getSystemMessageId() {
		return this.systemMessageId;
	}

	public void setSystemMessageId(Long systemMessageId) {
		this.systemMessageId = systemMessageId;
	}

	@Column(name = "MOBILE", nullable = false, length = 10)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CRT_DATE", nullable = false, length = 19)
	public Timestamp getCrtDate() {
		return this.crtDate;
	}

	public void setCrtDate(Timestamp crtDate) {
		this.crtDate = crtDate;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "SUBJECT", nullable = false, length = 40)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name = "CONTENT", nullable = false, length = 100)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}