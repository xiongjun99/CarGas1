package com.example.cargas.model;

public class BlackSmokeRegisterUploadRequest {

	private String SessionID;
	private String Registertime;
	private String CarNo;
	private String CarColor;
	private String Address;
	private String Fieldevaluation;
	private String RegisterStatus;
	private String Longitude;
	private String Latitude;

	public BlackSmokeRegisterUploadRequest() {

	}

	public BlackSmokeRegisterUploadRequest(String sessionID, String registertime,
			String carNo, String carColor, String address,
			String fieldevaluation, String registerStatus, String longitude,
			String latitude) {
		super();
		SessionID = sessionID;
		Registertime = registertime;
		CarNo = carNo;
		CarColor = carColor;
		Address = address;
		Fieldevaluation = fieldevaluation;
		RegisterStatus = registerStatus;
		Longitude = longitude;
		Latitude = latitude;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getRegistertime() {
		return Registertime;
	}

	public void setRegistertime(String registertime) {
		Registertime = registertime;
	}

	public String getCarNo() {
		return CarNo;
	}

	public void setCarNo(String carNo) {
		CarNo = carNo;
	}

	public String getCarColor() {
		return CarColor;
	}

	public void setCarColor(String carColor) {
		CarColor = carColor;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getFieldevaluation() {
		return Fieldevaluation;
	}

	public void setFieldevaluation(String fieldevaluation) {
		Fieldevaluation = fieldevaluation;
	}

	public String getRegisterStatus() {
		return RegisterStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		RegisterStatus = registerStatus;
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
