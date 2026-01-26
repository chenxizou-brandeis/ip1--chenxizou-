package edu.brandeis.cosi103a.ip2;

/**
 * Main entry point for Automation: The Game.
 * Runs the game simulation with two automated players.
 */
public class AutomationGame {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Welcome to Automation: The Game!");
        System.out.println("========================================\n");
        
        // Create and run a game
        Game game = new Game("Alice", "Bob");
        
        System.out.println("Starting game with automated players...\n");
        game.playGame();
        
        // Display results
        System.out.println(game.getGameSummary());
    }
}
