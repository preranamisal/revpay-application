//package com.revature.revpay.main;
//
//
//
//import com.revature.revpay.model.User;
//import com.revature.revpay.service.InvoiceService;
//
//import java.util.Scanner;
//
//public class PersonalMenu {
//
//    public static void show(User user) throws Exception {
//
//        Scanner sc = new Scanner(System.in);
//        InvoiceService invoiceService = new InvoiceService();
//
//        while (true) {
//            System.out.println("\n--- PERSONAL MENU ---");
//            System.out.println("1. Pay Invoice");
//            System.out.println("2. Logout");
//
//            int choice = sc.nextInt();
//
//            switch (choice) {
//
//                case 1:
//                    System.out.print("Invoice ID: ");
//                    int invoiceId = sc.nextInt();
//
//                    invoiceService.payInvoice(invoiceId, user.getId());
//                    
//                    System.out.println("Invoice paid successfully!");
//                    break;
//            
//            
//
//                case 2:
//                    return;
//            }
//        }
//    }
//}







//import com.revature.revpay.model.User;
//import com.revature.revpay.service.InvoiceService;
//
//import java.util.Scanner;
//
//public class PersonalMenu {
//
//    public static void show(User user) {
//
//        Scanner sc = new Scanner(System.in);
//        InvoiceService invoiceService = new InvoiceService();
//
//        while (true) {
//            try {
//                System.out.println("\n--- PERSONAL MENU ---");
//                System.out.println("1. Pay Invoice");
//                System.out.println("2. Logout");
//                System.out.print("Choose option: ");
//
//                int choice = sc.nextInt();
//
//                switch (choice) {
//
//                    case 1:
//                        System.out.print("Enter Invoice ID: ");
//                        int invoiceId = sc.nextInt();
//
//                        // ✅ CORRECT CALL (pass User, NOT userId)
//                        invoiceService.payInvoice(invoiceId, user);
//
//                        System.out.println("Invoice paid successfully!");
//                        break;
//
//                    case 2:
//                        System.out.println("Logged out successfully.");
//                        return;
//
//                    default:
//                        System.out.println("Invalid choice. Try again.");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//}





//barobar aahe pn addmoney add karav lagel yat

//package com.revature.revpay.main;
//
//import java.util.List;
//import java.util.Scanner;
//
//import com.revature.revpay.model.Notification;
//import com.revature.revpay.model.User;
//import com.revature.revpay.service.InvoiceService;
//import com.revature.revpay.service.NotificationService;
//import com.revature.revpay.service.WalletService;
//
//public class PersonalMenu {
//
//    public static void show(User user) {
//
//        Scanner sc = new Scanner(System.in);
//
//        InvoiceService invoiceService = new InvoiceService();
//        WalletService walletService = new WalletService();
//        NotificationService notificationService = new NotificationService();
//
//        while (true) {
//            try {
//                System.out.println("\n===== PERSONAL MENU =====");
//                System.out.println("1. View Wallet Balance");
//                System.out.println("2. Pay Invoice");
//                System.out.println("3. View Notifications");
//                System.out.println("4. Logout");
//                System.out.print("Choose option: ");
//
//                int choice = sc.nextInt();
//
//                switch (choice) {
//
//                    case 1:
//                        double balance = walletService.getBalance(user.getId());
//                        System.out.println("💰 Wallet Balance: ₹" + balance);
//                        break;
//
//                    case 2:
//                        System.out.print("Enter Invoice ID: ");
//                        int invoiceId = sc.nextInt();
//
//                        // ✅ correct call (User object)
//                        invoiceService.payInvoice(invoiceId, user);
//
//                        System.out.println("✅ Invoice paid successfully!");
//                        break;
//
//                    case 3:
//                        System.out.println("\n🔔 Your Notifications:");
//
//                        List<Notification> list =
//                                notificationService.getUserNotifications(user.getId());
//
//                        if (list.isEmpty()) {
//                            System.out.println("No notifications");
//                        } else {
//                            for (Notification n : list) {
//                                System.out.println(
//                                        "[" + (n.isRead() ? "READ" : "UNREAD") + "] "
//                                                + n.getMessage()
//                                );
//                            }
//
//                            // mark all as read after viewing
//                            notificationService.markAllAsRead(user.getId());
//                        }
//                        break;
//
//                    case 4:
//                        System.out.println("👋 Logged out successfully.");
//                        return;
//
//                    default:
//                        System.out.println("❌ Invalid choice. Try again.");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//}







