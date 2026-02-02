
package com.revature.revpay.service;

import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.dao.CardDAO;
import com.revature.revpay.dao.NotificationDAO;
import com.revature.revpay.model.Wallet;
import com.revature.revpay.model.Card;
import com.revature.revpay.model.Notification;

public class WalletService {

    private WalletDAO walletDAO = new WalletDAO();
    private CardDAO cardDAO = new CardDAO();
    private NotificationDAO notificationDAO = new NotificationDAO();

    private static final double LOW_BALANCE_THRESHOLD = 100.0;

    // 1️⃣ Get wallet balance
    public double getBalance(int userId) throws Exception {
        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");
        return wallet.getBalance();
    }

    // 2️⃣ Add money (simulated bank deposit)
    public void addMoney(int userId, double amount) throws Exception {
        if (amount <= 0) throw new Exception("Amount must be greater than zero");

        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");

        double newBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, newBalance);
        System.out.println("✅ ₹" + amount + " added to wallet successfully.");

        notificationDAO.save(new Notification(userId, "₹" + amount + " added to wallet", "CREDIT"));
    }

    // 3️⃣ Add money from a card
    public void addMoneyFromCard(int userId, int cardId, double amount) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");

        Card card = cardDAO.getCardById(cardId);
        if (card == null || card.getUserId() != userId) throw new Exception("Card not found");

        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");

        double newBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, newBalance);
        System.out.println("✅ ₹" + amount + " added to wallet from card " + card.getCardNumber());

        notificationDAO.save(new Notification(userId,
                "₹" + amount + " added to wallet from card " + card.getCardNumber(), "CREDIT"));

        // Low balance check (optional, usually not needed when adding money)
    }

    // 4️⃣ Withdraw money to bank (simulated)
    public void withdrawToBank(int userId, double amount) throws Exception {
        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");

        if (wallet.getBalance() < amount) throw new Exception("Insufficient balance");

        double newBalance = wallet.getBalance() - amount;
        walletDAO.updateBalance(userId, newBalance);
        System.out.println("✅ ₹" + amount + " withdrawn to bank account (simulated)");

        notificationDAO.save(new Notification(userId, "₹" + amount + " withdrawn to bank (simulated)", "DEBIT"));

        // Low balance alert
        checkLowBalance(userId, newBalance);
    }

    // 5️⃣ Debit money
    public void debit(int userId, double amount) throws Exception {
        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");

        if (wallet.getBalance() < amount) throw new Exception("Insufficient balance");

        double newBalance = wallet.getBalance() - amount;
        walletDAO.updateBalance(userId, newBalance);

        // Low balance alert
        checkLowBalance(userId, newBalance);
    }

    // 6️⃣ Credit money
    public void credit(int userId, double amount) throws Exception {
        Wallet wallet = walletDAO.getWalletByUserId(userId);
        if (wallet == null) throw new Exception("Wallet not found");

        double newBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, newBalance);
    }

    // 7️⃣ Transfer money between users
    public void transfer(int senderId, int receiverId, double amount, String note) throws Exception {
        debit(senderId, amount);
        credit(receiverId, amount);

        // Notifications
        notificationDAO.save(new Notification(senderId,
                "You sent ₹" + amount + " | " + note, "DEBIT"));
        notificationDAO.save(new Notification(receiverId,
                "You received ₹" + amount + " | " + note, "CREDIT"));
    }

    // ===== Helper method for low balance alerts =====
    private void checkLowBalance(int userId, double balance) throws Exception {
        if (balance < LOW_BALANCE_THRESHOLD) {
            notificationDAO.save(new Notification(userId,
                    "⚠️ Low wallet balance: ₹" + balance, "ALERT"));
        }
    }
}
