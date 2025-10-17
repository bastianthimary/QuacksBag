package com.example.quacksbag.gamematerial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClaudronUtilTest {
    @Test
    void testIsPositionARubyBubble(){
        assertFalse(ClaudronUtil.isPositionARubyBubble(1));
        assertTrue(ClaudronUtil.isPositionARubyBubble(5));
        assertTrue(ClaudronUtil.isPositionARubyBubble(9));

    }
    @Test
    void testCalcDistanceToNextRuby(){
        assertEquals(4,ClaudronUtil.calcDistanceToNextRuby(1));
        assertEquals(0,ClaudronUtil.calcDistanceToNextRuby(5));
        assertEquals(3,ClaudronUtil.calcDistanceToNextRuby(6));
        assertEquals(2,ClaudronUtil.calcDistanceToNextRuby(7));
        assertEquals(1,ClaudronUtil.calcDistanceToNextRuby(8));
        assertEquals(0,ClaudronUtil.calcDistanceToNextRuby(9));
    }

}