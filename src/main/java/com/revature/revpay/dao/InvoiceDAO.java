package com.revature.revpay.dao;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Invoice;
import com.revature.revpay.util.DBConnection;

public class InvoiceDAO {

    // 1️⃣ Create Invoice
    public void createInvoice(Invoice invoice) throws Exception {

        String sql = "INSERT INTO invoices (business_id, customer_email, amount, status) VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoice.getBusinessId());
            ps.setString(2, invoice.getCustomerEmail());
            ps.setDouble(3, invoice.getAmount());
            ps.setString(4, invoice.getStatus());

            ps.executeUpdate();
        }
    }

    // 2️⃣ Get all invoices for a business user
    public List<Invoice> getInvoicesByBusinessId(int businessId) throws Exception {

        List<Invoice> invoiceList = new ArrayList<>();

        String sql = "SELECT * FROM invoices WHERE business_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, businessId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setBusinessId(rs.getInt("business_id"));
                invoice.setCustomerEmail(rs.getString("customer_email"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setStatus(rs.getString("status"));

                invoiceList.add(invoice);
            }
        }
        return invoiceList;
    }

    // 3️⃣ Update invoice status (PAID / UNPAID)
    public void updateInvoiceStatus(int invoiceId, String status) throws Exception {

        String sql = "UPDATE invoices SET status = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, invoiceId);

            ps.executeUpdate();
        }
    }

    // 4️⃣ Get invoice by ID (used during payment)
    public Invoice getInvoiceById(int invoiceId) throws Exception {

        String sql = "SELECT * FROM invoices WHERE id = ?";
        Invoice invoice = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setBusinessId(rs.getInt("business_id"));
                invoice.setCustomerEmail(rs.getString("customer_email"));
                invoice.setAmount(rs.getDouble("amount"));
                invoice.setStatus(rs.getString("status"));
            }
        }
        return invoice;
    }
}
