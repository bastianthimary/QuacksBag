package com.example.quacksbag.max.strategy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyStrategyTest {

    @Test
    void testBuyStrategyWithOneChip() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        BuyStrategy strategy = new BuyStrategy(chip);
        
        assertNotNull(strategy);
        assertEquals(chip, strategy.getFirstChip());
        assertNull(strategy.getSecondChip());
    }

    @Test
    void testBuyStrategyWithTwoChips() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        BuyStrategy strategy = new BuyStrategy(chip1, chip2);
        
        assertNotNull(strategy);
        assertEquals(chip1, strategy.getFirstChip());
        assertEquals(chip2, strategy.getSecondChip());
    }

    @Test
    void testGetFirstChipNotNull() {
        Chip chip = new Chip(ChipColor.GREEN, 1);
        BuyStrategy strategy = new BuyStrategy(chip);
        
        assertNotNull(strategy.getFirstChip());
    }

    @Test
    void testGetFirstChipReturnsCorrectChip() {
        Chip chip = new Chip(ChipColor.ORANGE, 2);
        BuyStrategy strategy = new BuyStrategy(chip);
        
        Chip retrieved = strategy.getFirstChip();
        assertEquals(ChipColor.ORANGE, retrieved.getColor());
        assertEquals(2, retrieved.getValue());
    }

    @Test
    void testGetSecondChipNullWhenNotProvided() {
        Chip chip = new Chip(ChipColor.YELLOW, 1);
        BuyStrategy strategy = new BuyStrategy(chip);
        
        assertNull(strategy.getSecondChip());
    }

    @Test
    void testGetSecondChipReturnsCorrectChip() {
        Chip chip1 = new Chip(ChipColor.PURPLE, 3);
        Chip chip2 = new Chip(ChipColor.BLACK, 4);
        BuyStrategy strategy = new BuyStrategy(chip1, chip2);
        
        Chip retrieved = strategy.getSecondChip();
        assertEquals(ChipColor.BLACK, retrieved.getColor());
        assertEquals(4, retrieved.getValue());
    }

    @Test
    void testBuyStrategyWithSameColorChips() {
        Chip chip1 = new Chip(ChipColor.BLUE, 1);
        Chip chip2 = new Chip(ChipColor.BLUE, 2);
        BuyStrategy strategy = new BuyStrategy(chip1, chip2);
        
        assertEquals(chip1, strategy.getFirstChip());
        assertEquals(chip2, strategy.getSecondChip());
    }

    @Test
    void testBuyStrategyWithDifferentColorChips() {
        Chip chip1 = new Chip(ChipColor.WHITE, 1);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        BuyStrategy strategy = new BuyStrategy(chip1, chip2);
        
        assertNotEquals(chip1.getColor(), chip2.getColor());
        assertEquals(chip1, strategy.getFirstChip());
        assertEquals(chip2, strategy.getSecondChip());
    }
}
