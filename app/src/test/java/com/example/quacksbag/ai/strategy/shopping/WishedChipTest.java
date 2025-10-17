package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WishedChipTest {

    @Test
    void testWishedChipWithColorOnly() {
        WishedChip wishedChip = new WishedChip(ChipColor.BLUE);
        
        assertEquals(ChipColor.BLUE, wishedChip.getChipColor());
        assertFalse(wishedChip.isLimited());
        assertEquals(-1, wishedChip.getNumberOfChips());
    }

    @Test
    void testWishedChipWithNumberAndColor() {
        WishedChip wishedChip = new WishedChip(3, ChipColor.RED);
        
        assertEquals(ChipColor.RED, wishedChip.getChipColor());
        assertTrue(wishedChip.isLimited());
        assertEquals(3, wishedChip.getNumberOfChips());
    }

    @Test
    void testIsLimitedReturnsFalseWhenNoLimit() {
        WishedChip wishedChip = new WishedChip(ChipColor.GREEN);
        
        assertFalse(wishedChip.isLimited());
    }

    @Test
    void testIsLimitedReturnsTrueWhenLimited() {
        WishedChip wishedChip = new WishedChip(5, ChipColor.ORANGE);
        
        assertTrue(wishedChip.isLimited());
    }

    @Test
    void testWishedChipWithZeroLimit() {
        WishedChip wishedChip = new WishedChip(0, ChipColor.YELLOW);
        
        assertTrue(wishedChip.isLimited());
        assertEquals(0, wishedChip.getNumberOfChips());
    }

    @Test
    void testWishedChipWithOneLimit() {
        WishedChip wishedChip = new WishedChip(1, ChipColor.PURPLE);
        
        assertTrue(wishedChip.isLimited());
        assertEquals(1, wishedChip.getNumberOfChips());
    }

    @Test
    void testWishedChipWithHighLimit() {
        WishedChip wishedChip = new WishedChip(100, ChipColor.BLACK);
        
        assertTrue(wishedChip.isLimited());
        assertEquals(100, wishedChip.getNumberOfChips());
    }

    @Test
    void testMultipleWishedChipsWithDifferentColors() {
        WishedChip white = new WishedChip(ChipColor.WHITE);
        WishedChip blue = new WishedChip(2, ChipColor.BLUE);
        WishedChip red = new WishedChip(5, ChipColor.RED);
        
        assertEquals(ChipColor.WHITE, white.getChipColor());
        assertEquals(ChipColor.BLUE, blue.getChipColor());
        assertEquals(ChipColor.RED, red.getChipColor());
        
        assertFalse(white.isLimited());
        assertTrue(blue.isLimited());
        assertTrue(red.isLimited());
    }
}
