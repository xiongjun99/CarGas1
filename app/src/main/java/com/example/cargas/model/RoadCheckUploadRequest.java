package com.example.cargas.model;

public class RoadCheckUploadRequest {

	private String SessionID;
	private String CarNo;
	private String CarColor;
	private String CarType;
	private String Usenature;
	private String Registrationdate;
	private String Brandmodel;
	private String MaxTotalMass;
	private String EngineModel;
	private String Passengers;

	private String Emissionstandards;
	private String Emissionlimits;
	private String Ambienttemperature;
	private String Environmentalpressure;
	private String Relativehumidity;
	private String OilTemperature;
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
	private String CheckResult;
	private String CheckAddress;
	private String Longitude;
	private String Latitude;
	private String RegisterTime;

	public RoadCheckUploadRequest(String sessionID, String carNo,
			String carColor, String carType, String usenature,
			String registrationdate, String brandmodel, String maxTotalMass,
			String engineModel, String passengers, String emissionstandards,
			String emissionlimits, String ambienttemperature,
			String environmentalpressure, String relativehumidity,
			String oilTemperature, String excessairratio, String low_co,
			String low_hc, String low_no, String low_o2, String low_co2,
			String high_co, String high_hc, String high_no, String high_o2,
			String high_co2, String checkResult, String checkAddress,
			String longitude, String latitude, String registerTime) {
		super();
		SessionID = sessionID;
		CarNo = carNo;
		CarColor = carColor;
		CarType = carType;
		Usenature = usenature;
		Registrationdate = registrationdate;
		Brandmodel = brandmodel;
		MaxTotalMass = maxTotalMass;
		EngineModel = engineModel;
		Passengers = passengers;
		Emissionstandards = emissionstandards;
		Emissionlimits = emissionlimits;
		Ambienttemperature = ambienttemperature;
		Environmentalpressure = environmentalpressure;
		Relativehumidity = relativehumidity;
		OilTemperature = oilTemperature;
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
		CheckResult = checkResult;
		CheckAddress = checkAddress;
		Longitude = longitude;
		Latitude = latitude;
		RegisterTime = registerTime;
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

	public String getCarType() {
		return CarType;
	}

	public void setCarType(String carType) {
		CarType = carType;
	}

	public String getUsenature() {
		return Usenature;
	}

	public void setUsenature(String usenature) {
		Usenature = usenature;
	}

	public String getRegistrationdate() {
		return Registrationdate;
	}

	public void setRegistrationdate(String registrationdate) {
		Registrationdate = registrationdate;
	}

	public String getBrandmodel() {
		return Brandmodel;
	}

	public void setBrandmodel(String brandmodel) {
		Brandmodel = brandmodel;
	}

	public String getMaxTotalMass() {
		return MaxTotalMass;
	}

	public void setMaxTotalMass(String maxTotalMass) {
		MaxTotalMass = maxTotalMass;
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

	public String getEmissionstandards() {
		return Emissionstandards;
	}

	public void setEmissionstandards(String emissionstandards) {
		Emissionstandards = emissionstandards;
	}

	public String getEmissionlimits() {
		return Emissionlimits;
	}

	public void setEmissionlimits(String emissionlimits) {
		Emissionlimits = emissionlimits;
	}

	public String getAmbienttemperature() {
		return Ambienttemperature;
	}

	public void setAmbienttemperature(String ambienttemperature) {
		Ambienttemperature = ambienttemperature;
	}

	public String getEnvironmentalpressure() {
		return Environmentalpressure;
	}

	public void setEnvironmentalpressure(String environmentalpressure) {
		Environmentalpressure = environmentalpressure;
	}

	public String getRelativehumidity() {
		return Relativehumidity;
	}

	public void setRelativehumidity(String relativehumidity) {
		Relativehumidity = relativehumidity;
	}

	public String getOilTemperature() {
		return OilTemperature;
	}

	public void setOilTemperature(String oilTemperature) {
		OilTemperature = oilTemperature;
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

	public String getCheckResult() {
		return CheckResult;
	}

	public void setCheckResult(String checkResult) {
		CheckResult = checkResult;
	}

	public String getCheckAddress() {
		return CheckAddress;
	}

	public void setCheckAddress(String checkAddress) {
		CheckAddress = checkAddress;
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

	public String getRegisterTime() {
		return RegisterTime;
	}

	public void setRegisterTime(String registerTime) {
		RegisterTime = registerTime;
	}

}
