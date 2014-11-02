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
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MESSAGE", catalog = "drcalldb")
public class Message implements java.io.Serializable {

	// Fields

	private Long messageId;
	private Member member;
	private String content;
	private String tel;
	private String email;
	private Integer status;
	private Boolean isResponse;
	private String responseContent;
	private Timestamp responseDate;
	private String name;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(String content, Integer status) {
		this.content = content;
		this.status = status;
	}

	/** full constructor */
	public Message(Member member, String content, String tel, String email,
			Integer status, Boolean isResponse, String responseContent,
			Timestamp responseDate, String name) {
		this.member = member;
		this.content = content;
		this.tel = tel;
		this.email = email;
		this.status = status;
		this.isResponse = isResponse;
		this.responseContent = responseContent;
		this.responseDate = responseDate;
		this.name = name;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MESSAGE_ID", unique = true, nullable = false)
	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "CONTENT", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "TEL", length = 10)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "IS_RESPONSE")
	public Boolean getIsResponse() {
		return this.isResponse;
	}

	public void setIsResponse(Boolean isResponse) {
		this.isResponse = isResponse;
	}

	@Column(name = "RESPONSE_CONTENT", length = 65535)
	public String getResponseContent() {
		return this.responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	@Column(name = "RESPONSE_DATE", length = 19)
	public Timestamp getResponseDate() {
		return this.responseDate;
	}

	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}