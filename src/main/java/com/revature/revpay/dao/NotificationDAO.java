
package com.revature.revpay.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Notification;
import com.revature.revpay.util.DBConnection;

public class NotificationDAO {

    // ================= SAVE NOTIFICATION =================
    public void save(Notification notification) throws Exception {
        String sql = "INSERT INTO notifications (user_id, message, type, is_read, created_at) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setString(3, notification.getType());
            ps.setBoolean(4, notification.isRead());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Set current timestamp

            ps.executeUpdate();
        }
    }

    // ================= GET NOTIFICATIONS BY USER =================
    public List<Notification> getByUserId(int userId) throws Exception {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id=? ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setId(rs.getInt("id"));
                n.setUserId(rs.getInt("user_id"));
                n.setMessage(rs.getString("message"));
                n.setType(rs.getString("type"));
                n.setRead(rs.getBoolean("is_read"));
                n.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(n);
            }
        }
        return list;
    }

    // ================= MARK NOTIFICATION AS READ =================
    public void markAsRead(int id) throws Exception {
        String sql = "UPDATE notifications SET is_read=true WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ================= MARK ALL NOTIFICATIONS AS READ FOR A USER =================
    public void markAllAsRead(int userId) throws Exception {
        String sql = "UPDATE notifications SET is_read=true WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
