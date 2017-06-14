package com.example.cargas.model;

import java.io.Serializable;

public class SamplingCreateQueryResponse implements Serializable{

	private String SamplingCreateID;
	private String SamplingName;
	private String SamplingAddress;
	private String ChargePerson;
	private String SamplingPerson;
	private String StartTime;
	private String EndTime;
	private String RegisterUser;
	private String Remark;

	public SamplingCreateQueryResponse(String samplingCreateID,
			String samplingName, String samplingAddress, String chargePerson,
			String samplingPerson, String startTime, String endTime,
			String registerUser, String remark) {
		super();
		SamplingCreateID = samplingCreateID;
		SamplingName = samplingName;
		SamplingAddress = samplingAddress;
		ChargePerson = chargePerson;
		SamplingPerson = samplingPerson;
		StartTime = startTime;
		EndTime = endTime;
		RegisterUser = registerUser;
		Remark = remark;
	}

	public String getSamplingCreateID() {
		return SamplingCreateID;
	}

	public void setSamplingCreateID(String samplingCreateID) {
		SamplingCreateID = samplingCreateID;
	}

	public String getSamplingName() {
		return SamplingName;
	}

	public void setSamplingName(String samplingName) {
		SamplingName = samplingName;
	}

	public String getSamplingAddress() {
		return SamplingAddress;
	}

	public void setSamplingAddress(String samplingAddress) {
		SamplingAddress = samplingAddress;
	}

	public String getChargePerson() {
		return ChargePerson;
	}

	public void setChargePerson(String chargePerson) {
		ChargePerson = chargePerson;
	}

	public String getSamplingPerson() {
		return SamplingPerson;
	}

	public void setSamplingPerson(String samplingPerson) {
		SamplingPerson = samplingPerson;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getRegisterUser() {
		return RegisterUser;
	}

	public void setRegisterUser(String registerUser) {
		RegisterUser = registerUser;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
