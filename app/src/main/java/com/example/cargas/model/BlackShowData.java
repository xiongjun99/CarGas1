package com.example.cargas.model;

public class BlackShowData {

	private String carNumber;
	private String carColor;
	private String time;
	private String status;

	public BlackShowData(String carNumber, String carColor, String time,
			String status) {
		super();
		this.carNumber = carNumber;
		this.carColor = carColor;
		this.time = time;
		this.status = status;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
