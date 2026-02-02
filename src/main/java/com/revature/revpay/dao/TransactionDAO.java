
package com.revature.revpay.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Transaction;
import com.revature.revpay.util.DBConnection;

public class TransactionDAO {

    // ================= CREATE TRANSACTION =================
    public void createTransaction(Transaction transaction) throws Exception {

        String sql =
                "INSERT INTO transactions " +
                "(sender_id, receiver_id, amount, status, note) " +
                "VALUES (?, ?, ?, ?, ?)";

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

    // ================= BASIC TRANSACTION HISTORY =================
    public List<Transaction> getTransactionsByUser(int userId) throws Exception {

        List<Transaction> list = new ArrayList<>();

        String sql =
                "SELECT * FROM transactions " +
                "WHERE sender_id = ? OR receiver_id = ? " +
                "ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapTransaction(rs, userId));
            }
        }
        return list;
    }

    // ================= FILTERED TRANSACTIONS =================
    public List<Transaction> getFilteredTransactions(
            int userId,
            String type,
            Date fromDate,
            Date toDate,
            Double minAmount,
            Double maxAmount,
            String status,
            String search
    ) throws Exception {

        List<Transaction> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM transactions " +
                "WHERE (sender_id = ? OR receiver_id = ?)"
        );

        if (status != null) sql.append(" AND status = ?");
        if (fromDate != null) sql.append(" AND created_at >= ?");
        if (toDate != null) sql.append(" AND created_at <= ?");
        if (minAmount != null) sql.append(" AND amount >= ?");
        if (maxAmount != null) sql.append(" AND amount <= ?");
        if (search != null && !search.isEmpty()) sql.append(" AND note LIKE ?");

        sql.append(" ORDER BY created_at DESC");

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            ps.setInt(idx++, userId);
            ps.setInt(idx++, userId);

            if (status != null) ps.setString(idx++, status);
            if (fromDate != null) ps.setDate(idx++, fromDate);
            if (toDate != null) ps.setDate(idx++, toDate);
            if (minAmount != null) ps.setDouble(idx++, minAmount);
            if (maxAmount != null) ps.setDouble(idx++, maxAmount);
            if (search != null && !search.isEmpty())
                ps.setString(idx++, "%" + search + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction tx = mapTransaction(rs, userId);

                // CREDIT / DEBIT filter
                if (type != null) {
                    if ("CREDIT".equalsIgnoreCase(type) && tx.getReceiverId() != userId)
                        continue;
                    if ("DEBIT".equalsIgnoreCase(type) && tx.getSenderId() != userId)
                        continue;
                }

                list.add(tx);
            }
        }

        return list;
    }

    // ================= MAP RESULTSET =================
    private Transaction mapTransaction(ResultSet rs, int userId) throws Exception {

        Transaction tx = new Transaction();
        tx.setId(rs.getInt("id"));
        tx.setSenderId(rs.getInt("sender_id"));
        tx.setReceiverId(rs.getInt("receiver_id"));
        tx.setAmount(rs.getDouble("amount"));
        tx.setStatus(rs.getString("status"));
        tx.setNote(rs.getString("note"));
        tx.setCreatedAt(rs.getTimestamp("created_at"));

        // Set CREDIT / DEBIT for logged-in user
        if (rs.getInt("receiver_id") == userId) {
            tx.setType("CREDIT");
        } else {
            tx.setType("DEBIT");
        }

        return tx;
    }
}
