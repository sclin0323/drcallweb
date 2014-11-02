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
 * Notify entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "NOTIFY", catalog = "drcalldb")
public class Notify implements java.io.Serializable {

	// Fields

	private Long nofityId;
	private Appoint appoint;
	private Timestamp time;
	private Integer type;
	private String content;

	// Constructors

	/** default constructor */
	public Notify() {
	}

	/** minimal constructor */
	public Notify(Appoint appoint, Timestamp time, Integer type) {
		this.appoint = appoint;
		this.time = time;
		this.type = type;
	}

	/** full constructor */
	public Notify(Appoint appoint, Timestamp time, Integer type, String content) {
		this.appoint = appoint;
		this.time = time;
		this.type = type;
		this.content = content;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NOFITY_ID", unique = true, nullable = false)
	public Long getNofityId() {
		return this.nofityId;
	}

	public void setNofityId(Long nofityId) {
		this.nofityId = nofityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPOINT_ID", nullable = false)
	public Appoint getAppoint() {
		return this.appoint;
	}

	public void setAppoint(Appoint appoint) {
		this.appoint = appoint;
	}

	@Column(name = "TIME", nullable = false, length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "CONTENT", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}