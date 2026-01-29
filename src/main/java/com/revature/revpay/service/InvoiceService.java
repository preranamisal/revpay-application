//package com.revature.revpay.service;
//
//
//import java.util.List;
//
//import com.revature.revpay.dao.InvoiceDAO;
//import com.revature.revpay.model.Invoice;
//import com.revature.revpay.model.User;
//
//public class InvoiceService {
//
//    private InvoiceDAO invoiceDAO = new InvoiceDAO();
//
//    private WalletService walletService = new WalletService();
//    private TransactionService transactionService = new TransactionService();
//    private NotificationService notificationService = new NotificationService();
//
//    // 1️⃣ Create Invoice (Business user)
//    public void createInvoice(int businessId, String customerEmail, double amount) throws Exception {
//
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Invoice amount must be greater than zero");
//        }
//
//        Invoice invoice = new Invoice();
//        invoice.setBusinessId(businessId);
//        invoice.setCustomerEmail(customerEmail);
//        invoice.setAmount(amount);
//        invoice.setStatus("UNPAID");
//
//        invoiceDAO.createInvoice(invoice);
//    }
//
//    // 2️⃣ View all invoices for a business
//    public List<Invoice> getInvoicesByBusiness(int businessId) throws Exception {
//        return invoiceDAO.getInvoicesByBusinessId(businessId);
//    }
//
//    // 3️⃣ Pay Invoice (FULL PAYMENT FLOW)   
//    public void payInvoice(int invoiceId, int customerId) throws Exception {
//
//        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId);
//
//        if (invoice == null) {
//            throw new Exception("Invoice not found");
//        }
//
//        if ("PAID".equalsIgnoreCase(invoice.getStatus())) {
//            throw new Exception("Invoice already paid");
//        }
//
//        double amount = invoice.getAmount();
//        int businessId = invoice.getBusinessId();
//
//        // 🔐 STEP 1: Debit customer wallet
//        walletService.debit(customerId, amount);
//
//        // 🔐 STEP 2: Credit business wallet
//        walletService.credit(businessId, amount);
//
//        // 📜 STEP 3: Create transaction record
//        transactionService.sendMoney(
//                customerId,
//                businessId,
//                amount,
//                "Invoice Payment (Invoice ID: " + invoiceId + ")"
//        );
//
//        // 🧾 STEP 4: Mark invoice as PAID
//        invoiceDAO.updateInvoiceStatus(invoiceId, "PAID");
//
//        // 🔔 STEP 5: Send notifications
//        notificationService.notifyUser(
//                customerId,
//        		
//                "You paid ₹" + amount + " for Invoice #" + invoiceId
//        );
//
//        notificationService.notifyUser(
//                businessId,
//                "Invoice #" + invoiceId + " has been paid. Amount received: ₹" + amount
//        );
//    }
//
//    // 4️⃣ Mark invoice unpaid (admin / rollback)
//    public void markInvoiceUnpaid(int invoiceId) throws Exception {
//        invoiceDAO.updateInvoiceStatus(invoiceId, "UNPAID");
//    }
//}



package com.revature.revpay.service;

import java.util.List;

import com.revature.revpay.dao.InvoiceDAO;
import com.revature.revpay.model.Invoice;
import com.revature.revpay.model.User;

public class InvoiceService {

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private WalletService walletService = new WalletService();
    private TransactionService transactionService = new TransactionService();
    private NotificationService notificationService = new NotificationService();

    // 1️⃣ Create Invoice (Business user)
    public void createInvoice(int businessId, String customerEmail, double amount) throws Exception {

        if (amount <= 0) {
            throw new IllegalArgumentException("Invoice amount must be greater than zero");
        }

        Invoice invoice = new Invoice();
        invoice.setBusinessId(businessId);
        invoice.setCustomerEmail(customerEmail);
        invoice.setAmount(amount);
        invoice.setStatus("UNPAID");

        invoiceDAO.createInvoice(invoice);
    }

    // 2️⃣ View all invoices for a business
    public List<Invoice> getInvoicesByBusiness(int businessId) throws Exception {
        return invoiceDAO.getInvoicesByBusinessId(businessId);
    }

    // 3️⃣ Pay Invoice (FULL, CORRECT PAYMENT FLOW)
    public void payInvoice(int invoiceId, User customer) throws Exception {

        // Fetch invoice
        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId);

        if (invoice == null) {
            throw new Exception("Invoice not found");
        }

        if ("PAID".equalsIgnoreCase(invoice.getStatus())) {
            throw new Exception("Invoice already paid");
        }

        double amount = invoice.getAmount();
        int businessId = invoice.getBusinessId();
        int customerId = customer.getId();

        // 🔐 STEP 1: Debit CUSTOMER wallet (logged-in user)
        walletService.debit(customerId, amount);

        // 🔐 STEP 2: Credit BUSINESS wallet
        walletService.credit(businessId, amount);

        // 📜 STEP 3: Create transaction record
        transactionService.sendMoney(
                customerId,
                businessId,
                amount,
                "Invoice Payment (Invoice ID: " + invoiceId + ")"
        );

        // 🧾 STEP 4: Mark invoice as PAID
        invoiceDAO.updateInvoiceStatus(invoiceId, "PAID");

        // 🔔 STEP 5: Send notifications
//        notificationService.notifyUser(
//                customerId,
//                "You paid ₹" + amount + " for Invoice #" + invoiceId
//        );
//
//        notificationService.notifyUser(
//                businessId,
//                "Invoice #" + invoiceId + " has been paid. Amount received: ₹" + amount
//        );
     // 🔔 STEP 5: Send notifications
        notificationService.notifyUser(
                customerId,
                "You paid ₹" + amount + " for Invoice #" + invoiceId,
                "DEBIT"
        );

        notificationService.notifyUser(
                businessId,
                "Invoice #" + invoiceId + " has been paid. Amount received: ₹" + amount,
                "CREDIT"
        );
    }

    // 4️⃣ Mark invoice unpaid (admin / rollback)
    public void markInvoiceUnpaid(int invoiceId) throws Exception {
        invoiceDAO.updateInvoiceStatus(invoiceId, "UNPAID");
    }
}