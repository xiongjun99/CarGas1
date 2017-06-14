package com.example.cargas.model;

public class RoadCheckQueryRequest {

	private String SessionID;
	private String StartTime;
	private String EndTime;

	public RoadCheckQueryRequest(String sessionID, String startTime,
			String endTime) {
		super();
		SessionID = sessionID;
		StartTime = startTime;
		EndTime = endTime;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
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

}
