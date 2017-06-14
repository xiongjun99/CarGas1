package com.example.cargas.model;

public class SamplingCreateUploadRequest {

	private String SessionID;
	private String SamplingName;
	private String ChargePerson;
	private String SamplingAddress;
	private String SamplingPerson;
	private String StartTime;
	private String EndTime;
	private String Longitude;
	private String Latitude;

	public SamplingCreateUploadRequest(String sessionID, String samplingName,
			String chargePerson, String samplingAddress, String samplingPerson,
			String startTime, String endTime, String longitude, String latitude) {
		super();
		SessionID = sessionID;
		SamplingName = samplingName;
		ChargePerson = chargePerson;
		SamplingAddress = samplingAddress;
		SamplingPerson = samplingPerson;
		StartTime = startTime;
		EndTime = endTime;
		Longitude = longitude;
		Latitude = latitude;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getSamplingName() {
		return SamplingName;
	}

	public void setSamplingName(String samplingName) {
		SamplingName = samplingName;
	}

	public String getChargePerson() {
		return ChargePerson;
	}

	public void setChargePerson(String chargePerson) {
		ChargePerson = chargePerson;
	}

	public String getSamplingAddress() {
		return SamplingAddress;
	}

	public void setSamplingAddress(String samplingAddress) {
		SamplingAddress = samplingAddress;
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

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

}
