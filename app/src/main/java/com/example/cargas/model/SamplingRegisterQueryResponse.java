package com.example.cargas.model;

import java.io.Serializable;

public class SamplingRegisterQueryResponse implements Serializable{

	private String SamplingRegisterID;
	private String SamplingCreateID;
	private String CarNo;
	private String CarColor;
	private String CarType;
	private String UseNature;
	private String RegistrationDate;
	private String BrandModel;
	private String EngineModel;
	private String Passengers;
	private String MaxTotalMass;
	private String EmissionStandards;
	private String EmissionLimits;
	private String AmbientTemperature;
	private String EnvironmentalPressure;
	private String RelativeHumidity;
	private String SmokeOpacity1;
	private String SmokeOpacity2;
	private String SmokeOpacity3;
	private String SmokeOpacityAVG;
	private String CheckResult;
	private String RegisterUser;
	private String CheckTime;
	private String CheckAddress;
	private String Remark;
	private String ServerId;
	
	private String fueltype;
	private String Oiltemperature;
	private String Excessairratio;
	private String Low_co;
	private String Low_hc;
	private String Low_no;
	private String Low_o2;
	private String Low_co2;
	private String High_co;
	private String High_hc;
	private String High_no;
	private String High_o2;
	private String High_co2;
	private String PenaltyReceivable;
	private String PenaltyCollected;
	private String UserResult;
	public SamplingRegisterQueryResponse(String samplingRegisterID,
			String samplingCreateID, String carNo, String carColor,
			String carType, String useNature, String registrationDate,
			String brandModel, String engineModel, String passengers,
			String maxTotalMass, String emissionStandards,
			String emissionLimits, String ambientTemperature,
			String environmentalPressure, String relativeHumidity,
			String smokeOpacity1, String smokeOpacity2, String smokeOpacity3,
			String smokeOpacityAVG, String checkResult, String registerUser,
			String checkTime, String checkAddress, String remark,
			String serverId, String fueltype, String oiltemperature,
			String excessairratio, String low_co, String low_hc, String low_no,
			String low_o2, String low_co2, String high_co, String high_hc,
			String high_no, String high_o2, String high_co2,
			String penaltyReceivable, String penaltyCollected, String userResult) {
		super();
		SamplingRegisterID = samplingRegisterID;
		SamplingCreateID = samplingCreateID;
		CarNo = carNo;
		CarColor = carColor;
		CarType = carType;
		UseNature = useNature;
		RegistrationDate = registrationDate;
		BrandModel = brandModel;
		EngineModel = engineModel;
		Passengers = passengers;
		MaxTotalMass = maxTotalMass;
		EmissionStandards = emissionStandards;
		EmissionLimits = emissionLimits;
		AmbientTemperature = ambientTemperature;
		EnvironmentalPressure = environmentalPressure;
		RelativeHumidity = relativeHumidity;
		SmokeOpacity1 = smokeOpacity1;
		SmokeOpacity2 = smokeOpacity2;
		SmokeOpacity3 = smokeOpacity3;
		SmokeOpacityAVG = smokeOpacityAVG;
		CheckResult = checkResult;
		RegisterUser = registerUser;
		CheckTime = checkTime;
		CheckAddress = checkAddress;
		Remark = remark;
		ServerId = serverId;
		this.fueltype = fueltype;
		Oiltemperature = oiltemperature;
		Excessairratio = excessairratio;
		Low_co = low_co;
		Low_hc = low_hc;
		Low_no = low_no;
		Low_o2 = low_o2;
		Low_co2 = low_co2;
		High_co = high_co;
		High_hc = high_hc;
		High_no = high_no;
		High_o2 = high_o2;
		High_co2 = high_co2;
		PenaltyReceivable = penaltyReceivable;
		PenaltyCollected = penaltyCollected;
		UserResult = userResult;
	}
	public String getSamplingRegisterID() {
		return SamplingRegisterID;
	}
	public void setSamplingRegisterID(String samplingRegisterID) {
		SamplingRegisterID = samplingRegisterID;
	}
	public String getSamplingCreateID() {
		return SamplingCreateID;
	}
	public void setSamplingCreateID(String samplingCreateID) {
		SamplingCreateID = samplingCreateID;
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
	public String getCarType() {
		return CarType;
	}
	public void setCarType(String carType) {
		CarType = carType;
	}
	public String getUseNature() {
		return UseNature;
	}
	public void setUseNature(String useNature) {
		UseNature = useNature;
	}
	public String getRegistrationDate() {
		return RegistrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		RegistrationDate = registrationDate;
	}
	public String getBrandModel() {
		return BrandModel;
	}
	public void setBrandModel(String brandModel) {
		BrandModel = brandModel;
	}
	public String getEngineModel() {
		return EngineModel;
	}
	public void setEngineModel(String engineModel) {
		EngineModel = engineModel;
	}
	public String getPassengers() {
		return Passengers;
	}
	public void setPassengers(String passengers) {
		Passengers = passengers;
	}
	public String getMaxTotalMass() {
		return MaxTotalMass;
	}
	public void setMaxTotalMass(String maxTotalMass) {
		MaxTotalMass = maxTotalMass;
	}
	public String getEmissionStandards() {
		return EmissionStandards;
	}
	public void setEmissionStandards(String emissionStandards) {
		EmissionStandards = emissionStandards;
	}
	public String getEmissionLimits() {
		return EmissionLimits;
	}
	public void setEmissionLimits(String emissionLimits) {
		EmissionLimits = emissionLimits;
	}
	public String getAmbientTemperature() {
		return AmbientTemperature;
	}
	public void setAmbientTemperature(String ambientTemperature) {
		AmbientTemperature = ambientTemperature;
	}
	public String getEnvironmentalPressure() {
		return EnvironmentalPressure;
	}
	public void setEnvironmentalPressure(String environmentalPressure) {
		EnvironmentalPressure = environmentalPressure;
	}
	public String getRelativeHumidity() {
		return RelativeHumidity;
	}
	public void setRelativeHumidity(String relativeHumidity) {
		RelativeHumidity = relativeHumidity;
	}
	public String getSmokeOpacity1() {
		return SmokeOpacity1;
	}
	public void setSmokeOpacity1(String smokeOpacity1) {
		SmokeOpacity1 = smokeOpacity1;
	}
	public String getSmokeOpacity2() {
		return SmokeOpacity2;
	}
	public void setSmokeOpacity2(String smokeOpacity2) {
		SmokeOpacity2 = smokeOpacity2;
	}
	public String getSmokeOpacity3() {
		return SmokeOpacity3;
	}
	public void setSmokeOpacity3(String smokeOpacity3) {
		SmokeOpacity3 = smokeOpacity3;
	}
	public String getSmokeOpacityAVG() {
		return SmokeOpacityAVG;
	}
	public void setSmokeOpacityAVG(String smokeOpacityAVG) {
		SmokeOpacityAVG = smokeOpacityAVG;
	}
	public String getCheckResult() {
		return CheckResult;
	}
	public void setCheckResult(String checkResult) {
		CheckResult = checkResult;
	}
	public String getRegisterUser() {
		return RegisterUser;
	}
	public void setRegisterUser(String registerUser) {
		RegisterUser = registerUser;
	}
	public String getCheckTime() {
		return CheckTime;
	}
	public void setCheckTime(String checkTime) {
		CheckTime = checkTime;
	}
	public String getCheckAddress() {
		return CheckAddress;
	}
	public void setCheckAddress(String checkAddress) {
		CheckAddress = checkAddress;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getServerId() {
		return ServerId;
	}
	public void setServerId(String serverId) {
		ServerId = serverId;
	}
	public String getFueltype() {
		return fueltype;
	}
	public void setFueltype(String fueltype) {
		this.fueltype = fueltype;
	}
	public String getOiltemperature() {
		return Oiltemperature;
	}
	public void setOiltemperature(String oiltemperature) {
		Oiltemperature = oiltemperature;
	}
	public String getExcessairratio() {
		return Excessairratio;
	}
	public void setExcessairratio(String excessairratio) {
		Excessairratio = excessairratio;
	}
	public String getLow_co() {
		return Low_co;
	}
	public void setLow_co(String low_co) {
		Low_co = low_co;
	}
	public String getLow_hc() {
		return Low_hc;
	}
	public void setLow_hc(String low_hc) {
		Low_hc = low_hc;
	}
	public String getLow_no() {
		return Low_no;
	}
	public void setLow_no(String low_no) {
		Low_no = low_no;
	}
	public String getLow_o2() {
		return Low_o2;
	}
	public void setLow_o2(String low_o2) {
		Low_o2 = low_o2;
	}
	public String getLow_co2() {
		return Low_co2;
	}
	public void setLow_co2(String low_co2) {
		Low_co2 = low_co2;
	}
	public String getHigh_co() {
		return High_co;
	}
	public void setHigh_co(String high_co) {
		High_co = high_co;
	}
	public String getHigh_hc() {
		return High_hc;
	}
	public void setHigh_hc(String high_hc) {
		High_hc = high_hc;
	}
	public String getHigh_no() {
		return High_no;
	}
	public void setHigh_no(String high_no) {
		High_no = high_no;
	}
	public String getHigh_o2() {
		return High_o2;
	}
	public void setHigh_o2(String high_o2) {
		High_o2 = high_o2;
	}
	public String getHigh_co2() {
		return High_co2;
	}
	public void setHigh_co2(String high_co2) {
		High_co2 = high_co2;
	}
	public String getPenaltyReceivable() {
		return PenaltyReceivable;
	}
	public void setPenaltyReceivable(String penaltyReceivable) {
		PenaltyReceivable = penaltyReceivable;
	}
	public String getPenaltyCollected() {
		return PenaltyCollected;
	}
	public void setPenaltyCollected(String penaltyCollected) {
		PenaltyCollected = penaltyCollected;
	}
	public String getUserResult() {
		return UserResult;
	}
	public void setUserResult(String userResult) {
		UserResult = userResult;
	}
	


}
