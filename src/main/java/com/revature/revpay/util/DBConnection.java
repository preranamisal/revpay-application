package com.revature.revpay.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
		public static void main(String[] args) {
			getConnection();
		}

	    private static final String URL =
	        "jdbc:mysql://localhost:3306/revpay_db";
	    private static final String USER = "root";
	    private static final String PASSWORD = "pass@123"; // change if needed

	    public static Connection getConnection() {
	        Connection con = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("driver is loaded");
	            con = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("connection is created");
	        } 
	        
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return con;
	    }
	}




