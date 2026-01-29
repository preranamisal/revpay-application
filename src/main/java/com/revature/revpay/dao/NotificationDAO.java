//package com.revature.revpay.dao;
//
//
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.revature.revpay.model.Notification;
//import com.revature.revpay.util.DBConnection;
//
//public class NotificationDAO {
//
//    // 1️⃣ Create notification
//    public void createNotification(Notification notification) throws Exception {
//
//        String sql = "INSERT INTO notifications (user_id, message, is_read) VALUES (?,?,?)";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, notification.getUserId());
//            ps.setString(2, notification.getMessage());
//            ps.setBoolean(3, notification.isRead());
//
//            ps.executeUpdate();
//        }
//    }
//
//    // 2️⃣ Get notifications for user
//    public List<Notification> getNotificationsByUser(int userId) throws Exception {
//
//        List<Notification> list = new ArrayList<>();
//
//        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Notification n = new Notification();
//                n.setId(rs.getInt("id"));
//                n.setUserId(rs.getInt("user_id"));
//                n.setMessage(rs.getString("message"));
//                n.setRead(rs.getBoolean("is_read"));
//                n.setCreatedAt(rs.getTimestamp("created_at"));
//
//                list.add(n);
//            }
//        }
//        return list;
//    }
//
//    // 3️⃣ Mark notification as read
//    public void markAsRead(int notificationId) throws Exception {
//
//        String sql = "UPDATE notifications SET is_read = true WHERE id = ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, notificationId);
//            ps.executeUpdate();
//        }
//    }
//}
//


//package com.revature.revpay.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//import com.revature.revpay.model.Notification;
//import com.revature.revpay.util.DBConnection;
//
//public class NotificationDAO {
//
//    public void save(Notification notification) throws Exception {
//
//        String sql = "INSERT INTO notifications (user_id, message, created_at) VALUES (?, ?, NOW())";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, notification.getUserId());
//            ps.setString(2, notification.getMessage());
//
//            ps.executeUpdate();
//        }
//    }
//}

package com.revature.revpay.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Notification;
import com.revature.revpay.util.DBConnection;
import java.sql.PreparedStatement;


public class NotificationDAO {

    // Save notification
    public void save(Notification notification) throws Exception {

    	String sql = "INSERT INTO notifications (user_id, message, type, is_read) "
    	           + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setString(3, notification.getType());
            ps.setBoolean(4, notification.isRead());

            ps.executeUpdate();
        }
    }

    // Get notifications by user
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

    // Mark as read
    public void markAsRead(int id) throws Exception {

        String sql = "UPDATE notifications SET is_read=true WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
//    public void create(int userId, String message) throws Exception {
//
//
//    	String sql =
//    	"INSERT INTO notifications (user_id, message, is_read) VALUES (?, ?, false)";
//
//
//    	try (Connection con = DBConnection.getConnection();
//    	PreparedStatement ps = con.prepareStatement(sql)) {
//
//
//    	ps.setInt(1, userId);
//    	ps.setString(2, message);
//
//
//    	ps.executeUpdate();
//    	}
//    	}
    
//    public void create(Notification n) throws Exception {
//
//        String sql = "INSERT INTO notifications(user_id, message, type, is_read) VALUES (?, ?, ?, ?)";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, n.getUserId());
//            ps.setString(2, n.getMessage());
//            ps.setString(3, n.getType());   // ⭐ ADD THIS
//            ps.setBoolean(4, false);
//
//            ps.executeUpdate();
//        }
//    }
}
