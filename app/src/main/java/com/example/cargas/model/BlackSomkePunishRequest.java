package com.example.cargas.model;

public class BlackSomkePunishRequest {

	private String SessionID;
	private String PunishTime;
	private String BlackSmokeRegisterID;

	public BlackSomkePunishRequest(String sessionID, String punishTime,
			String blackSmokeRegisterID) {
		super();
		SessionID = sessionID;
		PunishTime = punishTime;
		BlackSmokeRegisterID = blackSmokeRegisterID;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getPunishTime() {
		return PunishTime;
	}

	public void setPunishTime(String punishTime) {
		PunishTime = punishTime;
	}

	public String getBlackSmokeRegisterID() {
		return BlackSmokeRegisterID;
	}

	public void setBlackSmokeRegisterID(String blackSmokeRegisterID) {
		BlackSmokeRegisterID = blackSmokeRegisterID;
	}

}
