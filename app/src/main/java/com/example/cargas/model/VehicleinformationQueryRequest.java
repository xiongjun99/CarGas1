package com.example.cargas.model;

public class VehicleinformationQueryRequest {

	private String SessionID;
	private String CarNo;
	private String CarColor;

	public VehicleinformationQueryRequest(String sessionID, String carNo,
			String carColor) {
		super();
		SessionID = sessionID;
		CarNo = carNo;
		CarColor = carColor;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
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

}
