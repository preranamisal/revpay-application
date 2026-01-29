package com.revature.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revature.revpay.model.User;
import com.revature.revpay.util.DBConnection;

public class AuthDAO {

    // REGISTER USER
    public void register(User user) throws Exception {

        String sql = "INSERT INTO users " +
                "(name, email, phone, password, pin, role, security_question, security_answer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPin());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getSecurityQuestion());
            ps.setString(8, user.getSecurityAnswer());

            ps.executeUpdate();
        }
    }

    // LOGIN USER
//    public User login(String email, String password) throws Exception {
//
//        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, email);
//            ps.setString(2, password);
//
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                User user = new User();
//                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setPhone(rs.getString("phone"));
//                user.setRole(rs.getString("role"));
//                return user;
//            }
//        }
//        return null;
//    }
    
 // AuthDAO.java
    public User getUserByEmail(String email) throws Exception {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password")); // hashed
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
}