//package com.revature.revpay.main;
//
//import java.util.List;
//import java.util.Scanner;
//
//import com.revature.revpay.model.Notification;
//import com.revature.revpay.model.User;
//import com.revature.revpay.service.InvoiceService;
//import com.revature.revpay.service.NotificationService;
//import com.revature.revpay.service.WalletService;
//
//public class PersonalMenu {
//
//    public static void show(User user) {
//
//        Scanner sc = new Scanner(System.in);
//
//        InvoiceService invoiceService = new InvoiceService();
//        WalletService walletService = new WalletService();
//        NotificationService notificationService = new NotificationService();
//
//        while (true) {
//            try {
//                System.out.println("\n===== PERSONAL MENU =====");
//                System.out.println("1. View Wallet Balance");
//                System.out.println("2. Add Money to Wallet");
//                System.out.println("3. Pay Invoice");
//                System.out.println("4. View Notifications");
//                System.out.println("5. Logout");
//                System.out.print("Choose option: ");
//
//                int choice = sc.nextInt();
//
//                switch (choice) {
//
//                    case 1:
//                        double balance = walletService.getBalance(user.getId());
//                        System.out.println("💰 Wallet Balance: ₹" + balance);
//                        break;
//
//                    case 2:
//                        System.out.print("Enter amount to add: ₹");
//                        double amount = sc.nextDouble();
//
//                        walletService.addMoney(user.getId(), amount);
//                        System.out.println("✅ Money added successfully!");
//                        break;
//
//                    case 3:
//                        System.out.print("Enter Invoice ID: ");
//                        int invoiceId = sc.nextInt();
//
//                        invoiceService.payInvoice(invoiceId, user);
//                        System.out.println("✅ Invoice paid successfully!");
//                        break;
//
//                    case 4:
//                        List<Notification> list =
//                                notificationService.getUserNotifications(user.getId());
//
//                        if (list.isEmpty()) {
//                            System.out.println("No notifications");
//                        } else {
//                            System.out.println("\n🔔 Notifications:");
//                            for (Notification n : list) {
//                                System.out.println(
//                                        "[" + (n.isRead() ? "READ" : "UNREAD") + "] "
//                                                + n.getMessage()
//                                );
//                            }
//                        }
//                        break;
//
//                    case 5:
//                        System.out.println("👋 Logged out successfully.");
//                        return;
//
//                    default:
//                        System.out.println("❌ Invalid choice");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//}
//

//Email: soumya456@gmail.com
//Password: megha123







