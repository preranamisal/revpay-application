package com.revature.revpay.model;

public class BusinessUser extends User {
private String businessName;
private String businessType;
private String taxId;
private String address;


public BusinessUser() {}


public BusinessUser(String name, String email, String phone, String password, String pin,String securityQuestion,
		String securityAnswer,String businessName, String businessType, String taxId, String address) {
super(name, email, phone, password, pin, "BUSINESS",securityQuestion,securityAnswer);
this.businessName = businessName;
this.businessType = businessType;
this.taxId = taxId;
this.address = address;
}
// getters & setters


public String getBusinessName() {
	return businessName;
}


public void setBusinessName(String businessName) {
	this.businessName = businessName;
}


public String getBusinessType() {
	return businessType;
}


public void setBusinessType(String businessType) {
	this.businessType = businessType;
}


public String getTaxId() {
	return taxId;
}


public void setTaxId(String taxId) {
	this.taxId = taxId;
}


public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}

}
