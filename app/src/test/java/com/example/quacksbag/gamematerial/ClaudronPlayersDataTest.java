package com.example.quacksbag.gamematerial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClaudronPlayersDataTest {

    private ClaudronPlayersData claudronData;

    @BeforeEach
    void setUp() {
        claudronData = new ClaudronPlayersData(1, true);
    }

    @Test
    void testInitialDropBonus() {
        assertEquals(1, claudronData.getDropBonus());
    }

    @Test
    void testInitialFlaskFilled() {
        assertTrue(claudronData.isFlaskFilled());
    }

    @Test
    void testAddDropBonus() {
        claudronData.addDropBonus();
        assertEquals(2, claudronData.getDropBonus());
        
        claudronData.addDropBonus();
        assertEquals(3, claudronData.getDropBonus());
    }

    @Test
    void testUseFlaskWhenFilled() {
        assertTrue(claudronData.isFlaskFilled());
        boolean result = claudronData.useFlask();
        
        assertTrue(result);
        assertFalse(claudronData.isFlaskFilled());
    }

    @Test
    void testUseFlaskWhenEmpty() {
        claudronData.useFlask(); // Empty the flask
        assertFalse(claudronData.isFlaskFilled());
        
        boolean result = claudronData.useFlask();
        assertFalse(result);
        assertFalse(claudronData.isFlaskFilled());
    }

    @Test
    void testFillFlaskWhenEmpty() {
        claudronData.useFlask(); // Empty the flask first
        assertFalse(claudronData.isFlaskFilled());
        
        boolean result = claudronData.fillFlask();
        assertTrue(result);
        assertTrue(claudronData.isFlaskFilled());
    }

    @Test
    void testFillFlaskWhenAlreadyFilled() {
        assertTrue(claudronData.isFlaskFilled());
        
        boolean result = claudronData.fillFlask();
        assertFalse(result);
        assertTrue(claudronData.isFlaskFilled());
    }

    @Test
    void testFlaskCycle() {
        // Start filled
        assertTrue(claudronData.isFlaskFilled());
        
        // Use it
        assertTrue(claudronData.useFlask());
        assertFalse(claudronData.isFlaskFilled());
        
        // Fill it again
        assertTrue(claudronData.fillFlask());
        assertTrue(claudronData.isFlaskFilled());
        
        // Use it again
        assertTrue(claudronData.useFlask());
        assertFalse(claudronData.isFlaskFilled());
    }

    @Test
    void testCreationWithEmptyFlask() {
        ClaudronPlayersData data = new ClaudronPlayersData(0, false);
        
        assertEquals(0, data.getDropBonus());
        assertFalse(data.isFlaskFilled());
    }

    @Test
    void testMultipleDropBonusIncrements() {
        ClaudronPlayersData data = new ClaudronPlayersData(0, false);
        
        for (int i = 0; i < 10; i++) {
            data.addDropBonus();
        }
        
        assertEquals(10, data.getDropBonus());
    }

    @Test
    void testCreationWithHighDropBonus() {
        ClaudronPlayersData data = new ClaudronPlayersData(100, true);
        
        assertEquals(100, data.getDropBonus());
        assertTrue(data.isFlaskFilled());
    }
}