//package com.revature.revpay.main;
//
//import java.util.List;
//import java.util.Scanner;
//
//import com.revature.revpay.dao.UserDAO;
//import com.revature.revpay.model.Notification;
//import com.revature.revpay.model.User;
//import com.revature.revpay.service.InvoiceService;
//import com.revature.revpay.service.NotificationService;
//import com.revature.revpay.service.WalletService;
//
//public class PersonalMenu {
//
//    public static void show(User user) {
//
//        Scanner sc = new Scanner(System.in);
//
//        InvoiceService invoiceService = new InvoiceService();
//        WalletService walletService = new WalletService();
//        NotificationService notificationService = new NotificationService();
//        UserDAO userDAO = new UserDAO();
//
//        while (true) {
//            try {
//                System.out.println("\n===== PERSONAL MENU =====");
//                System.out.println("1. View Wallet Balance");
//                System.out.println("2. Add Money to Wallet");
//                System.out.println("3. Send Money");
//                System.out.println("4. Pay Invoice");
//                System.out.println("5. View Notifications");
//                System.out.println("6. Logout");
//                System.out.print("Choose option: ");
//
//                int choice = sc.nextInt();
//                sc.nextLine(); // clear buffer
//
//                switch (choice) {
//
//                    case 1:
//                        double balance = walletService.getBalance(user.getId());
//                        System.out.println("💰 Wallet Balance: ₹" + balance);
//                        break;
//
//                    case 2:
//                        System.out.print("Enter amount to add: ₹");
//                        double amount = sc.nextDouble();
//                        sc.nextLine();
//
//                        walletService.addMoney(user.getId(), amount);
//                        System.out.println("✅ Money added successfully!");
//                        break;
//
//                    case 3:
//                        sendMoney(user, sc, walletService, userDAO);
//                        break;
//
//                    case 4:
//                        System.out.print("Enter Invoice ID: ");
//                        int invoiceId = sc.nextInt();
//
//                        invoiceService.payInvoice(invoiceId, user);
//                        System.out.println("✅ Invoice paid successfully!");
//                        break;
//
//                    case 5:
//                        List<Notification> list =
//                                notificationService.getUserNotifications(user.getId());
//
//                        if (list.isEmpty()) {
//                            System.out.println("No notifications");
//                        } else {
//                            System.out.println("\n🔔 Notifications:");
//                            for (Notification n : list) {
//                                System.out.println(
//                                        "[" + (n.isRead() ? "READ" : "UNREAD") + "] "
//                                                + n.getMessage()
//                                );
//                            }
//                        }
//                        break;
//
//                    case 6:
//                        System.out.println("👋 Logged out successfully.");
//                        return;
//
//                    default:
//                        System.out.println("❌ Invalid choice");
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
//    }
//
//    // ================= SEND MONEY =================
//    private static void sendMoney(
//            User sender,
//            Scanner sc,
//            WalletService walletService,
//            UserDAO userDAO) throws Exception {
//
//        System.out.println("Send money by:");
//        System.out.println("1. User ID");
//        System.out.println("2. Email");
//        System.out.println("3. Phone");
//
//        int choice = sc.nextInt();
//        sc.nextLine();
//
//        User receiver = null;
//
//        switch (choice) {
//            case 1:
//                System.out.print("Enter Receiver User ID: ");
//                int receiverId = sc.nextInt();
//                receiver = userDAO.getUserById(receiverId);
//                break;
//
//            case 2:
//                System.out.print("Enter Receiver Email: ");
//                String email = sc.nextLine();
//                receiver = userDAO.getUserByEmail(email);
//                break;
//
//            case 3:
//                System.out.print("Enter Receiver Phone: ");
//                String phone = sc.nextLine();
//                receiver = userDAO.getUserByPhone(phone);
//                break;
//
//            default:
//                System.out.println("❌ Invalid option");
//                return;
//        }
//
//        if (receiver == null) {
//            System.out.println("❌ Receiver not found");
//            return;
//        }
//
//        System.out.print("Enter amount: ₹");
//        double amount = sc.nextDouble();
//        sc.nextLine();
//
//        System.out.print("Enter note (optional): ");
//        String note = sc.nextLine();
//
//        walletService.transfer(sender.getId(), receiver.getId(), amount, note);
//
//        System.out.println("✅ Money sent successfully!");
//    }
//}




package com.revature.revpay.main;

import java.util.List;
import java.util.Scanner;

import com.revature.revpay.dao.UserDAO;
import com.revature.revpay.model.MoneyRequest;
import com.revature.revpay.model.Notification;
import com.revature.revpay.model.User;
import com.revature.revpay.service.InvoiceService;
import com.revature.revpay.service.MoneyRequestService;
import com.revature.revpay.service.NotificationService;
import com.revature.revpay.service.WalletService;

