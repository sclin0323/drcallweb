package com.drcall.db.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SystemEmail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYSTEM_EMAIL", catalog = "drcalldb")
public class SystemEmail implements java.io.Serializable {

	// Fields

	private Long systemEmailId;
	private String sendTo;
	private String title;
	private String text;
	private Timestamp crtDate;
	private Integer status;
	private Timestamp sendDate;

	// Constructors

	/** default constructor */
	public SystemEmail() {
	}

	/** minimal constructor */
	public SystemEmail(String sendTo, String title, String text,
			Timestamp crtDate, Integer status) {
		this.sendTo = sendTo;
		this.title = title;
		this.text = text;
		this.crtDate = crtDate;
		this.status = status;
	}

	/** full constructor */
	public SystemEmail(String sendTo, String title, String text,
			Timestamp crtDate, Integer status, Timestamp sendDate) {
		this.sendTo = sendTo;
		this.title = title;
		this.text = text;
		this.crtDate = crtDate;
		this.status = status;
		this.sendDate = sendDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SYSTEM_EMAIL_ID", unique = true, nullable = false)
	public Long getSystemEmailId() {
		return this.systemEmailId;
	}

	public void setSystemEmailId(Long systemEmailId) {
		this.systemEmailId = systemEmailId;
	}

	@Column(name = "SEND_TO", nullable = false, length = 60)
	public String getSendTo() {
		return this.sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	@Column(name = "TITLE", nullable = false, length = 60)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "TEXT", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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

	@Column(name = "SEND_DATE", length = 19)
	public Timestamp getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

}