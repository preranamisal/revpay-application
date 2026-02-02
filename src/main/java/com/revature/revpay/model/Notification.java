
package com.revature.revpay.model;

import java.sql.Timestamp;

public class Notification {

    private int id;             // Notification ID
    private int userId;         // Owner of the notification
    private String message;     // Message text
    private String type;        // DEBIT, CREDIT, INFO, ALERT
    private boolean isRead;     // Whether user has read it
    private Timestamp createdAt; // Timestamp of creation

    // ===== Constructors =====

    public Notification() {
        this.isRead = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public Notification(int userId, String message, String type) {
        this.userId = userId;
        this.message = message;
        this.type = type;
        this.isRead = false;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // ===== Getters & Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { this.isRead = read; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // ===== Utility Methods =====

    // Mark notification as read
    public void markAsRead() {
        this.isRead = true;
    }

    // Display nicely formatted notification
    @Override
    public String toString() {
        return "[" + (isRead ? "READ" : "UNREAD") + "] " +
                type + " | " +
                message + " | " +
                createdAt;
    }
}
