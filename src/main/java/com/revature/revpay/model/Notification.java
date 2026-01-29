//package com.revature.revpay.model;
//
//
//
//import java.sql.Timestamp;
//
//public class Notification {
//
//    private int id;
//    private int userId;
//    private String message;
//    private boolean isRead;
//    private Timestamp createdAt;
//
//    public Notification() {}
//
//    public Notification(int userId, String message) {
//        this.userId = userId;
//        this.message = message;
//        this.isRead = false;
//    }
// // getters and setters
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public int getUserId() {
//		return userId;
//	}
//
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public boolean isRead() {
//		return isRead;
//	}
//
//	public void setRead(boolean isRead) {
//		this.isRead = isRead;
//	}
//
//	public Timestamp getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Timestamp createdAt) {
//		this.createdAt = createdAt;
//	}
//
//    
//	
//    
//}
//
package com.revature.revpay.model;

import java.sql.Timestamp;

public class Notification {

    private int id;
    private int userId;
    private String message;
    private String type;
    private boolean isRead;
    private Timestamp createdAt;

    // ✅ Constructors
    public Notification() {}

    public Notification(int userId, String message, String type) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.isRead = false;
    }

    // ✅ Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}