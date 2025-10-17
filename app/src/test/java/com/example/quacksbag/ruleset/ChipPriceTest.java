package com.example.quacksbag.ruleset;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChipPriceTest {

    @Test
    void testChipPriceCreation() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        ChipPrice chipPrice = new ChipPrice(chip, 5);
        
        assertNotNull(chipPrice);
        assertEquals(chip, chipPrice.getChip());
        assertEquals(5, chipPrice.getPrice());
    }

    @Test
    void testChipPriceWithDifferentPrices() {
        Chip chip1 = new Chip(ChipColor.WHITE, 1);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        
        ChipPrice price1 = new ChipPrice(chip1, 1);
        ChipPrice price2 = new ChipPrice(chip2, 10);
        
        assertEquals(1, price1.getPrice());
        assertEquals(10, price2.getPrice());
    }

    @Test
    void testChipPriceWithZeroPrice() {
        Chip chip = new Chip(ChipColor.GREEN, 1);
        ChipPrice chipPrice = new ChipPrice(chip, 0);
        
        assertEquals(0, chipPrice.getPrice());
    }

    @Test
    void testChipPriceWithHighPrice() {
        Chip chip = new Chip(ChipColor.PURPLE, 4);
        ChipPrice chipPrice = new ChipPrice(chip, 100);
        
        assertEquals(100, chipPrice.getPrice());
    }

    @Test
    void testChipPriceGetChipReturnsCorrectChip() {
        Chip chip = new Chip(ChipColor.ORANGE, 2);
        ChipPrice chipPrice = new ChipPrice(chip, 7);
        
        Chip retrievedChip = chipPrice.getChip();
        assertEquals(ChipColor.ORANGE, retrievedChip.getColor());
        assertEquals(2, retrievedChip.getValue());
    }

    @Test
    void testMultipleChipPrices() {
        Chip whiteChip = new Chip(ChipColor.WHITE, 1);
        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        Chip redChip = new Chip(ChipColor.RED, 3);
        
        ChipPrice price1 = new ChipPrice(whiteChip, 2);
        ChipPrice price2 = new ChipPrice(blueChip, 4);
        ChipPrice price3 = new ChipPrice(redChip, 6);
        
        assertEquals(2, price1.getPrice());
        assertEquals(4, price2.getPrice());
        assertEquals(6, price3.getPrice());
    }
}
