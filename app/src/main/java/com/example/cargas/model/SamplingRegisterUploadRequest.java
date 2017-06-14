package com.example.cargas.model;

public class SamplingRegisterUploadRequest {

	private String SessionID;
	private String SamplingCreateid;
	private String CarNo;
	private String CarColor;
	private String CarType;
	private String Usenature;
	private String Registrationdate;
	private String Brandmodel;
	private String MaxTotalMass;
	private String Enginemodel;
	private String Passengers;
	private String Emissionstandards;
	private String Emissionlimits;
	private String Ambienttemperature;
	private String Environmentalpressure;
	private String Relativehumidity;
	private String Smokeopacity1;
	private String Smokeopacity2;
	private String Smokeopacity3;
	private String Smokeopacityavg;
	private String Checkresult;
	private String Checktime;
	private String Checkaddress;
	private String Longitude;
	private String Latitude;

	public SamplingRegisterUploadRequest(String sessionID, String samplingCreateid,
			String carNo, String carColor, String carType, String usenature,
			String registrationdate, String brandmodel, String maxTotalMass,
			String enginemodel, String passengers, String emissionstandards,
			String emissionlimits, String ambienttemperature,
			String environmentalpressure, String relativehumidity,
			String smokeopacity1, String smokeopacity2, String smokeopacity3,
			String smokeopacityavg, String checkresult, String checktime,
			String checkaddress, String longitude, String latitude) {
		super();
		SessionID = sessionID;
		SamplingCreateid = samplingCreateid;
		CarNo = carNo;
		CarColor = carColor;
		CarType = carType;
		Usenature = usenature;
		Registrationdate = registrationdate;
		Brandmodel = brandmodel;
		MaxTotalMass = maxTotalMass;
		Enginemodel = enginemodel;
		Passengers = passengers;
		Emissionstandards = emissionstandards;
		Emissionlimits = emissionlimits;
		Ambienttemperature = ambienttemperature;
		Environmentalpressure = environmentalpressure;
		Relativehumidity = relativehumidity;
		Smokeopacity1 = smokeopacity1;
		Smokeopacity2 = smokeopacity2;
		Smokeopacity3 = smokeopacity3;
		Smokeopacityavg = smokeopacityavg;
		Checkresult = checkresult;
		Checktime = checktime;
		Checkaddress = checkaddress;
		Longitude = longitude;
		Latitude = latitude;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getSamplingCreateid() {
		return SamplingCreateid;
	}

	public void setSamplingCreateid(String samplingCreateid) {
		SamplingCreateid = samplingCreateid;
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

	public String getEnginemodel() {
		return Enginemodel;
	}

	public void setEnginemodel(String enginemodel) {
		Enginemodel = enginemodel;
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

	public String getSmokeopacity1() {
		return Smokeopacity1;
	}

	public void setSmokeopacity1(String smokeopacity1) {
		Smokeopacity1 = smokeopacity1;
	}

	public String getSmokeopacity2() {
		return Smokeopacity2;
	}

	public void setSmokeopacity2(String smokeopacity2) {
		Smokeopacity2 = smokeopacity2;
	}

	public String getSmokeopacity3() {
		return Smokeopacity3;
	}

	public void setSmokeopacity3(String smokeopacity3) {
		Smokeopacity3 = smokeopacity3;
	}

	public String getSmokeopacityavg() {
		return Smokeopacityavg;
	}

	public void setSmokeopacityavg(String smokeopacityavg) {
		Smokeopacityavg = smokeopacityavg;
	}

	public String getCheckresult() {
		return Checkresult;
	}

	public void setCheckresult(String checkresult) {
		Checkresult = checkresult;
	}

	public String getChecktime() {
		return Checktime;
	}

	public void setChecktime(String checktime) {
		Checktime = checktime;
	}

	public String getCheckaddress() {
		return Checkaddress;
	}

	public void setCheckaddress(String checkaddress) {
		Checkaddress = checkaddress;
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
