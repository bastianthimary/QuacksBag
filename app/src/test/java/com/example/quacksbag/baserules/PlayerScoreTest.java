package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.ClaudronPlayersData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreTest {

    private PlayerScore playerScore;

    @BeforeEach
    void setUp() {
        playerScore = new PlayerScore("TestPlayer");
    }

    @Test
    void testPlayerScoreCreation() {
        assertNotNull(playerScore);
    }

    @Test
    void testInitialRubyCount() {
        assertEquals(1, playerScore.getRubyCount());
    }

    @Test
    void testInitialVictoryPoints() {
        assertEquals(0, playerScore.getVictoryPoints());
    }

    @Test
    void testInitialDropBonus() {
        assertEquals(0, playerScore.getDropBonus());
    }

    @Test
    void testGetBagManager() {
        BagManager bagManager = playerScore.getBagManager();
        
        assertNotNull(bagManager);
    }

    @Test
    void testSetBagManager() {
        BagManager newBagManager = new BagManager();
        playerScore.setBagManager(newBagManager);
        
        assertEquals(newBagManager, playerScore.getBagManager());
    }

    @Test
    void testAddRuby() {
        playerScore.addRuby();
        
        assertEquals(2, playerScore.getRubyCount());
    }

    @Test
    void testAddMultipleRubies() {
        playerScore.addRuby();
        playerScore.addRuby();
        playerScore.addRuby();
        
        assertEquals(4, playerScore.getRubyCount());
    }

    @Test
    void testPayRubyWhenEnoughRubies() {
        playerScore.addRuby(); // Now has 2 rubies
        playerScore.payRuby();
        
        assertEquals(0, playerScore.getRubyCount());
    }

    @Test
    void testPayRubyWithOnlyOneRuby() {
        // Initial state has 1 ruby
        playerScore.payRuby();
        
        assertEquals(1, playerScore.getRubyCount()); // Should not change
    }

    @Test
    void testPayRubyWithThreeRubies() {
        playerScore.setRubyCount(3);
        playerScore.payRuby();
        
        assertEquals(1, playerScore.getRubyCount());
    }

    @Test
    void testSetRubyCount() {
        playerScore.setRubyCount(10);
        
        assertEquals(10, playerScore.getRubyCount());
    }

    @Test
    void testSetRubyCountToZero() {
        playerScore.setRubyCount(0);
        
        assertEquals(0, playerScore.getRubyCount());
    }

    @Test
    void testAddVictoryPoints() {
        playerScore.addVictoryPoints(10);
        
        assertEquals(10, playerScore.getVictoryPoints());
    }

    @Test
    void testAddMultipleVictoryPoints() {
        playerScore.addVictoryPoints(5);
        playerScore.addVictoryPoints(10);
        playerScore.addVictoryPoints(15);
        
        assertEquals(30, playerScore.getVictoryPoints());
    }

    @Test
    void testSetVictoryPoints() {
        playerScore.setVictoryPoints(50);
        
        assertEquals(50, playerScore.getVictoryPoints());
    }

    @Test
    void testAddNegativeVictoryPoints() {
        playerScore.addVictoryPoints(-5);
        
        assertEquals(-5, playerScore.getVictoryPoints());
    }

    @Test
    void testGetClaudronPlayersData() {
        ClaudronPlayersData data = playerScore.getClaudronPlayersData();
        
        assertNotNull(data);
        assertEquals(0, data.getDropBonus());
        assertTrue(data.isFlaskFilled());
    }

    @Test
    void testAddClaudronDropBonus() {
        playerScore.addClaudronDropBonus();
        
        assertEquals(1, playerScore.getDropBonus());
    }

    @Test
    void testAddMultipleClaudronDropBonuses() {
        playerScore.addClaudronDropBonus();
        playerScore.addClaudronDropBonus();
        playerScore.addClaudronDropBonus();
        
        assertEquals(3, playerScore.getDropBonus());
    }

    @Test
    void testUseFlask() {
        playerScore.useFlask();
        
        assertFalse(playerScore.getClaudronPlayersData().isFlaskFilled());
    }

    @Test
    void testFillFlask() {
        playerScore.useFlask(); // Empty it first
        boolean result = playerScore.fillFlask();
        
        assertTrue(result);
        assertTrue(playerScore.getClaudronPlayersData().isFlaskFilled());
    }

    @Test
    void testFillFlaskWhenAlreadyFilled() {
        boolean result = playerScore.fillFlask();
        
        assertFalse(result);
    }

    @Test
    void testComplexScenario() {
        // Add rubies and victory points
        playerScore.addRuby();
        playerScore.addRuby();
        playerScore.addVictoryPoints(20);
        
        // Pay for something with rubies
        playerScore.payRuby();
        
        // Add drop bonus
        playerScore.addClaudronDropBonus();
        
        // Use flask
        playerScore.useFlask();
        
        assertEquals(1, playerScore.getRubyCount());
        assertEquals(20, playerScore.getVictoryPoints());
        assertEquals(1, playerScore.getDropBonus());
        assertFalse(playerScore.getClaudronPlayersData().isFlaskFilled());
    }
}
