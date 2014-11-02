package com.drcall.client.command;

import java.sql.Timestamp;
import java.util.Date;

import com.drcall.db.dao.Member;
import com.drcall.db.dao.Schedule;

public class AppointCommand extends BaseCommand{
	private Long appointId;
	private Member member;
	private Schedule schedule;
	private String name;
	private String tel;
	private Integer status;
	private Integer shift;
	private Boolean notifyTake;
	private Timestamp crtTime;
	
	// for submit appointment
	private String idNumber;
	private Long familyMemberId;
	private Long scheduleId;
	private boolean isKeepId;
	private String selectPatient;
	
	
	public String getSelectPatient() {
		return selectPatient;
	}
	public void setSelectPatient(String selectPatient) {
		this.selectPatient = selectPatient;
	}
	public void setKeepId(boolean isKeepId) {
		this.isKeepId = isKeepId;
	}
	public boolean isKeepId() {
		return isKeepId;
	}
	public void setIsKeepId(boolean isKeepId) {
		this.isKeepId = isKeepId;
	}
	
	
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Long getFamilyMemberId() {
		return familyMemberId;
	}
	public void setFamilyMemberId(Long familyMemberId) {
		this.familyMemberId = familyMemberId;
	}
	
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public Long getAppointId() {
		return appointId;
	}
	public void setAppointId(Long appointId) {
		this.appointId = appointId;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getShift() {
		return shift;
	}
	public void setShift(Integer shift) {
		this.shift = shift;
	}
	public Boolean getNotifyTake() {
		return notifyTake;
	}
	public void setNotifyTake(Boolean notifyTake) {
		this.notifyTake = notifyTake;
	}
	public Timestamp getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Timestamp crtTime) {
		this.crtTime = crtTime;
	}

	
	
	
	
}
