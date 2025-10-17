package com.example.quacksbag.baserules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RoundFinishManagerTest {
    
    @Test
    void testCalculateVictoryPointsForPosition() {
        RoundFinishManager manager = new RoundFinishManager(null, null, null, null, 0);
        assertEquals(0, manager.calculateVictoryPointsForPosition(0));
        assertEquals(0, manager.calculateVictoryPointsForPosition(4));
        assertEquals(1, manager.calculateVictoryPointsForPosition(5));
        assertEquals(1, manager.calculateVictoryPointsForPosition(9));
        assertEquals(2, manager.calculateVictoryPointsForPosition(10));
        assertEquals(2, manager.calculateVictoryPointsForPosition(14));
        assertEquals(3, manager.calculateVictoryPointsForPosition(15));
        assertEquals(3, manager.calculateVictoryPointsForPosition(19));
        assertEquals(4, manager.calculateVictoryPointsForPosition(20));
    }
}
