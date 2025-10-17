package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.ruleset.ChipPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComboResultToBuyStrategyMapperTest {

    @Test
    void testMapToBuyStrategyWithOneChip() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        ChipPrice price = new ChipPrice(chip, 4);
        ComboResult comboResult = new ComboResult(price, null, 2, 4);
        
        BuyStrategy strategy = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        
        assertNotNull(strategy);
        assertEquals(chip, strategy.getFirstChip());
        assertNull(strategy.getSecondChip());
    }

    @Test
    void testMapToBuyStrategyWithTwoChips() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        ChipPrice price1 = new ChipPrice(chip1, 4);
        ChipPrice price2 = new ChipPrice(chip2, 6);
        ComboResult comboResult = new ComboResult(price1, price2, 5, 10);
        
        BuyStrategy strategy = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        
        assertNotNull(strategy);
        assertEquals(chip1, strategy.getFirstChip());
        assertEquals(chip2, strategy.getSecondChip());
    }

    @Test
    void testMapToBuyStrategyPreservesChipProperties() {
        Chip chip = new Chip(ChipColor.GREEN, 3);
        ChipPrice price = new ChipPrice(chip, 6);
        ComboResult comboResult = new ComboResult(price, null, 3, 6);
        
        BuyStrategy strategy = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        
        assertEquals(ChipColor.GREEN, strategy.getFirstChip().getColor());
        assertEquals(3, strategy.getFirstChip().getValue());
    }

    @Test
    void testMapToBuyStrategyWithTwoChipsPreservesProperties() {
        Chip chip1 = new Chip(ChipColor.ORANGE, 1);
        Chip chip2 = new Chip(ChipColor.YELLOW, 2);
        ChipPrice price1 = new ChipPrice(chip1, 2);
        ChipPrice price2 = new ChipPrice(chip2, 4);
        ComboResult comboResult = new ComboResult(price1, price2, 3, 6);
        
        BuyStrategy strategy = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        
        assertEquals(ChipColor.ORANGE, strategy.getFirstChip().getColor());
        assertEquals(1, strategy.getFirstChip().getValue());
        assertEquals(ChipColor.YELLOW, strategy.getSecondChip().getColor());
        assertEquals(2, strategy.getSecondChip().getValue());
    }

    @Test
    void testMapToBuyStrategyMultipleTimes() {
        Chip chip = new Chip(ChipColor.PURPLE, 4);
        ChipPrice price = new ChipPrice(chip, 8);
        ComboResult comboResult = new ComboResult(price, null, 4, 8);
        
        BuyStrategy strategy1 = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        BuyStrategy strategy2 = ComboResultToBuyStrategyMapper.mapToBuyStrategy(comboResult);
        
        assertNotSame(strategy1, strategy2);
        assertEquals(strategy1.getFirstChip(), strategy2.getFirstChip());
    }
}
