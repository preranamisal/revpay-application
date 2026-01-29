//package com.revature.revpay.service;
//
//
//
//import java.util.List;
//
//import com.revature.revpay.dao.NotificationDAO;
//import com.revature.revpay.model.Notification;
//
//public class NotificationService {
//
//    private NotificationDAO notificationDAO = new NotificationDAO();
//
//    // Send notification
//    public void notifyUser(int userId, String message) throws Exception {
//        Notification notification = new Notification(userId, message);
//        notificationDAO.createNotification(notification);
//    }
//
//    // View notifications
//    public List<Notification> getUserNotifications(int userId) throws Exception {
//        return notificationDAO.getNotificationsByUser(userId);
//    }
//
//    // Read notification
//    public void readNotification(int notificationId) throws Exception {
//        notificationDAO.markAsRead(notificationId);
//    }

package com.revature.revpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.model.Notification;
import com.revature.revpay.util.DBConnection;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    // Send notification
    public void notifyUser(int userId, String message, String type) throws Exception {

        Notification notification =
                new Notification(userId, message, type);

        notificationDAO.save(notification);
    }

    // View notifications
    public List<Notification> getUserNotifications(int userId) throws Exception {
        return notificationDAO.getByUserId(userId);
    }

    // Read notification
    public void readNotification(int notificationId) throws Exception {
        notificationDAO.markAsRead(notificationId);
    }
    
    public int getUnreadCount(int userId) throws Exception {

        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND is_read = false";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public void markAllAsRead(int userId) throws Exception {

        String sql = "UPDATE notifications SET is_read = true WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
//}
//
