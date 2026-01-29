//package com.revature.revpay.main;
//
//import java.util.Scanner;
//
//import com.revature.revpay.main.BusinessMenu;
//import com.revature.revpay.main.PersonalMenu;
//
//
//import com.revature.revpay.model.User;
//import com.revature.revpay.service.AuthService;
//import com.revature.revpay.service.NotificationService;
//
//public class RevPayApp {
//
//    private static Scanner sc = new Scanner(System.in);
//    private static AuthService authService = new AuthService();
//
//    public static void main(String[] args) {
//
//        while (true) {
//            System.out.println("\n===== REV PAY =====");
//            System.out.println("1. Register");
//            System.out.println("2. Login");
//            System.out.println("3. Exit");
//
//            int choice = sc.nextInt();
//
//            try {
//                switch (choice) {
//                    case 1:
//                        register();
//                        break;
//                    case 2:
//                        login();
//                        break;
//                    case 3:
//                        System.out.println("Thank you for using RevPay!");
//                        System.exit(0);
//                }
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//
//    // ---------------- REGISTER ----------------
//    private static void register() throws Exception {
//
//        sc.nextLine(); // clear buffer
//
//        System.out.print("Name: ");
//        String name = sc.nextLine();
//
//        System.out.print("Email: ");
//        String email = sc.nextLine();
//
//        System.out.print("Phone: ");
//        String phone = sc.nextLine();
//
//        System.out.print("Password: ");
//        String password = sc.nextLine();
//
//        System.out.print("PIN: ");
//        String pin = sc.nextLine();
//
//        System.out.print("Account Type (PERSONAL / BUSINESS): ");
//        String role = sc.nextLine().toUpperCase();
//
//        User user = new User(name, email, phone, password, pin, role);
//        authService.register(user);
//
//        System.out.println("Registration successful!");
//    }
    
    // old logic for login

    // ---------------- LOGIN ----------------
//    private static void login() throws Exception {
//
//        sc.nextLine();
//
//        System.out.print("Email: ");
//        String email = sc.nextLine();
//
//        System.out.print("Password: ");
//        String password = sc.nextLine();
//
//        User user = authService.login(email, password);
//
//        if (user == null) {
//            System.out.println("Invalid credentials");
//            return;
//        }
//
//        System.out.println("Welcome " + user.getName());
//        System.out.println("DEBUG User ID = " + user.getId());
//
//        if ("BUSINESS".equalsIgnoreCase(user.getRole())) {
//            BusinessMenu.show(user);
//        } else {
//            PersonalMenu.show(user);
//        }
//    }
//}
    
    //another logic for login
//    private static void login() throws Exception {
//
//        sc.nextLine(); // clear buffer
//
//        System.out.print("Email: ");
//        String email = sc.nextLine();
//
//        System.out.print("Password: ");
//        String password = sc.nextLine();
//
//        User user = authService.login(email, password);
//
//        if (user == null) {
//            System.out.println("❌ Invalid credentials");
//            return;
//        }
//
//        System.out.println("\n✅ Login successful!");
//        System.out.println("Welcome " + user.getName());
//
//        // 🔔 SHOW UNREAD NOTIFICATION COUNT
//        NotificationService notificationService = new NotificationService();
//        int unreadCount = notificationService.getUnreadCount(user.getId());
//
//        if (unreadCount > 0) {
//            System.out.println("🔔 You have " + unreadCount + " unread notifications");
//        }
//
//        // 🔀 ROUTE BASED ON ROLE
//        if ("BUSINESS".equalsIgnoreCase(user.getRole())) {
//            BusinessMenu.show(user);
//        } else {
//            PersonalMenu.show(user);
//        }
//    }
//}











package com.revature.revpay.main;

import java.util.Scanner;

import com.revature.revpay.model.User;
import com.revature.revpay.service.AuthService;
import com.revature.revpay.service.NotificationService;

public class RevPayApp {

    private static Scanner sc = new Scanner(System.in);
    private static AuthService authService = new AuthService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== REV PAY =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Thank you for using RevPay!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------- REGISTER ----------------
    private static void register() throws Exception {

        sc.nextLine(); // clear buffer

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("PIN: ");
        String pin = sc.nextLine();

        System.out.print("Account Type (PERSONAL / BUSINESS): ");
        String role = sc.nextLine().toUpperCase();
        
//        System.out.print("Security Question: What is your mother's name? ");
//        String securityQuestion = sc.nextLine();
//
//        System.out.print("Security Answer: ");
//        String securityAnswer = sc.nextLine();
        


     // ✅ FIXED SECURITY QUESTION (SYSTEM DEFINED)
     String securityQuestion = "What is your mother's name?";


     // ✅ ONLY ASK ANSWER
     System.out.print("Mother's Name (Security Answer): ");
     String securityAnswer = sc.nextLine();

        User user = new User(name, email, phone, password, pin, role,securityQuestion,securityAnswer);
        authService.register(user);

        System.out.println("✅ Registration successful!");
    }

    // ---------------- LOGIN ----------------
    private static void login() throws Exception {

        sc.nextLine(); // clear buffer

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("❌ Invalid credentials");
            return;
        }

        System.out.println("\n✅ Login successful!");
        System.out.println("Welcome " + user.getName());

        // 🔔 SHOW UNREAD NOTIFICATION COUNT
        NotificationService notificationService = new NotificationService();
        int unreadCount = notificationService.getUnreadCount(user.getId());

        if (unreadCount > 0) {
            System.out.println("🔔 You have " + unreadCount + " unread notifications");
        }

        // 🔀 ROUTE BASED ON ROLE
        if ("BUSINESS".equalsIgnoreCase(user.getRole())) {
            BusinessMenu.show(user);
        } else {
            PersonalMenu.show(user);
        }
    }
}