package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComboResultUtilTest {

    @Test
    void testDetermineColorCountWithOneMatchingChip() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        ChipPrice price = new ChipPrice(chip, 4);
        ComboResult combo = new ComboResult(price, null, 2, 4);
        
        int count = ComboResultUtil.determineColorCount(combo, ChipColor.BLUE);
        
        assertEquals(1, count);
    }

    @Test
    void testDetermineColorCountWithNoMatchingChip() {
        Chip chip = new Chip(ChipColor.RED, 3);
        ChipPrice price = new ChipPrice(chip, 6);
        ComboResult combo = new ComboResult(price, null, 3, 6);
        
        int count = ComboResultUtil.determineColorCount(combo, ChipColor.BLUE);
        
        assertEquals(0, count);
    }

    @Test
    void testDetermineColorCountWithTwoMatchingChips() {
        Chip chip1 = new Chip(ChipColor.GREEN, 1);
        Chip chip2 = new Chip(ChipColor.GREEN, 2);
        ChipPrice price1 = new ChipPrice(chip1, 2);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        ComboResult combo = new ComboResult(price1, price2, 3, 6);
        
        int count = ComboResultUtil.determineColorCount(combo, ChipColor.GREEN);
        
        assertEquals(2, count);
    }

    @Test
    void testDetermineColorCountWithOneMatchingInTwoChips() {
        Chip chip1 = new Chip(ChipColor.ORANGE, 1);
        Chip chip2 = new Chip(ChipColor.BLUE, 2);
        ChipPrice price1 = new ChipPrice(chip1, 2);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        ComboResult combo = new ComboResult(price1, price2, 3, 6);
        
        int count = ComboResultUtil.determineColorCount(combo, ChipColor.ORANGE);
        
        assertEquals(1, count);
    }

    @Test
    void testDetermineMaxScoreWithDifferentValues() {
        Chip chip1 = new Chip(ChipColor.BLUE, 3);
        Chip chip2 = new Chip(ChipColor.RED, 2);
        ChipPrice price1 = new ChipPrice(chip1, 6);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        
        ComboResult comboA = new ComboResult(price1, null, 3, 6);
        ComboResult comboB = new ComboResult(price2, null, 2, 4);
        
        ComboResult result = ComboResultUtil.determineMaxScore(comboA, comboB);
        
        assertEquals(comboA, result);
    }

    @Test
    void testDetermineMaxScoreWithEqualValuesPreferHigherCost() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 2);
        ChipPrice price1 = new ChipPrice(chip1, 6);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 6);
        ComboResult comboB = new ComboResult(price2, null, 2, 4);
        
        ComboResult result = ComboResultUtil.determineMaxScore(comboA, comboB);
        
        assertEquals(comboA, result);
    }

    @Test
    void testDetermineHigherCost() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 2);
        ChipPrice price1 = new ChipPrice(chip1, 8);
        ChipPrice price2 = new ChipPrice(chip2, 5);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 8);
        ComboResult comboB = new ComboResult(price2, null, 2, 5);
        
        ComboResult result = ComboResultUtil.determineHigherCost(comboA, comboB);
        
        assertEquals(comboA, result);
    }

    @Test
    void testIsEqualChipCountTrue() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 4);
        ComboResult comboB = new ComboResult(price2, null, 3, 6);
        
        assertTrue(ComboResultUtil.isEqualChipCount(comboA, comboB));
    }

    @Test
    void testIsEqualChipCountFalse() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        Chip chip3 = new Chip(ChipColor.GREEN, 1);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        ChipPrice price3 = new ChipPrice(chip3, 2);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 4);
        ComboResult comboB = new ComboResult(price2, price3, 4, 8);
        
        assertFalse(ComboResultUtil.isEqualChipCount(comboA, comboB));
    }

    @Test
    void testDeterminePriorityColorRelevantWithSameCount() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.BLUE, 3);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 4);
        ComboResult comboB = new ComboResult(price2, null, 3, 6);
        
        PriorityColorRelevant result = ComboResultUtil.determinePriorityColorRelevant(
            comboA, comboB, ChipColor.BLUE, false);
        
        assertFalse(result.isPriorityColorRelevant());
        assertNull(result.getPriorComboResult());
    }

    @Test
    void testDeterminePriorityColorRelevantWithDifferentCount() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        
        ComboResult comboA = new ComboResult(price1, null, 2, 4);
        ComboResult comboB = new ComboResult(price2, null, 3, 6);
        
        PriorityColorRelevant result = ComboResultUtil.determinePriorityColorRelevant(
            comboA, comboB, ChipColor.BLUE, false);
        
        assertTrue(result.isPriorityColorRelevant());
        assertEquals(comboA, result.getPriorComboResult());
    }

}
