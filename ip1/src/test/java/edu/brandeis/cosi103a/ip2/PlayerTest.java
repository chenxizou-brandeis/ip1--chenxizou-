package edu.brandeis.cosi103a.ip2;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Unit tests for the Player class.
 */
public class PlayerTest {
    
    private Player player;
    
    @Before
    public void setUp() {
        player = new Player("TestPlayer");
    }
    
    @Test
    public void testPlayerCreation() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getHandSize());
    }
    
    @Test
    public void testAddStartingCards() {
        Card bitcoin = new Card(CardType.BITCOIN);
        player.addStartingCard(bitcoin);
        
        assertEquals(1, player.getAllCards().size());
        assertTrue(player.getAllCards().contains(bitcoin));
    }
    
    @Test
    public void testPurchaseCard() {
        Card method = new Card(CardType.METHOD);
        player.purchaseCard(method);
        
        assertEquals(1, player.getAllCards().size());
        assertTrue(player.getAllCards().contains(method));
    }
    
    @Test
    public void testShuffleAndDrawCards() {
        // Add cards to draw pile
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.METHOD));
        player.addStartingCard(new Card(CardType.ETHEREUM));
        
        player.shuffleDrawPile();
        player.drawCards(2);
        
        assertEquals(2, player.getHandSize());
    }
    
    @Test
    public void testDrawMoreCardsThanAvailable() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.METHOD));
        
        player.shuffleDrawPile();
        player.drawCards(5);
        
        // Should only have 2 cards
        assertEquals(2, player.getHandSize());
    }
    
    @Test
    public void testGetCryptocurrencyCardsFromHand() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.METHOD));
        player.addStartingCard(new Card(CardType.ETHEREUM));
        player.addStartingCard(new Card(CardType.DOGECOIN));
        
        player.shuffleDrawPile();
        player.drawCards(4);
        
        List<Card> cryptoCards = player.getCryptocurrencyCardsFromHand();
        
        // Should have 3 cryptocurrency cards
        assertEquals(3, cryptoCards.size());
    }
    
    @Test
    public void testCalculateTotalCoins() {
        player.addStartingCard(new Card(CardType.BITCOIN));    // 1 coin
        player.addStartingCard(new Card(CardType.ETHEREUM));   // 2 coins
        player.addStartingCard(new Card(CardType.DOGECOIN));   // 3 coins
        player.addStartingCard(new Card(CardType.METHOD));     // 0 coins
        
        player.shuffleDrawPile();
        player.drawCards(4);
        
        assertEquals(6, player.calculateTotalCoins());
    }
    
    @Test
    public void testPlayAllCryptocurrencyCards() {
        player.addStartingCard(new Card(CardType.BITCOIN));    // 1 coin
        player.addStartingCard(new Card(CardType.ETHEREUM));   // 2 coins
        player.addStartingCard(new Card(CardType.DOGECOIN));   // 3 coins
        player.addStartingCard(new Card(CardType.METHOD));     // automation card
        
        player.shuffleDrawPile();
        player.drawCards(4);
        
        // Before playing cards
        assertEquals(4, player.getHandSize());
        assertEquals(0, player.getPlayedCards().size());
        
        // Play all cryptocurrency cards
        int totalCoins = player.playAllCryptocurrencyCards();
        
        // After playing cards
        assertEquals(6, totalCoins);  // 1 + 2 + 3
        assertEquals(1, player.getHandSize());  // Only METHOD left
        assertEquals(3, player.getPlayedCards().size());  // 3 crypto cards played
    }
    
    @Test
    public void testPlayedCardsAreDiscarded() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.ETHEREUM));
        player.addStartingCard(new Card(CardType.METHOD));
        
        player.shuffleDrawPile();
        player.drawCards(3);
        
        // Play crypto cards
        player.playAllCryptocurrencyCards();
        
        assertEquals(2, player.getPlayedCards().size());
        
        // Discard hand and played cards
        player.discardHand();
        
        assertEquals(0, player.getHandSize());
        assertEquals(0, player.getPlayedCards().size());
    }
    
    @Test
    public void testCalculateTotalCoinsEmptyHand() {
        assertEquals(0, player.calculateTotalCoins());
    }
    
    @Test
    public void testDiscardHand() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.METHOD));
        
        player.shuffleDrawPile();
        player.drawCards(2);
        
        assertEquals(2, player.getHandSize());
        player.discardHand();
        assertEquals(0, player.getHandSize());
    }
    
    @Test
    public void testCalculateTotalAPs() {
        player.purchaseCard(new Card(CardType.METHOD));     // 1 AP
        player.purchaseCard(new Card(CardType.MODULE));     // 3 APs
        player.purchaseCard(new Card(CardType.FRAMEWORK));  // 6 APs
        player.purchaseCard(new Card(CardType.BITCOIN));    // 0 APs
        
        assertEquals(10, player.calculateTotalAPs());
    }
    
    @Test
    public void testCalculateTotalAPsNoCryptoCards() {
        player.purchaseCard(new Card(CardType.METHOD));     // 1 AP
        player.purchaseCard(new Card(CardType.MODULE));     // 3 APs
        
        assertEquals(4, player.calculateTotalAPs());
    }
    
    @Test
    public void testReshuffleDiscardPileWhenDrawPileEmpty() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.addStartingCard(new Card(CardType.METHOD));
        
        player.shuffleDrawPile();
        
        // Draw all cards from draw pile
        player.drawCards(2);
        assertEquals(2, player.getHandSize());
        
        // Discard hand
        player.discardHand();
        assertEquals(0, player.getHandSize());
        
        // Draw again - should reshuffle
        player.drawCards(2);
        assertEquals(2, player.getHandSize());
    }
    
    @Test
    public void testGetAllCards() {
        player.purchaseCard(new Card(CardType.BITCOIN));
        player.purchaseCard(new Card(CardType.BITCOIN));
        player.purchaseCard(new Card(CardType.METHOD));
        
        assertEquals(3, player.getAllCards().size());
    }
    
    @Test
    public void testGetDeckSummary() {
        player.purchaseCard(new Card(CardType.BITCOIN));
        player.purchaseCard(new Card(CardType.METHOD));
        player.purchaseCard(new Card(CardType.FRAMEWORK));
        
        String summary = player.getDeckSummary();
        assertNotNull(summary);
        assertTrue(summary.contains("TestPlayer's Deck"));
        assertTrue(summary.contains("Bitcoin"));
        assertTrue(summary.contains("Total APs"));
    }
    
    @Test
    public void testGetHandIsACopy() {
        player.addStartingCard(new Card(CardType.BITCOIN));
        player.shuffleDrawPile();
        player.drawCards(1);
        
        List<Card> hand = player.getHand();
        hand.clear();
        
        // Original hand should still have the card
        assertEquals(1, player.getHandSize());
    }
}
