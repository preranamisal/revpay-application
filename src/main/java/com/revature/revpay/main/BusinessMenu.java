package com.revature.revpay.main;



import com.revature.revpay.model.Invoice;
import com.revature.revpay.model.User;
import com.revature.revpay.service.InvoiceService;

import java.util.List;
import java.util.Scanner;

public class BusinessMenu {

    public static void show(User user) throws Exception {

        Scanner sc = new Scanner(System.in);
        InvoiceService invoiceService = new InvoiceService();

        while (true) {
            System.out.println("\n--- BUSINESS MENU ---");
            System.out.println("1. Create Invoice");
            System.out.println("2. View Invoices");
            System.out.println("3. Logout");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Customer Email: ");
                    String email = sc.nextLine();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();

                    invoiceService.createInvoice(user.getId(), email, amount);
                    System.out.println("Invoice created successfully!");
                    break;

                case 2:
                    List<Invoice> invoices =
                            invoiceService.getInvoicesByBusiness(user.getId());

                    if (invoices.isEmpty()) {
                        System.out.println("No invoices found");
                    } else {
                        invoices.forEach(System.out::println);
                    }
                    break;

                case 3:
                    return;
            }
        }
    }
}

