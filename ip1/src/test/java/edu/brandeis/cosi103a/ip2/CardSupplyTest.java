package edu.brandeis.cosi103a.ip2;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for the CardSupply class.
 */
public class CardSupplyTest {
    
    private CardSupply supply;
    
    @Before
    public void setUp() {
        supply = new CardSupply();
    }
    
    @Test
    public void testInitialSupply() {
        // Check initial counts
        assertEquals(60, supply.getCount(CardType.BITCOIN));
        assertEquals(40, supply.getCount(CardType.ETHEREUM));
        assertEquals(30, supply.getCount(CardType.DOGECOIN));
        assertEquals(14, supply.getCount(CardType.METHOD));
        assertEquals(8, supply.getCount(CardType.MODULE));
        assertEquals(8, supply.getCount(CardType.FRAMEWORK));
    }
    
    @Test
    public void testAllCardsAvailable() {
        for (CardType type : CardType.values()) {
            assertTrue("Card type " + type + " should be available", supply.isAvailable(type));
        }
    }
    
    @Test
    public void testBuyCard() {
        Card card = supply.buyCard(CardType.BITCOIN);
        assertNotNull(card);
        assertEquals(CardType.BITCOIN, card.getType());
        assertEquals(59, supply.getCount(CardType.BITCOIN));
    }
    
    @Test
    public void testBuyMultipleCards() {
        supply.buyCard(CardType.ETHEREUM);
        supply.buyCard(CardType.ETHEREUM);
        supply.buyCard(CardType.ETHEREUM);
        
        assertEquals(37, supply.getCount(CardType.ETHEREUM));
    }
    
    @Test
    public void testBuyCardWhenUnavailable() {
        // Buy all Frameworks
        for (int i = 0; i < 8; i++) {
            assertNotNull(supply.buyCard(CardType.FRAMEWORK));
        }
        
        // Try to buy another Framework
        Card card = supply.buyCard(CardType.FRAMEWORK);
        assertNull(card);
        assertEquals(0, supply.getCount(CardType.FRAMEWORK));
    }
    
    @Test
    public void testIsAvailableAfterPurchase() {
        assertTrue(supply.isAvailable(CardType.FRAMEWORK));
        
        // Buy all frameworks
        for (int i = 0; i < 8; i++) {
            supply.buyCard(CardType.FRAMEWORK);
        }
        
        assertFalse(supply.isAvailable(CardType.FRAMEWORK));
    }
    
    @Test
    public void testGetAvailableCardTypes() {
        assertEquals(6, supply.getAvailableCardTypes().size());
        
        // Buy all frameworks
        for (int i = 0; i < 8; i++) {
            supply.buyCard(CardType.FRAMEWORK);
        }
        
        assertEquals(5, supply.getAvailableCardTypes().size());
        assertFalse(supply.getAvailableCardTypes().contains(CardType.FRAMEWORK));
    }
    
    @Test
    public void testIsGameOverWhenFrameworksExist() {
        assertFalse(supply.isGameOver());
    }
    
    @Test
    public void testIsGameOverWhenAllFrameworksPurchased() {
        for (int i = 0; i < 8; i++) {
            supply.buyCard(CardType.FRAMEWORK);
        }
        assertTrue(supply.isGameOver());
    }
    
    @Test
    public void testGetSupplyStatus() {
        String status = supply.getSupplyStatus();
        assertNotNull(status);
        assertTrue(status.contains("Bitcoin"));
        assertTrue(status.contains("Framework"));
        assertTrue(status.contains("60"));
        assertTrue(status.contains("8"));
    }
}
