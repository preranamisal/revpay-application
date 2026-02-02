 
    package com.revature.revpay.service;

    import java.util.List;

    import com.revature.revpay.dao.CardDAO;
    import com.revature.revpay.model.Card;

    public class CardService {

        private CardDAO cardDAO = new CardDAO();
        private NotificationService notificationService = new NotificationService();

        // ================= ADD CARD =================
        public void addCard(Card card) throws Exception {

            if (card.getCardNumber() == null || !card.getCardNumber().matches("\\d{13,19}")) {
                throw new Exception("Invalid card number");
            }

            if (card.getExpiry() == null || !card.getExpiry().matches("(0[1-9]|1[0-2])/\\d{2}")) {
                throw new Exception("Invalid expiry date (MM/YY)");
            }

            List<Card> existingCards = cardDAO.getCardsByUser(card.getUserId());
            if (existingCards.isEmpty()) {
                card.setDefault(true);
            }

            cardDAO.addCard(card);

            // Send notification
            notificationService.notifyUser(
                    card.getUserId(),
                    "Card ending with " + card.getCardNumber().substring(card.getCardNumber().length() - 4) + " added",
                    "CARD"
            );
        }

        // ================= VIEW CARDS =================
        public List<Card> viewCards(int userId) throws Exception {
            return cardDAO.getCardsByUser(userId);
        }

        // ================= SET DEFAULT CARD =================
        public void makeDefault(int userId, int cardId) throws Exception {
            Card card = cardDAO.getCardById(cardId);
            if (card == null || card.getUserId() != userId) {
                throw new Exception("Card not found");
            }

            cardDAO.setDefaultCard(userId, cardId);

            // Send notification
            notificationService.notifyUser(
                    userId,
                    "Card ending with " + card.getCardNumber().substring(card.getCardNumber().length() - 4) + " set as default",
                    "CARD"
            );
        }

        // ================= DELETE CARD =================
        public void deleteCard(int userId, int cardId) throws Exception {
            Card card = cardDAO.getCardById(cardId);
            if (card == null || card.getUserId() != userId) {
                throw new Exception("Card not found");
            }

            cardDAO.deleteCard(cardId, userId);

            // Send notification
            notificationService.notifyUser(
                    userId,
                    "Card ending with " + card.getCardNumber().substring(card.getCardNumber().length() - 4) + " deleted",
                    "CARD"
            );
        }
    }
