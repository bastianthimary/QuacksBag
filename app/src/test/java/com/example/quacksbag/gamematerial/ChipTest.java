package com.example.quacksbag.gamematerial;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChipTest {

    @Test
    void testChipCreation() {
        Chip chip = new Chip(ChipColor.WHITE, 1);

        assertEquals(ChipColor.WHITE, chip.getColor());
        assertEquals(1, chip.getValue());
    }

    @Test
    void testChipCreationWithDifferentColors() {
        Chip whiteChip = new Chip(ChipColor.WHITE, 1);
        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        Chip redChip = new Chip(ChipColor.RED, 3);
        
        assertEquals(ChipColor.WHITE, whiteChip.getColor());
        assertEquals(ChipColor.BLUE, blueChip.getColor());
        assertEquals(ChipColor.RED, redChip.getColor());
    }

    @Test
    void testChipCreationWithDifferentValues() {
        Chip chip1 = new Chip(ChipColor.WHITE, 1);
        Chip chip2 = new Chip(ChipColor.WHITE, 2);
        Chip chip3 = new Chip(ChipColor.WHITE, 3);
        
        assertEquals(1, chip1.getValue());
        assertEquals(2, chip2.getValue());
        assertEquals(3, chip3.getValue());
    }

    @Test
    void testChipCopy() {
        Chip original = new Chip(ChipColor.ORANGE, 2);
        Chip copy = original.copy();
        
        assertNotNull(copy);
        assertEquals(original.getColor(), copy.getColor());
        assertEquals(original.getValue(), copy.getValue());
    }

    @Test
    void testChipCopyCreatesNewInstance() {
        Chip original = new Chip(ChipColor.GREEN, 3);
        Chip copy = original.copy();
        
        assertNotSame(original, copy);
    }

    @Test
    void testChipToString() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        String result = chip.toString();
        
        assertTrue(result.contains("Blue"));
        assertTrue(result.contains("2"));
    }

    @Test
    void testAllChipColors() {
        Chip white = new Chip(ChipColor.WHITE, 1);
        Chip orange = new Chip(ChipColor.ORANGE, 1);
        Chip green = new Chip(ChipColor.GREEN, 1);
        Chip blue = new Chip(ChipColor.BLUE, 1);
        Chip red = new Chip(ChipColor.RED, 1);
        Chip yellow = new Chip(ChipColor.YELLOW, 1);
        Chip purple = new Chip(ChipColor.PURPLE, 1);
        Chip black = new Chip(ChipColor.BLACK, 1);
        
        assertEquals(ChipColor.WHITE, white.getColor());
        assertEquals(ChipColor.ORANGE, orange.getColor());
        assertEquals(ChipColor.GREEN, green.getColor());
        assertEquals(ChipColor.BLUE, blue.getColor());
        assertEquals(ChipColor.RED, red.getColor());
        assertEquals(ChipColor.YELLOW, yellow.getColor());
        assertEquals(ChipColor.PURPLE, purple.getColor());
        assertEquals(ChipColor.BLACK, black.getColor());
    }
}
