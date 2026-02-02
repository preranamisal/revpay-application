package com.revature.revpay.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.MoneyRequest;
import com.revature.revpay.util.DBConnection;

public class MoneyRequestDAO {

    // CREATE REQUEST
    public void create(MoneyRequest req) throws Exception {
        String sql =
            "INSERT INTO money_requests (sender_id, receiver_id, amount, note) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, req.getSenderId());
            ps.setInt(2, req.getReceiverId());
            ps.setDouble(3, req.getAmount());
            ps.setString(4, req.getNote());

            ps.executeUpdate();
        }
    }

    // INCOMING REQUESTS
    public List<MoneyRequest> getIncoming(int userId) throws Exception {
        String sql =
            "SELECT * FROM money_requests WHERE receiver_id=? AND status='PENDING'";

        List<MoneyRequest> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MoneyRequest r = new MoneyRequest();
                r.setId(rs.getInt("id"));
                r.setSenderId(rs.getInt("sender_id"));
                r.setAmount(rs.getDouble("amount"));
                r.setNote(rs.getString("note"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        }
        return list;
    }

    // UPDATE STATUS
    public void updateStatus(int requestId, String status) throws Exception {
        String sql =
            "UPDATE money_requests SET status=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        }
    }
    
    public MoneyRequest getById(int requestId) throws Exception {

        String sql = "SELECT * FROM money_requests WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MoneyRequest r = new MoneyRequest();
                r.setId(rs.getInt("id"));
                r.setSenderId(rs.getInt("sender_id"));
                r.setReceiverId(rs.getInt("receiver_id"));
                r.setAmount(rs.getDouble("amount"));
                r.setNote(rs.getString("note"));
                r.setStatus(rs.getString("status"));
                return r;
            }
        }
        return null;
    }
    
    private MoneyRequest map(ResultSet rs) throws Exception {

        MoneyRequest r = new MoneyRequest();
        r.setId(rs.getInt("id"));
        r.setSenderId(rs.getInt("sender_id"));
        r.setReceiverId(rs.getInt("receiver_id"));
        r.setAmount(rs.getDouble("amount"));
        r.setNote(rs.getString("note"));
        r.setStatus(rs.getString("status"));
        return r;
    }
    
    
}