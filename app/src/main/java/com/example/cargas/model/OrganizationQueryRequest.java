package com.example.cargas.model;

public class OrganizationQueryRequest {

	private String SessionID;

	public OrganizationQueryRequest(String sessionID) {
		super();
		SessionID = sessionID;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

}
