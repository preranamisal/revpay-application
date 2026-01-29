package com.revature.revpay.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Transaction;
import com.revature.revpay.util.DBConnection;

public class TransactionDAO {

    // 1️⃣ Create transaction
    public void createTransaction(Transaction transaction) throws Exception {

        String sql = "INSERT INTO transactions (sender_id, receiver_id, amount, status, note) VALUES (?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, transaction.getSenderId());
            ps.setInt(2, transaction.getReceiverId());
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getStatus());
            ps.setString(5, transaction.getNote());

            ps.executeUpdate();
        }
    }

    // 2️⃣ Get transactions for a user
    public List<Transaction> getTransactionsByUser(int userId) throws Exception {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE sender_id=? OR receiver_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction tx = new Transaction();
                tx.setId(rs.getInt("id"));
                tx.setSenderId(rs.getInt("sender_id"));
                tx.setReceiverId(rs.getInt("receiver_id"));
                tx.setAmount(rs.getDouble("amount"));
                tx.setStatus(rs.getString("status"));
                tx.setNote(rs.getString("note"));
                tx.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(tx);
            }
        }
        return list;
    }
}

