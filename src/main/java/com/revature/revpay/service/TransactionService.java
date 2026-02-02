
package com.revature.revpay.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.dao.TransactionDAO;
import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.model.Notification;
import com.revature.revpay.model.Transaction;
import com.revature.revpay.model.Wallet;
import com.revature.revpay.util.DBConnection;

public class TransactionService {

    private TransactionDAO transactionDAO = new TransactionDAO();
    private WalletDAO walletDAO = new WalletDAO();
    private NotificationDAO notificationDAO = new NotificationDAO();

    // ================= SEND MONEY =================
    public void sendMoney(int senderId, int receiverId, double amount, String note) throws Exception {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // start transaction

            Wallet senderWallet = walletDAO.getWalletByUserId(senderId);
            Wallet receiverWallet = walletDAO.getWalletByUserId(receiverId);

            if (senderWallet == null || receiverWallet == null) {
                throw new Exception("Wallet not found");
            }

            if (senderWallet.getBalance() < amount) {
                throw new Exception("Insufficient balance");
            }

            // Debit sender
            walletDAO.updateBalance(senderId, senderWallet.getBalance() - amount);

            // Credit receiver
            walletDAO.updateBalance(receiverId, receiverWallet.getBalance() + amount);

            // Save transaction
            Transaction tx = new Transaction();
            tx.setSenderId(senderId);
            tx.setReceiverId(receiverId);
            tx.setAmount(amount);
            tx.setStatus("SUCCESS");
            tx.setNote(note);

            transactionDAO.createTransaction(tx);

            // Notifications
            notificationDAO.save(new Notification(senderId, "You sent ₹" + amount + " | " + note, "DEBIT"));
            notificationDAO.save(new Notification(receiverId, "You received ₹" + amount + " | " + note, "CREDIT"));

            // Low balance alert for sender
            if (senderWallet.getBalance() - amount < 100) {
                notificationDAO.save(new Notification(senderId,
                        "⚠️ Low wallet balance: ₹" + (senderWallet.getBalance() - amount), "ALERT"));
            }

            con.commit(); // commit transaction
        } catch (Exception e) {
            throw e; // rollback automatically if exception occurs
        }
    }

    // ================= GET ALL TRANSACTIONS =================
    public List<Transaction> getTransactionHistory(int userId) throws Exception {
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(userId);
        // Set CREDIT / DEBIT type for display
        for (Transaction tx : transactions) {
            tx.setType(tx.getReceiverId() == userId ? "CREDIT" : "DEBIT");
        }
        return transactions;
    }

    // ================= GET FILTERED TRANSACTIONS =================
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

        List<Transaction> transactions = transactionDAO.getFilteredTransactions(
                userId, type, fromDate, toDate, minAmount, maxAmount, status, search);

        // Set CREDIT / DEBIT type for display
        for (Transaction tx : transactions) {
            tx.setType(tx.getReceiverId() == userId ? "CREDIT" : "DEBIT");
        }

        return transactions;
    }
}

