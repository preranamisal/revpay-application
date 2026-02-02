
package com.revature.revpay.model;

import java.sql.Timestamp;

public class Transaction {

    private int id;
    private int senderId;
    private int receiverId;
    private double amount;
    private String status;   // SUCCESS / FAILED / PENDING
    private String note;
    private Timestamp createdAt;

    // CREDIT or DEBIT (already set by DAO)
    private String type;

    public Transaction() {}

    public Transaction(int senderId, int receiverId, double amount, String status, String note) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.status = status;
        this.note = note;
    }

    // ===== Getters & Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // ===== TYPE =====
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

