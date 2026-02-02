package com.revature.revpay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revature.revpay.model.Card;
import com.revature.revpay.util.DBConnection;
import com.revature.revpay.util.EncryptionUtil;

public class CardDAO {

    // ================= ADD CARD =================
    public void addCard(Card card) throws Exception {

        String sql = "INSERT INTO cards "
                   + "(user_id, card_number, expiry, card_holder, is_default) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, card.getUserId());

            // 🔐 Encrypt card number before storing
            String encryptedCard = EncryptionUtil.encrypt(card.getCardNumber());
            ps.setString(2, encryptedCard);

            ps.setString(3, card.getExpiry());
            ps.setString(4, card.getCardHolder());
            ps.setBoolean(5, card.isDefault());

            ps.executeUpdate();
        }
    }

    // ================= GET ALL CARDS FOR USER =================
    public List<Card> getCardsByUser(int userId) throws Exception {

        List<Card> list = new ArrayList<>();

        String sql = "SELECT * FROM cards WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Card card = new Card();
                    card.setId(rs.getInt("id"));
                    card.setUserId(rs.getInt("user_id"));

                    // 🔓 Decrypt card number
                    String decrypted =
                            EncryptionUtil.decrypt(rs.getString("card_number"));

                    // 🎭 Mask card number (show only last 4 digits)
                    String masked =
                            "**** **** **** " +
                            decrypted.substring(decrypted.length() - 4);

                    card.setCardNumber(masked);
                    card.setExpiry(rs.getString("expiry"));
                    card.setCardHolder(rs.getString("card_holder"));
                    card.setDefault(rs.getBoolean("is_default"));

                    list.add(card);
                }
            }
        }
        return list;
    }

    // ================= SET DEFAULT CARD =================
    public void setDefaultCard(int userId, int cardId) throws Exception {

        Connection con = DBConnection.getConnection();

        try {
            con.setAutoCommit(false); // 🔁 Transaction start

            // ❌ Remove old default card
            String reset =
                    "UPDATE cards SET is_default=false WHERE user_id=?";
            try (PreparedStatement ps1 = con.prepareStatement(reset)) {
                ps1.setInt(1, userId);
                ps1.executeUpdate();
            }

            // ✅ Set new default card
            String set =
                    "UPDATE cards SET is_default=true WHERE id=? AND user_id=?";
            try (PreparedStatement ps2 = con.prepareStatement(set)) {
                ps2.setInt(1, cardId);
                ps2.setInt(2, userId);
                ps2.executeUpdate();
            }

            con.commit(); // ✅ Success

        } catch (Exception e) {
            con.rollback(); // ❌ Failure
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    // ================= DELETE CARD (OPTIONAL BUT PROFESSIONAL) =================
    public void deleteCard(int cardId, int userId) throws Exception {

        String sql = "DELETE FROM cards WHERE id=? AND user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cardId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }
    
    
    
 // Get card by ID
    public Card getCardById(int cardId) throws Exception {
        String sql = "SELECT * FROM cards WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cardId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt("id"));
                card.setUserId(rs.getInt("user_id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setExpiry(rs.getString("expiry"));
                card.setCardHolder(rs.getString("card_holder"));
                card.setDefault(rs.getBoolean("is_default"));
                return card;
            } else {
                return null; // card not found
            }
        }
    }   
    
}
    
