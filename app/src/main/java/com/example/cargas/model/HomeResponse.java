package com.example.cargas.model;

public class HomeResponse {

	private String CarNo;
	private String CarColor;
	private String fuel_type;
	private String emission_standard_name;
	private String verdict_outcome_name;
	private String mark_type;
	private String mark_release_time;
	private String Remark;

	public HomeResponse() {

	}

	public HomeResponse(String carNo, String carColor, String fuel_type,
			String emission_standard_name, String verdict_outcome_name,
			String mark_type, String mark_release_time, String remark) {
		super();
		CarNo = carNo;
		CarColor = carColor;
		this.fuel_type = fuel_type;
		this.emission_standard_name = emission_standard_name;
		this.verdict_outcome_name = verdict_outcome_name;
		this.mark_type = mark_type;
		this.mark_release_time = mark_release_time;
		Remark = remark;
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

	public String getFuel_type() {
		return fuel_type;
	}

	public void setFuel_type(String fuel_type) {
		this.fuel_type = fuel_type;
	}

	public String getEmission_standard_name() {
		return emission_standard_name;
	}

	public void setEmission_standard_name(String emission_standard_name) {
		this.emission_standard_name = emission_standard_name;
	}

	public String getVerdict_outcome_name() {
		return verdict_outcome_name;
	}

	public void setVerdict_outcome_name(String verdict_outcome_name) {
		this.verdict_outcome_name = verdict_outcome_name;
	}

	public String getMark_type() {
		return mark_type;
	}

	public void setMark_type(String mark_type) {
		this.mark_type = mark_type;
	}

	public String getMark_release_time() {
		return mark_release_time;
	}

	public void setMark_release_time(String mark_release_time) {
		this.mark_release_time = mark_release_time;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
