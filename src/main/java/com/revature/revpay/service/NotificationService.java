
package com.revature.revpay.service;

import java.util.List;

import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.model.Notification;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    // ================= SEND NOTIFICATION =================
    public void notifyUser(int userId, String message, String type) throws Exception {
        Notification notification = new Notification(userId, message, type);
        notificationDAO.save(notification);
    }

    // ================= GET USER NOTIFICATIONS =================
    public List<Notification> getUserNotifications(int userId) throws Exception {
        return notificationDAO.getByUserId(userId);
    }

    // ================= MARK SINGLE NOTIFICATION AS READ =================
    public void readNotification(int notificationId) throws Exception {
        notificationDAO.markAsRead(notificationId);
    }

    // ================= MARK ALL NOTIFICATIONS AS READ =================
    public void markAllAsRead(int userId) throws Exception {
        notificationDAO.markAllAsRead(userId);
    }

    // ================= GET UNREAD NOTIFICATION COUNT =================
    public int getUnreadCount(int userId) throws Exception {
        List<Notification> notifications = notificationDAO.getByUserId(userId);
        int count = 0;
        for (Notification n : notifications) {
            if (!n.isRead()) count++;
        }
        return count;
    }
}
