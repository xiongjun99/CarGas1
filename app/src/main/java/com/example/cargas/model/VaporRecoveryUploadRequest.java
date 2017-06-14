package com.example.cargas.model;

public class VaporRecoveryUploadRequest {

	private String SessionID;
	private String Executiontime;
	private String Address;
	private String OrganizationID;
	private String Checkresult;
	private String Opinion;
	private String Longitude;
	private String Latitude;

	public VaporRecoveryUploadRequest(String sessionID, String executiontime,
			String address, String organizationID, String checkresult,
			String opinion, String longitude, String latitude) {
		super();
		SessionID = sessionID;
		Executiontime = executiontime;
		Address = address;
		OrganizationID = organizationID;
		Checkresult = checkresult;
		Opinion = opinion;
		Longitude = longitude;
		Latitude = latitude;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getExecutiontime() {
		return Executiontime;
	}

	public void setExecutiontime(String executiontime) {
		Executiontime = executiontime;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getOrganizationID() {
		return OrganizationID;
	}

	public void setOrganizationID(String organizationID) {
		OrganizationID = organizationID;
	}

	public String getCheckresult() {
		return Checkresult;
	}

	public void setCheckresult(String checkresult) {
		Checkresult = checkresult;
	}

	public String getOpinion() {
		return Opinion;
	}

	public void setOpinion(String opinion) {
		Opinion = opinion;
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
