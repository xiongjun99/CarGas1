package com.example.cargas.model;

public class OrganizationQueryResponse {

	private String OrganizationID;
	private String OrganizationName;
	private String OrganizationAddress;
	private String Linkman;
	private String Phone;
	private String Remark;

	public OrganizationQueryResponse(String organizationID,
			String organizationName, String organizationAddress,
			String linkman, String phone, String remark) {
		super();
		OrganizationID = organizationID;
		OrganizationName = organizationName;
		OrganizationAddress = organizationAddress;
		Linkman = linkman;
		Phone = phone;
		Remark = remark;
	}

	public String getOrganizationID() {
		return OrganizationID;
	}

	public void setOrganizationID(String organizationID) {
		OrganizationID = organizationID;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getOrganizationAddress() {
		return OrganizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		OrganizationAddress = organizationAddress;
	}

	public String getLinkman() {
		return Linkman;
	}

	public void setLinkman(String linkman) {
		Linkman = linkman;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

}
