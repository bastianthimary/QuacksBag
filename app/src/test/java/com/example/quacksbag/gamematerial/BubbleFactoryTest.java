package com.example.quacksbag.gamematerial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BubbleFactoryTest {

    @Test
    void testCreateBubble() {
        Bubble bubble = BubbleFactory.createBubble(10);
        
        assertNotNull(bubble);
        assertEquals(10, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
        assertEquals(0, bubble.getVictoryPoints());
    }

    @Test
    void testCreateRubyBubble() {
        Bubble bubble = BubbleFactory.createRubyBubble(15);
        
        assertNotNull(bubble);
        assertEquals(15, bubble.getBubbleValue());
        assertTrue(bubble.isRuby());
        assertEquals(0, bubble.getVictoryPoints());
    }

    @Test
    void testCreateBubbleWithVictoryPoints() {
        Bubble bubble = BubbleFactory.createBubble(20, 25);
        
        assertNotNull(bubble);
        assertEquals(20, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
        assertEquals(25, bubble.getVictoryPoints());
    }

    @Test
    void testCreateRubyBubbleWithVictoryPoints() {
        Bubble bubble = BubbleFactory.createRubyBubble(30, 35);
        
        assertNotNull(bubble);
        assertEquals(30, bubble.getBubbleValue());
        assertTrue(bubble.isRuby());
        assertEquals(35, bubble.getVictoryPoints());
    }

    @Test
    void testCreateBubbleWithZeroValue() {
        Bubble bubble = BubbleFactory.createBubble(0);
        
        assertEquals(0, bubble.getBubbleValue());
        assertFalse(bubble.isRuby());
    }

    @Test
    void testCreateRubyBubbleWithZeroVictoryPoints() {
        Bubble bubble = BubbleFactory.createRubyBubble(10, 0);
        
        assertEquals(10, bubble.getBubbleValue());
        assertTrue(bubble.isRuby());
        assertEquals(0, bubble.getVictoryPoints());
    }

    @Test
    void testMultipleBubbleCreation() {
        Bubble bubble1 = BubbleFactory.createBubble(5);
        Bubble bubble2 = BubbleFactory.createBubble(10);
        Bubble bubble3 = BubbleFactory.createRubyBubble(15);
        
        assertNotSame(bubble1, bubble2);
        assertNotSame(bubble2, bubble3);
        assertEquals(5, bubble1.getBubbleValue());
        assertEquals(10, bubble2.getBubbleValue());
        assertEquals(15, bubble3.getBubbleValue());
    }
}
