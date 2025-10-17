package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComboResultTest {

    @Test
    void testComboResultWithTwoChips() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        
        ComboResult result = new ComboResult(price1, price2, 5, 10);
        
        assertEquals(price1, result.getChip1());
        assertEquals(price2, result.getChip2());
        assertEquals(5, result.getTotalValue());
        assertEquals(10, result.getTotalCost());
    }

    @Test
    void testComboResultWithOneChip() {
        Chip chip = new Chip(ChipColor.GREEN, 1);
        ChipPrice price = new ChipPrice(chip, 2);
        
        ComboResult result = new ComboResult(price, null, 1, 2);
        
        assertEquals(price, result.getChip1());
        assertNull(result.getChip2());
        assertEquals(1, result.getTotalValue());
        assertEquals(2, result.getTotalCost());
    }

    @Test
    void testGetChipCountWithTwoChips() {
        Chip chip1 = new Chip(ChipColor.ORANGE, 1);
        Chip chip2 = new Chip(ChipColor.YELLOW, 2);
        ChipPrice price1 = new ChipPrice(chip1, 3);
        ChipPrice price2 = new ChipPrice(chip2, 5);
        
        ComboResult result = new ComboResult(price1, price2, 3, 8);
        
        assertEquals(2, result.getChipCount());
    }

    @Test
    void testGetChipCountWithOneChip() {
        Chip chip = new Chip(ChipColor.PURPLE, 4);
        ChipPrice price = new ChipPrice(chip, 8);
        
        ComboResult result = new ComboResult(price, null, 4, 8);
        
        assertEquals(1, result.getChipCount());
    }

    @Test
    void testComboResultWithZeroValues() {
        Chip chip = new Chip(ChipColor.WHITE, 0);
        ChipPrice price = new ChipPrice(chip, 0);
        
        ComboResult result = new ComboResult(price, null, 0, 0);
        
        assertEquals(0, result.getTotalValue());
        assertEquals(0, result.getTotalCost());
    }

    @Test
    void testComboResultToString() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        
        ComboResult result = new ComboResult(price1, null, 2, 4);
        String str = result.toString();
        
        assertNotNull(str);
        assertTrue(str.contains("Wert=2"));
        assertTrue(str.contains("Kosten=4"));
    }

    @Test
    void testComboResultWithHighValues() {
        Chip chip1 = new Chip(ChipColor.BLACK, 5);
        Chip chip2 = new Chip(ChipColor.RED, 4);
        ChipPrice price1 = new ChipPrice(chip1, 10);
        ChipPrice price2 = new ChipPrice(chip2, 8);
        
        ComboResult result = new ComboResult(price1, price2, 9, 18);
        
        assertEquals(9, result.getTotalValue());
        assertEquals(18, result.getTotalCost());
        assertEquals(2, result.getChipCount());
    }

    @Test
    void testMultipleComboResults() {
        Chip chip1 = new Chip(ChipColor.WHITE, 1);
        Chip chip2 = new Chip(ChipColor.BLUE, 2);
        ChipPrice price1 = new ChipPrice(chip1, 2);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        
        ComboResult result1 = new ComboResult(price1, null, 1, 2);
        ComboResult result2 = new ComboResult(price1, price2, 3, 6);
        
        assertEquals(1, result1.getChipCount());
        assertEquals(2, result2.getChipCount());
        assertNotEquals(result1.getTotalValue(), result2.getTotalValue());
    }
}
