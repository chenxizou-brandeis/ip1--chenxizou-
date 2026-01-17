package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the Dice Game application
 */
public class AppTest 
{
    /**
     * Test that rollDie returns a value between 1 and 6
     */
    @Test
    public void testRollDieRange()
    {
        for (int i = 0; i < 100; i++) {
            int roll = App.rollDie();
            assertTrue("Die roll should be >= 1", roll >= 1);
            assertTrue("Die roll should be <= 6", roll <= 6);
        }
    }
    
    /**
     * Test that rollDie returns valid integer values
     */
    @Test
    public void testRollDieReturnsInt()
    {
        int roll = App.rollDie();
        assertTrue("Roll should be a positive integer", roll > 0);
    }
    
    /**
     * Test simulateTurn with a single roll
     */
    @Test
    public void testSimulateTurnSingleRoll()
    {
        int[] rolls = {4};
        int result = App.simulateTurn(rolls);
        assertEquals("Should return the only roll value", 4, result);
    }
    
    /**
     * Test simulateTurn with multiple rolls (should return last)
     */
    @Test
    public void testSimulateTurnMultipleRolls()
    {
        int[] rolls = {2, 5, 6};
        int result = App.simulateTurn(rolls);
        assertEquals("Should return the last roll value", 6, result);
    }
    
    /**
     * Test simulateTurn with empty array
     */
    @Test
    public void testSimulateTurnEmptyArray()
    {
        int[] rolls = {};
        int result = App.simulateTurn(rolls);
        assertEquals("Should return 0 for empty array", 0, result);
    }
    
    /**
     * Test simulateTurn with null array
     */
    @Test
    public void testSimulateTurnNullArray()
    {
        int result = App.simulateTurn(null);
        assertEquals("Should return 0 for null array", 0, result);
    }
    
    /**
     * Test determineWinner when player 1 wins
     */
    @Test
    public void testDetermineWinnerPlayer1Wins()
    {
        String result = App.determineWinner("Alice", 50, "Bob", 30);
        assertTrue("Should indicate Alice wins", result.contains("Alice") && result.contains("wins"));
    }
    
    /**
     * Test determineWinner when player 2 wins
     */
    @Test
    public void testDetermineWinnerPlayer2Wins()
    {
        String result = App.determineWinner("Alice", 20, "Bob", 60);
        assertTrue("Should indicate Bob wins", result.contains("Bob") && result.contains("wins"));
    }
    
    /**
     * Test determineWinner when it's a tie
     */
    @Test
    public void testDetermineWinnerTie()
    {
        String result = App.determineWinner("Alice", 40, "Bob", 40);
        assertTrue("Should indicate a tie", result.contains("tie") || result.contains("Tie"));
    }
    
    /**
     * Test determineWinner with zero scores
     */
    @Test
    public void testDetermineWinnerZeroScores()
    {
        String result = App.determineWinner("Alice", 0, "Bob", 0);
        assertTrue("Should indicate a tie with zero scores", result.contains("tie") || result.contains("Tie"));
    }
    
    /**
     * Test determineWinner with large scores
     */
    @Test
    public void testDetermineWinnerLargeScores()
    {
        String result = App.determineWinner("Player1", 500, "Player2", 450);
        assertTrue("Should indicate Player1 wins", result.contains("Player1") && result.contains("wins"));
    }
    
    /**
     * Test game constants
     */
    @Test
    public void testGameConstants()
    {
        assertEquals("NUM_TURNS should be 10", 10, App.NUM_TURNS);
        assertEquals("MAX_REROLLS should be 2", 2, App.MAX_REROLLS);
        assertEquals("DIE_FACES should be 6", 6, App.DIE_FACES);
    }
    
    /**
     * Test that NUM_TURNS is reasonable
     */
    @Test
    public void testNumTurnsIsPositive()
    {
        assertTrue("NUM_TURNS should be positive", App.NUM_TURNS > 0);
    }
    
    /**
     * Test that MAX_REROLLS is reasonable
     */
    @Test
    public void testMaxRerollsIsNonNegative()
    {
        assertTrue("MAX_REROLLS should be non-negative", App.MAX_REROLLS >= 0);
    }
    
    /**
     * Test that DIE_FACES is reasonable
     */
    @Test
    public void testDieFacesIsValid()
    {
        assertTrue("DIE_FACES should be positive", App.DIE_FACES > 1);
    }
}
