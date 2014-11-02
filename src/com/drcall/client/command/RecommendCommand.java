package com.drcall.client.command;

import java.sql.Timestamp;
import java.util.Date;

import com.drcall.db.dao.Member;


public class RecommendCommand {
	private String recommendId;
	private Member member;
	private String email;
	private Timestamp recommendDate;
	private Date deadline;
	private Boolean active;
	private Integer status;
	private String name;
	private String tel;
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(Timestamp recommendDate) {
		this.recommendDate = recommendDate;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
