package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityColorRelevantTest {

    @Test
    void testPriorityColorRelevantCreation() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        ChipPrice price = new ChipPrice(chip, 4);
        ComboResult combo = new ComboResult(price, null, 2, 4);
        
        PriorityColorRelevant result = new PriorityColorRelevant(true, combo);
        
        assertNotNull(result);
        assertTrue(result.isPriorityColorRelevant());
        assertEquals(combo, result.getPriorComboResult());
    }

    @Test
    void testPriorityColorRelevantWithFalse() {
        PriorityColorRelevant result = new PriorityColorRelevant(false, null);
        
        assertFalse(result.isPriorityColorRelevant());
        assertNull(result.getPriorComboResult());
    }

    @Test
    void testPriorityColorRelevantWithNullCombo() {
        PriorityColorRelevant result = new PriorityColorRelevant(true, null);
        
        assertTrue(result.isPriorityColorRelevant());
        assertNull(result.getPriorComboResult());
    }

    @Test
    void testPriorityColorRelevantGetters() {
        Chip chip1 = new Chip(ChipColor.RED, 3);
        Chip chip2 = new Chip(ChipColor.BLUE, 2);
        ChipPrice price1 = new ChipPrice(chip1, 6);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        ComboResult combo = new ComboResult(price1, price2, 5, 10);
        
        PriorityColorRelevant result = new PriorityColorRelevant(true, combo);
        
        assertTrue(result.isPriorityColorRelevant());
        assertNotNull(result.getPriorComboResult());
        assertEquals(5, result.getPriorComboResult().getTotalValue());
        assertEquals(10, result.getPriorComboResult().getTotalCost());
    }
}
