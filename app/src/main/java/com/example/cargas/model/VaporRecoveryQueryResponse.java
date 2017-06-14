package com.example.cargas.model;

import java.io.Serializable;

public class VaporRecoveryQueryResponse implements Serializable{

	private String VaporRecoveryID;
	private String ExecutionTime;

	private String Address;

	private String OrganizationID;

	private String CheckResult;

	private String Opinion;

	private String RegisterUser;

	private String Remark;
	
	private String ServerId;

	public VaporRecoveryQueryResponse(String vaporRecoveryID,
			String executionTime, String address, String organizationID,
			String checkResult, String opinion, String registerUser,
			String remark, String ServerId) {
		super();
		this.ServerId = ServerId;
		VaporRecoveryID = vaporRecoveryID;
		ExecutionTime = executionTime;
		Address = address;
		OrganizationID = organizationID;
		CheckResult = checkResult;
		Opinion = opinion;
		RegisterUser = registerUser;
		Remark = remark;
	}
	
	

	public String getServerId() {
		return ServerId;
	}



	public void setServerId(String serverId) {
		ServerId = serverId;
	}



	public String getVaporRecoveryID() {
		return VaporRecoveryID;
	}

	public void setVaporRecoveryID(String vaporRecoveryID) {
		VaporRecoveryID = vaporRecoveryID;
	}

	public String getExecutionTime() {
		return ExecutionTime;
	}

	public void setExecutionTime(String executionTime) {
		ExecutionTime = executionTime;
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

	public String getCheckResult() {
		return CheckResult;
	}

	public void setCheckResult(String checkResult) {
		CheckResult = checkResult;
	}

	public String getOpinion() {
		return Opinion;
	}

	public void setOpinion(String opinion) {
		Opinion = opinion;
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