public class PersonalMenu {

    public static void show(User user) {

        Scanner sc = new Scanner(System.in);

        InvoiceService invoiceService = new InvoiceService();
        WalletService walletService = new WalletService();
        NotificationService notificationService = new NotificationService();
        MoneyRequestService moneyRequestService = new MoneyRequestService();
        UserDAO userDAO = new UserDAO();

        while (true) {
            try {
                System.out.println("\n===== PERSONAL MENU =====");
                System.out.println("1. View Wallet Balance");
                System.out.println("2. Add Money to Wallet");
                System.out.println("3. Send Money");
                System.out.println("4. Request Money");
                System.out.println("5. View Money Requests");
                System.out.println("6. Pay Invoice");
                System.out.println("7. View Notifications");
                System.out.println("8. Logout");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.println("💰 Wallet Balance: ₹" +
                                walletService.getBalance(user.getId()));
                        break;

                    case 2:
                        System.out.print("Enter amount: ₹");
                        walletService.addMoney(user.getId(), sc.nextDouble());
                        sc.nextLine();
                        System.out.println("✅ Money added");
                        break;

                    case 3:
                        sendMoney(user, sc, walletService, userDAO);
                        break;

                    case 4:
                        requestMoney(user, sc, moneyRequestService);
                        break;

                    case 5:
                        viewMoneyRequests(user, sc, moneyRequestService);
                        break;

                    case 6:
                        System.out.print("Enter Invoice ID: ");
                        invoiceService.payInvoice(sc.nextInt(), user);
                        System.out.println("✅ Invoice paid");
                        break;

                    case 7:
                        List<Notification> notes =
                                notificationService.getUserNotifications(user.getId());
                        if (notes.isEmpty()) {
                            System.out.println("No notifications");
                        } else {
                            notes.forEach(n ->
                                System.out.println(
                                    "[" + (n.isRead() ? "READ" : "UNREAD") + "] "
                                    + n.getMessage()));
                        }
                        break;

                    case 8:
                        System.out.println("👋 Logged out");
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= SEND MONEY =================
    private static void sendMoney(
            User sender,
            Scanner sc,
            WalletService walletService,
            UserDAO userDAO) throws Exception {

        System.out.print("Enter Receiver User ID: ");
        int receiverId = sc.nextInt();
        sc.nextLine();

        User receiver = userDAO.getUserById(receiverId);
        if (receiver == null) {
            System.out.println("❌ Receiver not found");
            return;
        }

        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter note: ");
        String note = sc.nextLine();

        walletService.transfer(sender.getId(), receiver.getId(), amount, note);
        System.out.println("✅ Money sent successfully");
    }

    // ================= REQUEST MONEY =================
    private static void requestMoney(
            User user,
            Scanner sc,
            MoneyRequestService service) throws Exception {

        System.out.print("Enter Receiver User ID: ");
        int rid = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter amount: ₹");
        double amt = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter note: ");
        String note = sc.nextLine();

        service.requestMoney(user.getId(), rid, amt, note);
        System.out.println("✅ Money request sent");
    }

    // ================= VIEW REQUESTS =================
    private static void viewMoneyRequests(
            User user,
            Scanner sc,
            MoneyRequestService service) throws Exception {

        List<MoneyRequest> list =
                service.incomingRequests(user.getId());

        if (list.isEmpty()) {
            System.out.println("No pending requests");
            return;
        }

        for (MoneyRequest r : list) {
            System.out.println(
                "ID: " + r.getId() +
                " | From: " + r.getSenderId() +
                " | ₹" + r.getAmount() +
                " | " + r.getNote()
            );
        }

        System.out.print("Enter Request ID: ");
        int reqId = sc.nextInt();

        System.out.println("1. Accept  2. Decline");
        int action = sc.nextInt();

        if (action == 1)
            service.accept(reqId);
        else
            service.decline(reqId);

        System.out.println("✅ Request updated");
    }
}