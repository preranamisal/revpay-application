
package com.revature.revpay.main;

import java.util.List;
import java.util.Scanner;

import com.revature.revpay.dao.UserDAO;
import com.revature.revpay.model.*;
import com.revature.revpay.service.*;

public class PersonalMenu {

    public static void show(User user) {
        Scanner sc = new Scanner(System.in);

        WalletService walletService = new WalletService();
        NotificationService notificationService = new NotificationService();
        MoneyRequestService moneyRequestService = new MoneyRequestService();
        TransactionService transactionService = new TransactionService();
        InvoiceService invoiceService = new InvoiceService();
        UserDAO userDAO = new UserDAO();
        CardService cardService = new CardService();

        while (true) {
            try {
                // Show unread notifications count
                int unread = notificationService.getUnreadCount(user.getId());
                if (unread > 0) {
                    System.out.println("🔔 You have " + unread + " unread notifications!");
                }

                System.out.println("\n===== PERSONAL MENU =====");
                System.out.println("1. View Wallet Balance");
                System.out.println("2. Add Money to Wallet (Bank)");
                System.out.println("3. Add Money from Card");
                System.out.println("4. Withdraw to Bank");
                System.out.println("5. Send Money");
                System.out.println("6. Request Money");
                System.out.println("7. View Money Requests");
                System.out.println("8. Pay Invoice");
                System.out.println("9. View Notifications");
                System.out.println("10. Manage Cards");
                System.out.println("11. View Transaction History");
                System.out.println("12. Logout");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine(); // clear newline

                switch (choice) {
                    case 1:
                        System.out.println("💰 Wallet Balance: ₹" + walletService.getBalance(user.getId()));
                        break;

                    case 2:
                        System.out.print("Enter amount to add: ₹");
                        double amtBank = sc.nextDouble();
                        sc.nextLine();
                        walletService.addMoney(user.getId(), amtBank);

                        // Notify user
                        notificationService.notifyUser(user.getId(),
                                "₹" + amtBank + " added to wallet", "CREDIT");
                        break;

                    case 3:
                        List<Card> cards = cardService.viewCards(user.getId());
                        if (cards.isEmpty()) {
                            System.out.println("No cards found. Please add a card first.");
                            break;
                        }
                        System.out.println("Available Cards:");
                        for (Card c : cards) {
                            System.out.println("ID: " + c.getId() + " | " + c.getCardNumber() +
                                    (c.isDefault() ? " | DEFAULT" : ""));
                        }
                        System.out.print("Enter Card ID to use: ");
                        int cardId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter amount to add from card: ₹");
                        double amtCard = sc.nextDouble();
                        sc.nextLine();
                        walletService.addMoneyFromCard(user.getId(), cardId, amtCard);

                        // Notify user
                        notificationService.notifyUser(user.getId(),
                                "₹" + amtCard + " added to wallet from card " + cardId, "CREDIT");
                        break;

                    case 4:
                        System.out.print("Enter amount to withdraw to bank: ₹");
                        double amtWithdraw = sc.nextDouble();
                        sc.nextLine();
                        walletService.withdrawToBank(user.getId(), amtWithdraw);

                        // Notification inside WalletService handles low balance alert automatically
                        break;

                    case 5:
                        sendMoney(user, sc, walletService, userDAO, notificationService);
                        break;

                    case 6:
                        requestMoney(user, sc, moneyRequestService, notificationService);
                        break;

                    case 7:
                        viewMoneyRequests(user, sc, moneyRequestService, notificationService);
                        break;

                    case 8:
                        System.out.print("Enter Invoice ID: ");
                        int invoiceId = sc.nextInt();
                        sc.nextLine();
                        invoiceService.payInvoice(invoiceId, user);
                        System.out.println("✅ Invoice paid");
                        notificationService.notifyUser(user.getId(),
                                "Invoice ID " + invoiceId + " paid", "TRANSACTION");
                        break;

                    case 9:
                        viewNotifications(user, notificationService);
                        break;

                    case 10:
                        manageCards(user, sc, cardService, notificationService);
                        break;

                    case 11:
                        showTransactionHistory(user, transactionService);
                        break;

                    case 12:
                        System.out.println("👋 Logged out");
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    // ================= SEND MONEY =================
    private static void sendMoney(User sender, Scanner sc, WalletService walletService, UserDAO userDAO,
                                  NotificationService notificationService) throws Exception {

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

        // Notifications
        notificationService.notifyUser(sender.getId(),
                "You sent ₹" + amount + " to " + receiver.getName() + " | " + note, "DEBIT");
        notificationService.notifyUser(receiver.getId(),
                "You received ₹" + amount + " from " + sender.getName() + " | " + note, "CREDIT");
    }

    // ================= REQUEST MONEY =================
    private static void requestMoney(User user, Scanner sc, MoneyRequestService service,
                                     NotificationService notificationService) throws Exception {

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

        notificationService.notifyUser(rid,
                "You received a money request of ₹" + amt + " from " + user.getName() + " | " + note,
                "REQUEST");
    }

    // ================= VIEW MONEY REQUESTS =================
    private static void viewMoneyRequests(User user, Scanner sc, MoneyRequestService service,
                                          NotificationService notificationService) throws Exception {

        List<MoneyRequest> requests = service.incomingRequests(user.getId());
        if (requests.isEmpty()) {
            System.out.println("No pending requests");
            return;
        }

        for (MoneyRequest r : requests) {
            System.out.println("ID: " + r.getId() + " | From: " + r.getSenderId() +
                    " | ₹" + r.getAmount() + " | " + r.getNote());
        }

        System.out.print("Enter Request ID to process: ");
        int reqId = sc.nextInt();
        System.out.println("1. Accept  2. Decline");
        int action = sc.nextInt();
        sc.nextLine();

        MoneyRequest mr = service.getRequestById(reqId);

        if (action == 1) {
            service.accept(reqId);
            System.out.println("✅ Request accepted");

            // Notify sender
            notificationService.notifyUser(mr.getSenderId(),
                    user.getName() + " accepted your request of ₹" + mr.getAmount(), "CREDIT");

        } else {
            service.decline(reqId);
            System.out.println("❌ Request declined");

            notificationService.notifyUser(mr.getSenderId(),
                    user.getName() + " declined your request of ₹" + mr.getAmount(), "ALERT");
        }
    }

    // ================= VIEW NOTIFICATIONS =================
    private static void viewNotifications(User user, NotificationService service) throws Exception {
        List<Notification> notes = service.getUserNotifications(user.getId());
        if (notes.isEmpty()) {
            System.out.println("No notifications");
            return;
        }

        for (Notification n : notes) {
            System.out.println("[" + (n.isRead() ? "READ" : "UNREAD") + "] " + n.getMessage());
        }

        System.out.print("Mark all as read? (y/n): ");
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            service.markAllAsRead(user.getId());
            System.out.println("✅ All notifications marked as read");
        }
    }

    // ================= CARD MANAGEMENT =================
    private static void manageCards(User user, Scanner sc, CardService cardService,
                                    NotificationService notificationService) {

        while (true) {
            try {
                System.out.println("\n--- CARD MANAGEMENT ---");
                System.out.println("1. Add Card");
                System.out.println("2. View Cards");
                System.out.println("3. Set Default Card");
                System.out.println("4. Delete Card");
                System.out.println("5. Back");
                System.out.print("Choose option: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        Card card = new Card();
                        card.setUserId(user.getId());

                        System.out.print("Enter Card Number: ");
                        card.setCardNumber(sc.nextLine());

                        System.out.print("Enter Expiry (MM/YY): ");
                        card.setExpiry(sc.nextLine());

                        System.out.print("Enter Card Holder Name: ");
                        card.setCardHolder(sc.nextLine());

                        card.setDefault(false);
                        cardService.addCard(card);
                        System.out.println("✅ Card added");

                        notificationService.notifyUser(user.getId(),
                                "New card added: " + card.getCardNumber(), "CARD");
                        break;

                    case 2:
                        List<Card> cardsList = cardService.viewCards(user.getId());
                        if (cardsList.isEmpty()) {
                            System.out.println("No cards found");
                        } else {
                            for (Card c : cardsList) {
                                System.out.println("ID: " + c.getId() + " | " + c.getCardNumber() +
                                        (c.isDefault() ? " | DEFAULT" : ""));
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Enter Card ID to set as default: ");
                        int defaultId = sc.nextInt();
                        sc.nextLine();
                        cardService.makeDefault(user.getId(), defaultId);
                        System.out.println("✅ Default card set");

                        notificationService.notifyUser(user.getId(),
                                "Card ID " + defaultId + " set as default", "CARD");
                        break;

                    case 4:
                        System.out.print("Enter Card ID to delete: ");
                        int delId = sc.nextInt();
                        sc.nextLine();
                        cardService.deleteCard(user.getId(), delId);
                        System.out.println("✅ Card deleted");

                        notificationService.notifyUser(user.getId(),
                                "Card ID " + delId + " deleted", "CARD");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("❌ Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    // ================= TRANSACTION HISTORY =================
    private static void showTransactionHistory(User user, TransactionService service) {
        try {
            List<Transaction> transactions = service.getFilteredTransactions(user.getId(), null, null, null, null, null, null, null);

            if (transactions.isEmpty()) {
                System.out.println("No transactions found");
                return;
            }

            for (Transaction tx : transactions) {
                String type = tx.getReceiverId() == user.getId() ? "CREDIT" : "DEBIT";
                tx.setType(type);

                System.out.println("ID: " + tx.getId() +
                        " | " + tx.getType() +
                        " | ₹" + tx.getAmount() +
                        " | From: " + tx.getSenderId() +
                        " | To: " + tx.getReceiverId() +
                        " | Status: " + tx.getStatus() +
                        " | Note: " + tx.getNote() +
                        " | Date: " + tx.getCreatedAt());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
