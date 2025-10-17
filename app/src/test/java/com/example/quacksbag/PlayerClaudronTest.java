package com.example.quacksbag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerClaudronTest {

    private PlayerClaudron playerClaudron;

    @BeforeEach
    void setUp() {
        playerClaudron = new PlayerClaudron();
    }

    @Test
    void testPlayerClaudronCreation() {
        assertNotNull(playerClaudron);
    }

    @Test
    void testInitialDropPosition() {
        assertEquals(0, playerClaudron.getDropPositionForTesting());
    }

    @Test
    void testShiftDropOneForward() {
        playerClaudron.shiftDropOneForward();
        
        assertEquals(1, playerClaudron.getDropPositionForTesting());
    }

    @Test
    void testShiftDropMultipleTimes() {
        playerClaudron.shiftDropOneForward();
        playerClaudron.shiftDropOneForward();
        playerClaudron.shiftDropOneForward();
        
        assertEquals(3, playerClaudron.getDropPositionForTesting());
    }

    @Test
    void testShiftDropManyTimes() {
        for (int i = 0; i < 10; i++) {
            playerClaudron.shiftDropOneForward();
        }
        
        assertEquals(10, playerClaudron.getDropPositionForTesting());
    }

    @Test
    void testShiftDropIncrementsByOne() {
        int initialPosition = playerClaudron.getDropPositionForTesting();
        playerClaudron.shiftDropOneForward();
        int newPosition = playerClaudron.getDropPositionForTesting();
        
        assertEquals(initialPosition + 1, newPosition);
    }
}
