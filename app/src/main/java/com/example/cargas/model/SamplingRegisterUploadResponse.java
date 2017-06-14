package com.example.cargas.model;

public class SamplingRegisterUploadResponse {

	private String Success;
	private String Result;

	public SamplingRegisterUploadResponse(String success, String result) {
		super();
		Success = success;
		Result = result;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

}
