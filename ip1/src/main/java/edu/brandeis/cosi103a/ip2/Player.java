package edu.brandeis.cosi103a.ip2;

import java.util.*;

/**
 * Represents a player in Automation: The Game.
 * Manages the player's deck (draw pile, hand, discard pile) and purchased cards.
 */
public class Player {
    private final String name;
    private final Deque<Card> drawPile;      // Cards to be drawn
    private final List<Card> hand;            // Cards in current hand
    private final List<Card> playedCards;    // Cards played this turn
    private final Deque<Card> discardPile;   // Discarded cards (will be reshuffled)
    private final List<Card> purchasedCards; // All cards purchased during the game
    
    /**
     * Creates a new player with the given name.
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        this.drawPile = new LinkedList<>();
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.discardPile = new LinkedList<>();
        this.purchasedCards = new ArrayList<>();
    }
    
    /**
     * Gets the player's name.
     * @return the player's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a card to the player's purchased cards list.
     * The card goes into the discard pile and will be shuffled into the draw pile.
     * @param card the card to purchase
     */
    public void purchaseCard(Card card) {
        purchasedCards.add(card);
        discardPile.addLast(card);
    }
    
    /**
     * Adds a starting card to the draw pile (for initial deck setup).
     * @param card the card to add
     */
    public void addStartingCard(Card card) {
        purchasedCards.add(card);
        drawPile.addLast(card);
    }
    
    /**
     * Draws cards from the draw pile into the hand.
     * If the draw pile is empty, reshuffles the discard pile into the draw pile.
     * @param count the number of cards to draw
     */
    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            // If draw pile is empty, reshuffle discard pile
            if (drawPile.isEmpty()) {
                if (discardPile.isEmpty()) {
                    // No more cards available
                    break;
                }
                reshuffleDiscardPile();
            }
            Card card = drawPile.removeFirst();
            hand.add(card);
        }
    }
    
    /**
     * Reshuffles the discard pile into the draw pile.
     */
    private void reshuffleDiscardPile() {
        List<Card> cardsToReshuffle = new ArrayList<>(discardPile);
        Collections.shuffle(cardsToReshuffle);
        drawPile.clear();
        drawPile.addAll(cardsToReshuffle);
        discardPile.clear();
    }
    
    /**
     * Shuffles the current draw pile.
     */
    public void shuffleDrawPile() {
        List<Card> cards = new ArrayList<>(drawPile);
        Collections.shuffle(cards);
        drawPile.clear();
        drawPile.addAll(cards);
    }
    
    /**
     * Gets the player's current hand.
     * @return a copy of the hand list
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    
    /**
     * Gets the number of cards in the player's hand.
     * @return the hand size
     */
    public int getHandSize() {
        return hand.size();
    }
    
    /**
     * Gets the played cards for this turn.
     * @return a copy of the played cards list
     */
    public List<Card> getPlayedCards() {
        return new ArrayList<>(playedCards);
    }
    
    /**
     * Gets all cryptocurrency cards from the current hand.
     * @return a list of cryptocurrency cards in hand
     */
    public List<Card> getCryptocurrencyCardsFromHand() {
        List<Card> crypto = new ArrayList<>();
        for (Card card : hand) {
            if (card.isCryptocurrency()) {
                crypto.add(card);
            }
        }
        return crypto;
    }
    
    /**
     * Calculates the total coin value from cryptocurrency cards in hand.
     * @return the total coin value
     */
    public int calculateTotalCoins() {
        int totalCoins = 0;
        for (Card card : hand) {
            if (card.isCryptocurrency()) {
                totalCoins += card.getValue();
            }
        }
        return totalCoins;
    }
    
    /**
     * Plays all cryptocurrency cards from the hand to the played area.
     * This removes them from hand and adds them to playedCards.
     * @return the total coin value from played cryptocurrency cards
     */
    public int playAllCryptocurrencyCards() {
        int totalCoins = 0;
        List<Card> toPlay = new ArrayList<>();
        
        for (Card card : hand) {
            if (card.isCryptocurrency()) {
                toPlay.add(card);
                totalCoins += card.getValue();
            }
        }
        
        hand.removeAll(toPlay);
        playedCards.addAll(toPlay);
        return totalCoins;
    }
    
    /**
     * Discards the entire hand and all played cards to the discard pile.
     */
    public void discardHand() {
        discardPile.addAll(hand);
        discardPile.addAll(playedCards);
        hand.clear();
        playedCards.clear();
    }
    
    /**
     * Gets all purchased cards (the entire deck).
     * @return a list of all purchased cards
     */
    public List<Card> getAllCards() {
        return new ArrayList<>(purchasedCards);
    }
    
    /**
     * Calculates the player's total Automation Points (APs) from all purchased cards.
     * @return the total AP value
     */
    public int calculateTotalAPs() {
        int totalAPs = 0;
        for (Card card : purchasedCards) {
            if (!card.isCryptocurrency()) {
                totalAPs += card.getValue();
            }
        }
        return totalAPs;
    }
    
    /**
     * Gets a summary of the player's deck composition.
     * @return a string showing the player's cards
     */
    public String getDeckSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("'s Deck:\n");
        
        // Count cards by type
        Map<CardType, Integer> cardCounts = new HashMap<>();
        for (Card card : purchasedCards) {
            cardCounts.put(card.getType(), cardCounts.getOrDefault(card.getType(), 0) + 1);
        }
        
        for (CardType type : CardType.values()) {
            int count = cardCounts.getOrDefault(type, 0);
            if (count > 0) {
                sb.append(String.format("  %s: %d\n", type.getDisplayName(), count));
            }
        }
        
        sb.append(String.format("Total APs: %d\n", calculateTotalAPs()));
        return sb.toString();
    }
}
