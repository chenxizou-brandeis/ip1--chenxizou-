package edu.brandeis.cosi103a.ip2;

/**
 * Enum representing the different types of cards in Automation: The Game.
 * Cards are either Cryptocurrency cards (provide coin value) or Automation cards (provide AP value).
 */
public enum CardType {
    // Cryptocurrency cards - provide buying power
    BITCOIN("Bitcoin", 0, 1, 60, true),
    ETHEREUM("Ethereum", 3, 2, 40, true),
    DOGECOIN("Dogecoin", 6, 3, 30, true),
    
    // Automation cards - provide victory points at game end
    METHOD("Method", 2, 1, 14, false),
    MODULE("Module", 5, 3, 8, false),
    FRAMEWORK("Framework", 8, 6, 8, false);
    
    private final String displayName;
    private final int cost;
    private final int value;
    private final int supplyCount;
    private final boolean isCryptocurrency;
    
    CardType(String displayName, int cost, int value, int supplyCount, boolean isCryptocurrency) {
        this.displayName = displayName;
        this.cost = cost;
        this.value = value;
        this.supplyCount = supplyCount;
        this.isCryptocurrency = isCryptocurrency;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getCost() {
        return cost;
    }
    
    public int getValue() {
        return value;
    }
    
    public int getSupplyCount() {
        return supplyCount;
    }
    
    public boolean isCryptocurrency() {
        return isCryptocurrency;
    }
}
