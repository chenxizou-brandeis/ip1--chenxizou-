package edu.brandeis.cosi103a.ip2;

/**
 * Represents a single card in Automation: The Game.
 * Cards have a type that defines their cost, value, and whether they're cryptocurrency or automation cards.
 */
public class Card {
    private final CardType type;
    
    /**
     * Creates a new card of the specified type.
     * @param type the CardType of this card
     */
    public Card(CardType type) {
        this.type = type;
    }
    
    /**
     * Gets the card's type.
     * @return the CardType
     */
    public CardType getType() {
        return type;
    }
    
    /**
     * Gets the cost to purchase this card.
     * @return the cost in coins
     */
    public int getCost() {
        return type.getCost();
    }
    
    /**
     * Gets the value of this card.
     * For cryptocurrency cards: value in coins when played.
     * For automation cards: value in Automation Points at game end.
     * @return the value
     */
    public int getValue() {
        return type.getValue();
    }
    
    /**
     * Checks if this card is a cryptocurrency card.
     * @return true if this card provides coins, false if it provides APs
     */
    public boolean isCryptocurrency() {
        return type.isCryptocurrency();
    }
    
    /**
     * Gets the display name of this card.
     * @return the card's display name
     */
    public String getDisplayName() {
        return type.getDisplayName();
    }
    
    @Override
    public String toString() {
        return type.getDisplayName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card other = (Card) obj;
        return type == other.type;
    }
    
    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
