package com.example.cargas.model;

public class VehicleinformationQueryResponse {

	private String CarNo;
	private String CarColor;
	private String CarType;
	private String UseNature;
	private String RegistrationDate;
	private String BrandModel;
	private String MaxTotalMass;
	private String EngineModel;
	private String Passengers;
	private String EmissionStandards;

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

	public String getEmissionStandards() {
		return EmissionStandards;
	}

	public void setEmissionStandards(String emissionStandards) {
		EmissionStandards = emissionStandards;
	}

	public VehicleinformationQueryResponse(String carNo, String carColor,
			String carType, String useNature, String registrationDate,
			String brandModel, String maxTotalMass, String engineModel,
			String passengers, String emissionStandards) {
		super();
		CarNo = carNo;
		CarColor = carColor;
		CarType = carType;
		UseNature = useNature;
		RegistrationDate = registrationDate;
		BrandModel = brandModel;
		MaxTotalMass = maxTotalMass;
		EngineModel = engineModel;
		Passengers = passengers;
		EmissionStandards = emissionStandards;
	}

}
