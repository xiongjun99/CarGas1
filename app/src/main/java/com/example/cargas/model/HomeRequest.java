package com.example.cargas.model;

public class HomeRequest {
	private String CarNo;
	private String CarColor;

	public HomeRequest(String carNo, String carColor) {
		super();
		CarNo = carNo;
		CarColor = carColor;
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

}
