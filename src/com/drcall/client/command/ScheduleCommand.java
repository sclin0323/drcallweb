package com.drcall.client.command;

import java.util.Date;

import com.drcall.db.dao.Division;
import com.drcall.db.dao.Doctor;
import com.drcall.db.dao.Hospital;


public class ScheduleCommand {
	private Long scheduleId;
    private Hospital hospital;
    private Division division;
    private Doctor doctor;
    private String crtDate;
    private String crtUsrId;
    private Date date;
    private Boolean enable;
    private Boolean morningShift;
    private Boolean aftermoomShift;
    private Boolean nightShift;
	
	// key
	private String hospitalId;
	private String divisionId;
	private Integer selectShift;
	
	
	public Integer getSelectShift() {
		return selectShift;
	}
	public void setSelectShift(Integer selectShift) {
		this.selectShift = selectShift;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getCrtDate() {
		return crtDate;
	}
	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	}
	public String getCrtUsrId() {
		return crtUsrId;
	}
	public void setCrtUsrId(String crtUsrId) {
		this.crtUsrId = crtUsrId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Boolean getMorningShift() {
		return morningShift;
	}
	public void setMorningShift(Boolean morningShift) {
		this.morningShift = morningShift;
	}
	public Boolean getAftermoomShift() {
		return aftermoomShift;
	}
	public void setAftermoomShift(Boolean aftermoomShift) {
		this.aftermoomShift = aftermoomShift;
	}
	public Boolean getNightShift() {
		return nightShift;
	}
	public void setNightShift(Boolean nightShift) {
		this.nightShift = nightShift;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	
	
	
	
	
	
}
