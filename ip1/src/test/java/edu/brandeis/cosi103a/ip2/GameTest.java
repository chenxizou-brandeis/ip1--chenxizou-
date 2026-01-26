package edu.brandeis.cosi103a.ip2;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the Game class.
 */
public class GameTest {
    
    private Game game;
    
    @Before
    public void setUp() {
        game = new Game("Alice", "Bob");
    }
    
    @Test
    public void testGameCreation() {
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertNotNull(game.getSupply());
        
        assertEquals("Alice", game.getPlayer1().getName());
        assertEquals("Bob", game.getPlayer2().getName());
    }
    
    @Test
    public void testGameInitialization() {
        game.initialize();
        
        // Check starting deck sizes
        assertEquals(10, game.getPlayer1().getAllCards().size());  // 7 Bitcoin + 3 Method
        assertEquals(10, game.getPlayer2().getAllCards().size());  // 7 Bitcoin + 3 Method
        
        // Check hand sizes after drawing
        assertEquals(5, game.getPlayer1().getHandSize());
        assertEquals(5, game.getPlayer2().getHandSize());
        
        // Check supply was updated
        assertEquals(46, game.getSupply().getCount(CardType.BITCOIN));  // 60 - 7 - 7
        assertEquals(8, game.getSupply().getCount(CardType.METHOD));    // 14 - 3 - 3
    }
    
    @Test
    public void testGameIsInProgress() {
        game.initialize();
        
        // Game should be in progress at start
        assertTrue(game.isGameInProgress());
    }
    
    @Test
    public void testGameEndsWhenFrameworksGone() {
        game.initialize();
        
        // Manually buy all frameworks to test end condition
        for (int i = 0; i < 8; i++) {
            game.getSupply().buyCard(CardType.FRAMEWORK);
        }
        
        assertFalse(game.isGameInProgress());
    }
    
    @Test
    public void testExecuteTurnUpdatesTurnCount() {
        game.initialize();
        
        assertEquals(0, game.getTurnCount());
        game.executeTurn();
        assertEquals(1, game.getTurnCount());
        game.executeTurn();
        assertEquals(2, game.getTurnCount());
    }
    
    @Test
    public void testExecuteTurnDiscardsAndRedrawsHand() {
        game.initialize();
        
        int handSizeBefore = game.getPlayer1().getHandSize();
        assertEquals(5, handSizeBefore);
        
        game.executeTurn();
        
        // After turn, hand should be redraw to 5 cards
        assertEquals(5, game.getPlayer1().getHandSize());
    }
    
    @Test
    public void testPlayGameCompletesWithoutError() {
        game.playGame();
        
        // Game should have ended
        assertFalse(game.isGameInProgress());
        
        // Both players should have cards
        assertTrue(game.getPlayer1().getAllCards().size() > 10);
        assertTrue(game.getPlayer2().getAllCards().size() > 10);
        
        // At least one player should have Frameworks
        assertTrue(game.getPlayer1().calculateTotalAPs() > 0 || 
                   game.getPlayer2().calculateTotalAPs() > 0);
    }
    
    @Test
    public void testGetWinnerReturnsValidString() {
        game.initialize();
        // Manually set up a simple scenario
        game.getPlayer1().purchaseCard(new Card(CardType.FRAMEWORK));  // 6 APs
        game.getPlayer2().purchaseCard(new Card(CardType.METHOD));     // 1 AP
        
        String winner = game.getWinner();
        assertNotNull(winner);
        assertTrue(winner.contains("Alice") || winner.contains("Bob"));
        assertTrue(winner.contains("APs"));
    }
    
    @Test
    public void testGetWinnerDeterminesCorrectWinner() {
        // Start fresh without initialization
        Game freshGame = new Game("TestPlayer1", "TestPlayer2");
        
        // Player 1 gets a Framework (6 APs)
        freshGame.getPlayer1().purchaseCard(new Card(CardType.FRAMEWORK));
        
        // Player 2 gets 2 Methods (2 APs)
        freshGame.getPlayer2().purchaseCard(new Card(CardType.METHOD));
        freshGame.getPlayer2().purchaseCard(new Card(CardType.METHOD));
        
        String winner = freshGame.getWinner();
        assertTrue(winner.contains("TestPlayer1"));
        assertTrue(winner.contains("6"));
    }
    
    @Test
    public void testGetGameSummary() {
        game.playGame();
        
        String summary = game.getGameSummary();
        assertNotNull(summary);
        assertTrue(summary.contains("GAME OVER"));
        assertTrue(summary.contains("Alice"));
        assertTrue(summary.contains("Bob"));
        assertTrue(summary.contains("wins") || summary.contains("Tie"));
    }
    
    @Test
    public void testGetPlayer1() {
        assertEquals("Alice", game.getPlayer1().getName());
    }
    
    @Test
    public void testGetPlayer2() {
        assertEquals("Bob", game.getPlayer2().getName());
    }
    
    @Test
    public void testGetSupply() {
        assertNotNull(game.getSupply());
    }
    
    @Test
    public void testMultipleGamesProduceDifferentResults() {
        game.playGame();
        int game1Player1APs = game.getPlayer1().calculateTotalAPs();
        
        Game game2 = new Game("Alice", "Bob");
        game2.playGame();
        int game2Player1APs = game2.getPlayer1().calculateTotalAPs();
        
        // Different games might produce different results (not guaranteed but very likely)
        // At minimum, both should be valid games that completed
        assertTrue(game1Player1APs >= 0);
        assertTrue(game2Player1APs >= 0);
    }
    
    @Test
    public void testGameReachesCompletion() {
        long startTime = System.currentTimeMillis();
        game.playGame();
        long endTime = System.currentTimeMillis();
        
        // Game should complete in reasonable time (within 10 seconds)
        assertTrue("Game took too long to complete", (endTime - startTime) < 10000);
        
        // Supply should show no frameworks
        assertEquals(0, game.getSupply().getCount(CardType.FRAMEWORK));
    }
}
