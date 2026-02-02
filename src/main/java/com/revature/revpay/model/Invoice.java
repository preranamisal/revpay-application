package com.revature.revpay.model;

public class Invoice {
private int id;
private int businessId;
private String customerEmail;
private double amount;
private String status; // PAID / UNPAID


public Invoice() {}


public Invoice(int businessId, String customerEmail, double amount, String status) {
this.businessId = businessId;
this.customerEmail = customerEmail;
this.amount = amount;
this.status = status;
}
// getters & setters


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getBusinessId() {
	return businessId;
}


public void setBusinessId(int businessId) {
	this.businessId = businessId;
}


public String getCustomerEmail() {
	return customerEmail;
}


public void setCustomerEmail(String customerEmail) {
	this.customerEmail = customerEmail;
}


public double getAmount() {
	return amount;
}


public void setAmount(double amount) {
	this.amount = amount;
}


public String getStatus() {
	return status;
}


public void setStatus(String status) {
	this.status = status;
}


@Override
public String toString() {
	return "Invoice [id=" + id + ", businessId=" + businessId + ", customerEmail=" + customerEmail + ", amount="
			+ amount + ", status=" + status + "]";
}



}
