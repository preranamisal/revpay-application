//package com.revature.revpay.service;
//
//
//
//import com.revature.revpay.dao.TransactionDAO;
//import com.revature.revpay.model.Transaction;
//
//public class TransactionService {
//
//    private TransactionDAO transactionDAO = new TransactionDAO();
//
//    // Send money between users
//    public void sendMoney(int senderId, int receiverId, double amount, String note) throws Exception {
//
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Amount must be greater than zero");
//        }
//
//        Transaction transaction = new Transaction(
//                senderId,
//                receiverId,
//                amount,
//                "SUCCESS",
//                note
//        );
//
//        transactionDAO.createTransaction(transaction);
//    }
//}



package com.revature.revpay.service;

import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.dao.TransactionDAO;
import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.model.Notification;
import com.revature.revpay.model.Transaction;
import com.revature.revpay.model.Wallet;

public class TransactionService {

    private TransactionDAO transactionDAO = new TransactionDAO();
    private WalletDAO walletDAO = new WalletDAO();
    private NotificationDAO notificationDAO = new NotificationDAO();

    // Send money between users
    public void sendMoney(int senderId, int receiverId, double amount, String note) throws Exception {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        // 1️⃣ Get wallets
        Wallet senderWallet = walletDAO.getWalletByUserId(senderId);
        Wallet receiverWallet = walletDAO.getWalletByUserId(receiverId);

        if (senderWallet == null || receiverWallet == null) {
            throw new Exception("Wallet not found");
        }

        // 2️⃣ Check balance
        if (senderWallet.getBalance() < amount) {
            throw new Exception("Insufficient balance");
        }

        // 3️⃣ Debit sender
        walletDAO.updateBalance(
                senderId,
                senderWallet.getBalance() - amount
        );

        // 4️⃣ Credit receiver
        walletDAO.updateBalance(
                receiverId,
                receiverWallet.getBalance() + amount
        );

        // 5️⃣ Save transaction
        Transaction transaction = new Transaction(
                senderId,
                receiverId,
                amount,
                "SUCCESS",
                note
        );
        transactionDAO.createTransaction(transaction);

        // 6️⃣ Notifications
//        notificationDAO.save(
//                new Notification(senderId, "You sent ₹" + amount)
//        );
//        notificationDAO.save(
//                new Notification(receiverId, "You received ₹" + amount)
//        );
     // Sender notification
        notificationDAO.save(
            new Notification(
                senderId,
                "You sent ₹" + amount,
                "DEBIT"
            )
        );

        // Receiver notification
        notificationDAO.save(
            new Notification(
                receiverId,
                "You received ₹" + amount,
                "CREDIT"
            )
        );
    }
    
    public void transferMoney(int senderId, int receiverId, double amount, String senderName) throws Exception {

        walletDAO.transfer(senderId, receiverId, amount);

        Notification n = new Notification();
        n.setUserId(receiverId);
        n.setMessage("You received ₹" + amount + " from " + senderName);
        n.setType("CREDIT");   // ⭐ ADD THIS

        notificationDAO.save(n);
    }
}