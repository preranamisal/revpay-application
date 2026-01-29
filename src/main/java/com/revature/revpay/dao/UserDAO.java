//package com.revature.revpay.dao;
//
//import java.sql.*;
//
//import com.revature.revpay.model.User;
//import com.revature.revpay.util.DBConnection;
//
//public class UserDAO {
//
//    // 1️⃣ Save user (registration)
//    public void save(User user) throws Exception {
//
//        String sql = "INSERT INTO users (name, email, phone, password, pin, role) VALUES (?,?,?,?,?,?)";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, user.getName());
//            ps.setString(2, user.getEmail());
//            ps.setString(3, user.getPhone());
//            ps.setString(4, user.getPassword());
//            ps.setString(5, user.getPin());
//            ps.setString(6, user.getRole());
//
//            ps.executeUpdate();
//        }
//    }
//
//    // 2️⃣ Get user by email (LOGIN)
//    public User getUserByEmail(String email) throws Exception {
//
//        String sql = "SELECT * FROM users WHERE email = ?";
//        User user = null;
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                user = new User();
//                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setPhone(rs.getString("phone"));
//                user.setPassword(rs.getString("password"));
//                user.setPin(rs.getString("pin"));
//                user.setRole(rs.getString("role"));
//            }
//        }
//        return user;
//    }
//}


//here i comment because to add by email and phone
//package com.revature.revpay.dao;
//
//import java.sql.*;
//
//import com.revature.revpay.model.User;
//import com.revature.revpay.util.DBConnection;
//
//public class UserDAO {
//
//    // 1️⃣ Save user (registration) - RETURN GENERATED USER ID
//    public int save(User user) throws Exception {
//
//        String sql = "INSERT INTO users (name, email, phone, password, pin, role) VALUES (?,?,?,?,?,?)";
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps =
//                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setString(1, user.getName());
//            ps.setString(2, user.getEmail());
//            ps.setString(3, user.getPhone());
//            ps.setString(4, user.getPassword());
//            ps.setString(5, user.getPin());
//            ps.setString(6, user.getRole());
//
//            ps.executeUpdate();
//
//            // ✅ Get auto-generated user ID
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        }
//
//        throw new Exception("User registration failed");
//    }
//
//    // 2️⃣ Get user by email (LOGIN)
//    public User getUserByEmail(String email) throws Exception {
//
//        String sql = "SELECT * FROM users WHERE email = ?";
//        User user = null;
//
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, email);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                user = new User();
//                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
//                user.setEmail(rs.getString("email"));
//                user.setPhone(rs.getString("phone"));
//                user.setPassword(rs.getString("password"));
//                user.setPin(rs.getString("pin"));
//                user.setRole(rs.getString("role"));
//            }
//        }
//        return user;
//    }
//}




package com.revature.revpay.dao;

import java.sql.*;

import com.revature.revpay.model.User;
import com.revature.revpay.util.DBConnection;

public class UserDAO {

    // 1️⃣ REGISTER USER (WITH SECURITY QUESTION)
    public int save(User user) throws Exception {

    	String sql =
    		    "INSERT INTO users " +
    		    "(name, email, phone, password, pin, role, security_question, security_answer) " +
    		    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPin());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getSecurityQuestion());
            ps.setString(8, user.getSecurityAnswer());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new Exception("❌ User registration failed");
    }

    // 2️⃣ GET USER BY EMAIL (LOGIN + SEND MONEY)
    public User getUserByEmail(String email) throws Exception {

        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    // 3️⃣ GET USER BY PHONE (SEND MONEY)
    public User getUserByPhone(String phone) throws Exception {

        String sql = "SELECT * FROM users WHERE phone = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }

    // 🔁 COMMON MAPPER (VERY IMPORTANT)
    private User extractUser(ResultSet rs) throws Exception {

        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setPassword(rs.getString("password"));
        user.setPin(rs.getString("pin"));
        user.setRole(rs.getString("role"));
        user.setSecurityQuestion(rs.getString("security_question"));
        user.setSecurityAnswer(rs.getString("security_answer"));

        return user;
    }
    
 // 4️⃣ GET USER BY ID (SEND MONEY)
    public User getUserById(int id) throws Exception {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        }
        return null;
    }
}