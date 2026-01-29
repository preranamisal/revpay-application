package com.revature.revpay.model;

public class User {
private int id;
private String name;
private String email;
private String phone;
private String password;
private String pin;
private String role; // PERSONAL / BUSINESS
private String securityQuestion;
private String securityAnswer;


public User() {}
public User(String name, String email, String phone, String password, String pin, String role,String securityQuestion,
		String securityAnswer) {
this.name = name;
this.email = email;
this.phone = phone;
this.password = password;
this.pin = pin;
this.role = role;
this.securityQuestion = securityQuestion;
this.securityAnswer = securityAnswer;
}
// getters & setters
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPin() {
	return pin;
}
public void setPin(String pin) {
	this.pin = pin;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getSecurityQuestion() {
    return securityQuestion;
}
public void setSecurityQuestion(String securityQuestion) {
this.securityQuestion = securityQuestion;
}

public String getSecurityAnswer() {
    return securityAnswer;
}
public void setSecurityAnswer(String securityAnswer) {
this.securityAnswer = securityAnswer;
}

}