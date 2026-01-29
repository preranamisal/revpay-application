//package com.revature.revpay.service;
//
//import com.revature.revpay.dao.WalletDAO;
//import com.revature.revpay.model.Wallet;
//
//public class WalletService {
//
//    private WalletDAO walletDAO = new WalletDAO();
//
//    // Debit money
//    public void debit(int userId, double amount) throws Exception {
//
//        Wallet wallet = walletDAO.getWalletByUserId(userId);
//
//        if (wallet == null) {
//            throw new Exception("Wallet not found");
//        }
//
//        if (wallet.getBalance() < amount) {
//            throw new Exception("Insufficient balance");
//        }
//
//        double updatedBalance = wallet.getBalance() - amount;
//        walletDAO.updateBalance(userId, updatedBalance);
//    }
//
//    // Credit money
//    public void credit(int userId, double amount) throws Exception {
//
//        Wallet wallet = walletDAO.getWalletByUserId(userId);
//
//        if (wallet == null) {
//            throw new Exception("Wallet not found");
//        }
//
//        double updatedBalance = wallet.getBalance() + amount;
//        walletDAO.updateBalance(userId, updatedBalance);
//    }
//    
//    
//   
//}

package com.revature.revpay.service;

import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.model.Wallet;

public class WalletService {

    private WalletDAO walletDAO = new WalletDAO();

    // 1️⃣ Get wallet balance (USED IN CONSOLE)
    public double getBalance(int userId) throws Exception {

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found");
        }

        return wallet.getBalance();
    }

    // 2️⃣ Add money (SIMULATES BANK DEPOSIT)
    public void addMoney(int userId, double amount) throws Exception {

        if (amount <= 0) {
            throw new Exception("Amount must be greater than zero");
        }

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found");
        }

        double updatedBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, updatedBalance);
    }

    // 3️⃣ Debit money
    public void debit(int userId, double amount) throws Exception {

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found");
        }

        if (wallet.getBalance() < amount) {
            throw new Exception("Insufficient balance");
        }

        double updatedBalance = wallet.getBalance() - amount;
        walletDAO.updateBalance(userId, updatedBalance);
    }

    // 4️⃣ Credit money
    public void credit(int userId, double amount) throws Exception {

        Wallet wallet = walletDAO.getWalletByUserId(userId);

        if (wallet == null) {
            throw new Exception("Wallet not found");
        }

        double updatedBalance = wallet.getBalance() + amount;
        walletDAO.updateBalance(userId, updatedBalance);
    }
    
    public void transfer(int senderId, int receiverId, double amount, String note) throws Exception {
    	debit(senderId, amount);
    	credit(receiverId, amount);
    	// later: save transaction + notification
    	}
}
