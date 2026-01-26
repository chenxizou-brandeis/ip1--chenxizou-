package edu.brandeis.cosi103a.ip2;

import java.util.*;

/**
 * Main controller class for Automation: The Game.
 * Manages game setup, turn execution, and determines the winner.
 */
public class Game {
    private final Player player1;
    private final Player player2;
    private final CardSupply supply;
    private Player currentPlayer;
    private int turnCount;
    private static final int STARTING_HAND_SIZE = 5;
    private static final int STARTING_BITCOINS = 7;
    private static final int STARTING_METHODS = 3;
    
    /**
     * Creates a new game with two players.
     * @param player1Name name of the first player
     * @param player2Name name of the second player
     */
    public Game(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.supply = new CardSupply();
        this.turnCount = 0;
    }
    
    /**
     * Initializes the game: sets up starting decks and draws initial hands.
     */
    public void initialize() {
        // Set up starting deck for player 1
        for (int i = 0; i < STARTING_BITCOINS; i++) {
            Card bitcoin = supply.buyCard(CardType.BITCOIN);
            if (bitcoin != null) {
                player1.addStartingCard(bitcoin);
            }
        }
        for (int i = 0; i < STARTING_METHODS; i++) {
            Card method = supply.buyCard(CardType.METHOD);
            if (method != null) {
                player1.addStartingCard(method);
            }
        }
        
        // Set up starting deck for player 2
        for (int i = 0; i < STARTING_BITCOINS; i++) {
            Card bitcoin = supply.buyCard(CardType.BITCOIN);
            if (bitcoin != null) {
                player2.addStartingCard(bitcoin);
            }
        }
        for (int i = 0; i < STARTING_METHODS; i++) {
            Card method = supply.buyCard(CardType.METHOD);
            if (method != null) {
                player2.addStartingCard(method);
            }
        }
        
        // Shuffle and draw initial hands
        player1.shuffleDrawPile();
        player2.shuffleDrawPile();
        
        player1.drawCards(STARTING_HAND_SIZE);
        player2.drawCards(STARTING_HAND_SIZE);
        
        // Choose starting player randomly
        Random random = new Random();
        currentPlayer = random.nextBoolean() ? player1 : player2;
    }
    
    /**
     * Executes a single turn for the current player.
     * A turn consists of the Buy Phase and Cleanup Phase.
     */
    public void executeTurn() {
        turnCount++;
        
        // Buy Phase
        executeBuyPhase(currentPlayer);
        
        // Cleanup Phase
        executeCleanupPhase(currentPlayer);
        
        // Switch to next player
        switchToNextPlayer();
    }
    
    /**
     * Executes the Buy Phase of a player's turn.
     * Player plays all cryptocurrency cards and buys one affordable card if available.
     * @param player the player whose turn it is
     */
    private void executeBuyPhase(Player player) {
        // Play all cryptocurrency cards and calculate total coins
        int totalCoins = player.playAllCryptocurrencyCards();
        
        // Determine which card to buy using simple AI strategy
        CardType cardToBuy = chooseCardToBuy(totalCoins);
        
        // Buy the card if available
        if (cardToBuy != null && supply.isAvailable(cardToBuy)) {
            Card boughtCard = supply.buyCard(cardToBuy);
            if (boughtCard != null) {
                player.purchaseCard(boughtCard);
            }
        }
    }
    
    /**
     * Executes the Cleanup Phase of a player's turn.
     * Discards hand and all played cards, then draws 5 new cards.
     * @param player the player whose turn it is
     */
    private void executeCleanupPhase(Player player) {
        // Discard hand to discard pile
        player.discardHand();
        
        // Draw 5 new cards
        player.drawCards(STARTING_HAND_SIZE);
    }
    
    /**
     * Implements simple AI strategy to choose which card to buy.
     * Strategy: Buy the most expensive affordable card available.
     * Priority: Framework > Module > Method > Ethereum > Dogecoin > Bitcoin
     * @param availableCoins the coins available for spending
     * @return the CardType to buy, or null if no affordable cards
     */
    private CardType chooseCardToBuy(int availableCoins) {
        // Order by preference: most valuable cards first
        CardType[] preferredOrder = {
            CardType.FRAMEWORK,
            CardType.MODULE,
            CardType.METHOD,
            CardType.DOGECOIN,
            CardType.ETHEREUM,
            CardType.BITCOIN
        };
        
        for (CardType type : preferredOrder) {
            if (supply.isAvailable(type) && type.getCost() <= availableCoins) {
                return type;
            }
        }
        
        return null;
    }
    
    /**
     * Switches the current player to the other player.
     */
    private void switchToNextPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    
    /**
     * Checks if the game should continue.
     * Game ends when all Framework cards have been purchased.
     * @return true if the game is still in progress
     */
    public boolean isGameInProgress() {
        return !supply.isGameOver();
    }
    
    /**
     * Runs the complete game simulation until a winner is determined.
     */
    public void playGame() {
        initialize();
        
        while (isGameInProgress()) {
            executeTurn();
        }
    }
    
    /**
     * Determines and returns the winner.
     * @return a string describing the winner
     */
    public String getWinner() {
        int player1APs = player1.calculateTotalAPs();
        int player2APs = player2.calculateTotalAPs();
        
        if (player1APs > player2APs) {
            return String.format("%s wins with %d APs (vs %d APs)", 
                player1.getName(), player1APs, player2APs);
        } else if (player2APs > player1APs) {
            return String.format("%s wins with %d APs (vs %d APs)", 
                player2.getName(), player2APs, player1APs);
        } else {
            return String.format("Tie! Both players have %d APs", player1APs);
        }
    }
    
    /**
     * Gets a summary of the final game state.
     * @return a string with game results
     */
    public String getGameSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GAME OVER ===\n");
        sb.append("Total turns: ").append(turnCount).append("\n\n");
        sb.append(player1.getDeckSummary()).append("\n");
        sb.append(player2.getDeckSummary()).append("\n");
        sb.append(getWinner()).append("\n");
        return sb.toString();
    }
    
    /**
     * Gets the current turn number.
     * @return the turn count
     */
    public int getTurnCount() {
        return turnCount;
    }
    
    /**
     * Gets player 1.
     * @return player 1
     */
    public Player getPlayer1() {
        return player1;
    }
    
    /**
     * Gets player 2.
     * @return player 2
     */
    public Player getPlayer2() {
        return player2;
    }
    
    /**
     * Gets the current card supply.
     * @return the CardSupply
     */
    public CardSupply getSupply() {
        return supply;
    }
}
