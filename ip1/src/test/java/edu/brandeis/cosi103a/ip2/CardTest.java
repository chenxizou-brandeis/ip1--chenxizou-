package edu.brandeis.cosi103a.ip2;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Card class.
 */
public class CardTest {
    
    @Test
    public void testCardCreation() {
        Card card = new Card(CardType.BITCOIN);
        assertNotNull(card);
        assertEquals(CardType.BITCOIN, card.getType());
    }
    
    @Test
    public void testCardCost() {
        assertEquals(0, new Card(CardType.BITCOIN).getCost());
        assertEquals(3, new Card(CardType.ETHEREUM).getCost());
        assertEquals(6, new Card(CardType.DOGECOIN).getCost());
        assertEquals(2, new Card(CardType.METHOD).getCost());
        assertEquals(5, new Card(CardType.MODULE).getCost());
        assertEquals(8, new Card(CardType.FRAMEWORK).getCost());
    }
    
    @Test
    public void testCardValue() {
        assertEquals(1, new Card(CardType.BITCOIN).getValue());
        assertEquals(2, new Card(CardType.ETHEREUM).getValue());
        assertEquals(3, new Card(CardType.DOGECOIN).getValue());
        assertEquals(1, new Card(CardType.METHOD).getValue());
        assertEquals(3, new Card(CardType.MODULE).getValue());
        assertEquals(6, new Card(CardType.FRAMEWORK).getValue());
    }
    
    @Test
    public void testIsCryptocurrency() {
        assertTrue(new Card(CardType.BITCOIN).isCryptocurrency());
        assertTrue(new Card(CardType.ETHEREUM).isCryptocurrency());
        assertTrue(new Card(CardType.DOGECOIN).isCryptocurrency());
        assertFalse(new Card(CardType.METHOD).isCryptocurrency());
        assertFalse(new Card(CardType.MODULE).isCryptocurrency());
        assertFalse(new Card(CardType.FRAMEWORK).isCryptocurrency());
    }
    
    @Test
    public void testCardDisplayName() {
        assertEquals("Bitcoin", new Card(CardType.BITCOIN).getDisplayName());
        assertEquals("Method", new Card(CardType.METHOD).getDisplayName());
        assertEquals("Framework", new Card(CardType.FRAMEWORK).getDisplayName());
    }
    
    @Test
    public void testCardToString() {
        assertEquals("Bitcoin", new Card(CardType.BITCOIN).toString());
        assertEquals("Framework", new Card(CardType.FRAMEWORK).toString());
    }
    
    @Test
    public void testCardEquality() {
        Card card1 = new Card(CardType.BITCOIN);
        Card card2 = new Card(CardType.BITCOIN);
        Card card3 = new Card(CardType.ETHEREUM);
        
        assertEquals(card1, card2);
        assertNotEquals(card1, card3);
        assertEquals(card1, card1);
    }
    
    @Test
    public void testCardHashCode() {
        Card card1 = new Card(CardType.BITCOIN);
        Card card2 = new Card(CardType.BITCOIN);
        
        assertEquals(card1.hashCode(), card2.hashCode());
    }
}
