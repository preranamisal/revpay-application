

package com.revature.revpay.service;

import java.util.List;

import com.revature.revpay.dao.MoneyRequestDAO;
import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.model.MoneyRequest;
import com.revature.revpay.model.Notification;
import com.revature.revpay.service.WalletService;

public class MoneyRequestService {

    private MoneyRequestDAO dao = new MoneyRequestDAO();
   // private WalletDAO walletDAO = new WalletDAO();
    private WalletService walletService = new WalletService();
    private NotificationDAO notificationDAO = new NotificationDAO();

    // ================= REQUEST MONEY =================
    public void requestMoney(int senderId, int receiverId,
                             double amount, String note) throws Exception {

        MoneyRequest req = new MoneyRequest();
        req.setSenderId(senderId);
        req.setReceiverId(receiverId);
        req.setAmount(amount);
        req.setNote(note);

        dao.create(req);
    }

    // ================= VIEW INCOMING =================
    public List<MoneyRequest> incomingRequests(int userId) throws Exception {
        return dao.getIncoming(userId);
    }

    
    
    public void accept(int requestId) throws Exception {

        MoneyRequest req = dao.getById(requestId);

        if (req == null) {
            throw new Exception("Money request not found");
        }

        // 💸 transfer money (receiver ➜ sender)
        walletService.transfer(
                req.getReceiverId(),
                req.getSenderId(),
                req.getAmount(),
                "Money request accepted"
        );

        dao.updateStatus(requestId, "ACCEPTED");

        Notification n1 = new Notification();
        n1.setUserId(req.getSenderId());
        n1.setMessage("✅ Your money request of ₹" + req.getAmount() + " was accepted");
        n1.setType("REQUEST_ACCEPTED");   // ⭐ ADD THIS


        notificationDAO.save(n1);
    }

    // ================= DECLINE REQUEST =================
    public void decline(int requestId) throws Exception {

        MoneyRequest req = dao.getById(requestId);
        dao.updateStatus(requestId, "DECLINED");

//        notificationDAO.create(
//                req.getSenderId(),
//                "❌ Your money request was declined"
//        );
        
        Notification n2 = new Notification();
        n2.setUserId(req.getSenderId());
        n2.setMessage("❌ Your money request was declined");
        n2.setType("REQUEST_DECLINED");   // ⭐ ADD THIS

        notificationDAO.save(n2);
    }

    // ================= CANCEL REQUEST (SENDER) =================
    public void cancel(int requestId) throws Exception {
        dao.updateStatus(requestId, "CANCELLED");
    }
    
    public MoneyRequest getRequestById(int id) throws Exception {
        MoneyRequest request = dao.getById(id);
        if (request == null) throw new Exception("Money request not found");
        return request;
    }
}