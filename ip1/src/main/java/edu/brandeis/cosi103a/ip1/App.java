package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Two-Player Dice Game
 * Players take turns rolling a 6-sided die, with up to 2 re-rolls per turn.
 * Each player gets 10 turns, and the player with the highest score wins.
 */
public class App 
{
    public static final int NUM_TURNS = 10;
    public static final int MAX_REROLLS = 2;
    public static final int DIE_FACES = 6;
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    public static void main( String[] args )
    {
        System.out.println("========================================");
        System.out.println("     Welcome to the Dice Game!");
        System.out.println("========================================");
        System.out.println();
        
        // Get player names
        System.out.print("Enter name for Player 1: ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) player1Name = "Player 1";
        
        System.out.print("Enter name for Player 2: ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) player2Name = "Player 2";
        
        System.out.println();
        
        // Play the game
        playGame(player1Name, player2Name);
        
        scanner.close();
    }
    
    /**
     * Plays a complete game between two players
     * @param player1Name Name of player 1
     * @param player2Name Name of player 2
     */
    public static void playGame(String player1Name, String player2Name) {
        // Initialize scores
        int player1Score = 0;
        int player2Score = 0;
        
        // Play 10 turns for each player
        for (int turn = 1; turn <= NUM_TURNS; turn++) {
            System.out.println("========== TURN " + turn + " / " + NUM_TURNS + " ==========");
            
            // Player 1's turn
            System.out.println();
            System.out.println(player1Name + "'s turn:");
            int player1TurnScore = playerTurn(player1Name);
            player1Score += player1TurnScore;
            System.out.println(player1Name + " scored: " + player1TurnScore);
            System.out.println(player1Name + " total score: " + player1Score);
            System.out.println();
            
            // Player 2's turn
            System.out.println(player2Name + "'s turn:");
            int player2TurnScore = playerTurn(player2Name);
            player2Score += player2TurnScore;
            System.out.println(player2Name + " scored: " + player2TurnScore);
            System.out.println(player2Name + " total score: " + player2Score);
            System.out.println();
        }
        
        // Display results
        displayResults(player1Name, player1Score, player2Name, player2Score);
    }
    
    /**
     * Displays the final results and determines the winner
     * @param player1Name Name of player 1
     * @param player1Score Final score of player 1
     * @param player2Name Name of player 2
     * @param player2Score Final score of player 2
     */
    public static void displayResults(String player1Name, int player1Score, 
                                      String player2Name, int player2Score) {
        System.out.println("========================================");
        System.out.println("            GAME OVER!");
        System.out.println("========================================");
        System.out.println(player1Name + " final score: " + player1Score);
        System.out.println(player2Name + " final score: " + player2Score);
        System.out.println();
        
        String winner = determineWinner(player1Name, player1Score, player2Name, player2Score);
        System.out.println(winner);
    }
    
    /**
     * Determines the winner based on final scores
     * @param player1Name Name of player 1
     * @param player1Score Final score of player 1
     * @param player2Name Name of player 2
     * @param player2Score Final score of player 2
     * @return A string announcing the winner
     */
    public static String determineWinner(String player1Name, int player1Score, 
                                         String player2Name, int player2Score) {
        if (player1Score > player2Score) {
            return "🎉 " + player1Name + " wins!";
        } else if (player2Score > player1Score) {
            return "🎉 " + player2Name + " wins!";
        } else {
            return "🤝 It's a tie!";
        }
    }
    
    /**
     * Handles one player's turn: rolling the die and allowing up to 2 re-rolls
     * @param playerName The name of the player
     * @return The final score for this turn
     */
    public static int playerTurn(String playerName) {
        int currentValue = rollDie();
        System.out.println("  Initial roll: " + currentValue);
        
        int rerollsUsed = 0;
        
        while (rerollsUsed < MAX_REROLLS) {
            System.out.print("  Do you want to re-roll? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (response.equals("yes") || response.equals("y")) {
                currentValue = rollDie();
                rerollsUsed++;
                System.out.println("  Re-roll #" + rerollsUsed + ": " + currentValue);
            } else {
                break;
            }
        }
        
        System.out.println("  Turn ended. Final value: " + currentValue);
        return currentValue;
    }
    
    /**
     * Rolls a 6-sided die
     * @return Random number between 1 and 6
     */
    public static int rollDie() {
        return random.nextInt(DIE_FACES) + 1;
    }
    
    /**
     * Simulates a turn with predetermined roll values (for testing)
     * @param rolls Array of die roll values
     * @return The final score for this turn
     */
    public static int simulateTurn(int[] rolls) {
        if (rolls == null || rolls.length == 0) {
            return 0;
        }
        // Return the last value in the rolls array as the final score
        return rolls[rolls.length - 1];
    }
}
