package com.drcall.client.command;

public class AutoCompletedCommand {
	private String hospitalId;
	private String value;

	public AutoCompletedCommand(String id, String value) {
		this.hospitalId = id;
		this.value = value;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
