
package com.revature.revpay.dao;

import java.sql.*;

import com.revature.revpay.model.Wallet;
import com.revature.revpay.util.DBConnection;

public class WalletDAO {

    // 1️⃣ Create wallet for new user (CALLED DURING REGISTRATION)
    public void createWallet(int userId) throws Exception {

        String sql = "INSERT INTO wallet (user_id, balance) VALUES (?, 0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }

    // 2️⃣ Get wallet by user ID
    public Wallet getWalletByUserId(int userId) throws Exception {

        String sql = "SELECT * FROM wallet WHERE user_id = ?";
        Wallet wallet = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                wallet = new Wallet();
                wallet.setUserId(rs.getInt("user_id"));
                wallet.setBalance(rs.getDouble("balance"));
            }
        }
        return wallet;
    }

    // 3️⃣ Update wallet balance
    public void updateBalance(int userId, double newBalance) throws Exception {

        String sql = "UPDATE wallet SET balance = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }
    
    public void transfer(int senderId, int receiverId, double amount) throws Exception {

        Connection con = DBConnection.getConnection();
        try {
            con.setAutoCommit(false);

            // deduct sender
            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE wallets SET balance = balance - ? WHERE user_id = ?");
            ps1.setDouble(1, amount);
            ps1.setInt(2, senderId);
            ps1.executeUpdate();

            // add receiver
            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE wallets SET balance = balance + ? WHERE user_id = ?");
            ps2.setDouble(1, amount);
            ps2.setInt(2, receiverId);
            ps2.executeUpdate();

            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
    
   
}