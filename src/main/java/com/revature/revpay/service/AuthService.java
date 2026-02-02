
package com.revature.revpay.service;

import com.revature.revpay.dao.UserDAO;
import com.revature.revpay.dao.WalletDAO;
import com.revature.revpay.model.User;
import com.revature.revpay.util.PasswordUtil;

public class AuthService {

    private UserDAO userDAO = new UserDAO();
    private WalletDAO walletDAO = new WalletDAO();

    // 1️⃣ REGISTER USER
    public void register(User user) throws Exception {

        // 🔒 Check duplicate email
        User existing = userDAO.getUserByEmail(user.getEmail());
        if (existing != null) {
            throw new Exception("Email already registered");
        }

        // 🔐 Hash password & PIN BEFORE saving
        user.setPassword(PasswordUtil.hash(user.getPassword()));
        user.setPin(PasswordUtil.hash(user.getPin()));

        // 💾 Save user & get userId
        int userId = userDAO.save(user);

        // 💰 Create wallet for user
        walletDAO.createWallet(userId);
    }

    // 2️⃣ LOGIN USER
    public User login(String email, String password) throws Exception {

        User user = userDAO.getUserByEmail(email);

        if (user != null &&
            PasswordUtil.verify(password, user.getPassword())) {
            return user;
        }

        return null;
    }
}