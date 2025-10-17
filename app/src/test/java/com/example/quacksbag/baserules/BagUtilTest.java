package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BagUtilTest {

    @Test
    void testDefaultStartingChipsNotNull() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        assertNotNull(chips);
    }

    @Test
    void testDefaultStartingChipsSize() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        assertEquals(9, chips.size());
    }

    @Test
    void testDefaultStartingChipsContainsFourWhiteOnes() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        long count = chips.stream()
                .filter(chip -> chip.getColor() == ChipColor.WHITE && chip.getValue() == 1)
                .count();
        
        assertEquals(4, count);
    }

    @Test
    void testDefaultStartingChipsContainsTwoWhiteTwos() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        long count = chips.stream()
                .filter(chip -> chip.getColor() == ChipColor.WHITE && chip.getValue() == 2)
                .count();
        
        assertEquals(2, count);
    }

    @Test
    void testDefaultStartingChipsContainsOneWhiteThree() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        long count = chips.stream()
                .filter(chip -> chip.getColor() == ChipColor.WHITE && chip.getValue() == 3)
                .count();
        
        assertEquals(1, count);
    }

    @Test
    void testDefaultStartingChipsContainsOneOrangeOne() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        long count = chips.stream()
                .filter(chip -> chip.getColor() == ChipColor.ORANGE && chip.getValue() == 1)
                .count();
        
        assertEquals(1, count);
    }

    @Test
    void testDefaultStartingChipsContainsOneGreenOne() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        long count = chips.stream()
                .filter(chip -> chip.getColor() == ChipColor.GREEN && chip.getValue() == 1)
                .count();
        
        assertEquals(1, count);
    }

    @Test
    void testDefaultStartingChipsColorDistribution() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        Map<ChipColor, Long> colorCounts = chips.stream()
                .collect(Collectors.groupingBy(Chip::getColor, Collectors.counting()));
        
        assertEquals(7, colorCounts.get(ChipColor.WHITE));
        assertEquals(1, colorCounts.get(ChipColor.ORANGE));
        assertEquals(1, colorCounts.get(ChipColor.GREEN));
    }


    @Test
    void testDefaultStartingChipsMultipleCalls() {
        List<Chip> chips1 = BagUtil.defaultStartingChips();
        List<Chip> chips2 = BagUtil.defaultStartingChips();
        
        assertEquals(chips1.size(), chips2.size());
        assertNotSame(chips1, chips2);
    }

    @Test
    void testDefaultStartingChipsNoBlueRedYellowPurpleBlack() {
        List<Chip> chips = BagUtil.defaultStartingChips();
        
        assertFalse(chips.stream().anyMatch(chip -> chip.getColor() == ChipColor.BLUE));
        assertFalse(chips.stream().anyMatch(chip -> chip.getColor() == ChipColor.RED));
        assertFalse(chips.stream().anyMatch(chip -> chip.getColor() == ChipColor.YELLOW));
        assertFalse(chips.stream().anyMatch(chip -> chip.getColor() == ChipColor.PURPLE));
        assertFalse(chips.stream().anyMatch(chip -> chip.getColor() == ChipColor.BLACK));
    }
}
