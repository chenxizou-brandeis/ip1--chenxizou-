package edu.brandeis.cosi103a.ip2;

import java.util.*;

/**
 * Manages the shared supply of cards available for purchase.
 * Maintains separate piles for each card type and tracks remaining quantities.
 */
public class CardSupply {
    private final Map<CardType, Integer> cardCounts;
    
    /**
     * Creates a new CardSupply with initial quantities of all card types.
     */
    public CardSupply() {
        this.cardCounts = new HashMap<>();
        
        // Initialize all card types with their supply count
        for (CardType type : CardType.values()) {
            cardCounts.put(type, type.getSupplyCount());
        }
    }
    
    /**
     * Gets the number of cards of a specific type remaining in supply.
     * @param type the CardType to check
     * @return the count of cards available
     */
    public int getCount(CardType type) {
        return cardCounts.getOrDefault(type, 0);
    }
    
    /**
     * Checks if a card of a specific type is available in the supply.
     * @param type the CardType to check
     * @return true if at least one card is available
     */
    public boolean isAvailable(CardType type) {
        return getCount(type) > 0;
    }
    
    /**
     * Purchases a card from the supply (removes one card from inventory).
     * @param type the CardType to purchase
     * @return a Card of the specified type, or null if not available
     */
    public Card buyCard(CardType type) {
        if (isAvailable(type)) {
            int currentCount = cardCounts.get(type);
            cardCounts.put(type, currentCount - 1);
            return new Card(type);
        }
        return null;
    }
    
    /**
     * Gets all card types that are still available in the supply.
     * @return a list of available CardTypes
     */
    public List<CardType> getAvailableCardTypes() {
        List<CardType> available = new ArrayList<>();
        for (CardType type : CardType.values()) {
            if (isAvailable(type)) {
                available.add(type);
            }
        }
        return available;
    }
    
    /**
     * Checks if the game should end (all Framework cards have been purchased).
     * @return true if no Framework cards remain in supply
     */
    public boolean isGameOver() {
        return getCount(CardType.FRAMEWORK) == 0;
    }
    
    /**
     * Gets a summary of the current supply state (for debugging/display).
     * @return a string representation of remaining card quantities
     */
    public String getSupplyStatus() {
        StringBuilder sb = new StringBuilder("Supply Status:\n");
        for (CardType type : CardType.values()) {
            sb.append(String.format("  %s: %d\n", type.getDisplayName(), getCount(type)));
        }
        return sb.toString();
    }
}
