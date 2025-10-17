package com.example.quacksbag.gamematerial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleTest {

    @Test
    void testBubbleCreationWithValueOnly() {
        Bubble bubble = new Bubble(5);
        
        assertEquals(5, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
        assertEquals(0, bubble.getVictoryPoints());
    }

    @Test
    void testBubbleCreationWithValueAndRuby() {
        Bubble rubyBubble = new Bubble(10, true);
        
        assertEquals(10, rubyBubble.getBubbleValue());
        assertTrue(rubyBubble.isRuby());
        assertEquals(0, rubyBubble.getVictoryPoints());
    }

    @Test
    void testBubbleCreationWithValueAndNonRuby() {
        Bubble nonRubyBubble = new Bubble(7, false);
        
        assertEquals(7, nonRubyBubble.getBubbleValue());
        assertFalse(nonRubyBubble.isRuby());
        assertEquals(0, nonRubyBubble.getVictoryPoints());
    }

    @Test
    void testBubbleCreationWithAllParameters() {
        Bubble bubble = new Bubble(15, true, 20);
        
        assertEquals(15, bubble.getBubbleValue());
        assertTrue(bubble.isRuby());
        assertEquals(20, bubble.getVictoryPoints());
    }

    @Test
    void testBubbleWithZeroValue() {
        Bubble bubble = new Bubble(0);
        
        assertEquals(0, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
        assertEquals(0, bubble.getVictoryPoints());
    }

    @Test
    void testBubbleWithNegativeValue() {
        Bubble bubble = new Bubble(-5);
        
        assertEquals(-5, bubble.getBubbleValue());
    }

    @Test
    void testBubbleWithVictoryPointsButNoRuby() {
        Bubble bubble = new Bubble(10, false, 15);
        
        assertEquals(10, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
        assertEquals(15, bubble.getVictoryPoints());
    }

    @Test
    void testBubbleWithHighValues() {
        Bubble bubble = new Bubble(100, true, 200);
        
        assertEquals(100, bubble.getBubbleValue());
        assertTrue(bubble.isRuby());
        assertEquals(200, bubble.getVictoryPoints());
    }
}
