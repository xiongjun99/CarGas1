package com.example.cargas.model;

import java.io.Serializable;

/**
 * @author Totoy
 *
 */
public class BlackSmokeQueryResponse implements Serializable{
	private String BlackSmokeRegisterID;
	private String RegisterUser;
	private String RegisterTime;
	private String CarNo;
	private String CarColor;
	private String Address;
	private String FieldEvaluation;
	private String RegisterStatus;
	private String PunishTime;
	private String PunishUser;
	private String PunishStatus;
	private String Remark;
	private String ServerId;
	
	private boolean selected;
	
	public boolean isSelected(){
		
		return selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public BlackSmokeQueryResponse() {
	}

	public String getBlackSmokeRegisterID() {
		return BlackSmokeRegisterID;
	}

	public BlackSmokeQueryResponse(String blackSmokeRegisterID,
			String registerUser, String registerTime, String carNo,
			String carColor, String address, String fieldEvaluation,
			String registerStatus, String punishTime, String punishUser,
			String punishStatus, String remark, String ServerId) {
		super();
		BlackSmokeRegisterID = blackSmokeRegisterID;
		RegisterUser = registerUser;
		RegisterTime = registerTime;
		CarNo = carNo;
		CarColor = carColor;
		Address = address;
		FieldEvaluation = fieldEvaluation;
		RegisterStatus = registerStatus;
		PunishTime = punishTime;
		PunishUser = punishUser;
		PunishStatus = punishStatus;
		Remark = remark;
		this.ServerId = ServerId;
	}
	
	

	public String getServerId() {
		return ServerId;
	}

	public void setServerId(String serverId) {
		ServerId = serverId;
	}

	public void setBlackSmokeRegisterID(String blackSmokeRegisterID) {
		BlackSmokeRegisterID = blackSmokeRegisterID;
	}

	public String getRegisterUser() {
		return RegisterUser;
	}

	public void setRegisterUser(String registerUser) {
		RegisterUser = registerUser;
	}

	public String getRegisterTime() {
		return RegisterTime;
	}

	public void setRegisterTime(String registerTime) {
		RegisterTime = registerTime;
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

	public String getFieldEvaluation() {
		return FieldEvaluation;
	}

	public void setFieldEvaluation(String fieldEvaluation) {
		FieldEvaluation = fieldEvaluation;
	}

	public String getRegisterStatus() {
		return RegisterStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		RegisterStatus = registerStatus;
	}

	public String getPunishTime() {
		return PunishTime;
	}

	public void setPunishTime(String punishTime) {
		PunishTime = punishTime;
	}

	public String getPunishUser() {
		return PunishUser;
	}

	public void setPunishUser(String punishUser) {
		PunishUser = punishUser;
	}

	public String getPunishStatus() {
		return PunishStatus;
	}

	public void setPunishStatus(String punishStatus) {
		PunishStatus = punishStatus;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
