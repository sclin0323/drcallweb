package com.drcall.client.command;

import java.sql.Timestamp;

import com.drcall.db.dao.Appoint;

public class FreeExperienceCommand {
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
	
	// for appoint
	private Long scheduleId;
	private Integer shift;
	
	
	
	
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Long getFreeExperienceId() {
		return freeExperienceId;
	}
	public void setFreeExperienceId(Long freeExperienceId) {
		this.freeExperienceId = freeExperienceId;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Appoint getAppoint() {
		return appoint;
	}
	public void setAppoint(Appoint appoint) {
		this.appoint = appoint;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Timestamp getCrtDatetime() {
		return crtDatetime;
	}
	public void setCrtDatetime(Timestamp crtDatetime) {
		this.crtDatetime = crtDatetime;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public Integer getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}
	public Integer getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Integer birthDay) {
		this.birthDay = birthDay;
	}
	
	
}
