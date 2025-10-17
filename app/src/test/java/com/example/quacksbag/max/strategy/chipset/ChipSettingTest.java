package com.example.quacksbag.max.strategy.chipset;

import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChipSettingTest {

    @Test
    void testChipSettingCreation() {
        ChipSetting setting = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.FIX_MAX_COUNT, 5, true);
        
        assertNotNull(setting);
        assertEquals(ChipColor.BLUE, setting.getChipColor());
        assertEquals(ChipSettingWeights.FIX_MAX_COUNT, setting.getChipSettingWeight());
        assertEquals(5, setting.getMaxAmount());
        assertTrue(setting.isPriorizeFirst());
    }

    @Test
    void testChipSettingWithoutPriority() {
        ChipSetting setting = new ChipSetting(ChipColor.RED, ChipSettingWeights.REST_TILL_END, 10, false);
        
        assertEquals(ChipColor.RED, setting.getChipColor());
        assertEquals(ChipSettingWeights.REST_TILL_END, setting.getChipSettingWeight());
        assertEquals(10, setting.getMaxAmount());
        assertFalse(setting.isPriorizeFirst());
    }

    @Test
    void testChipSettingWithNegativeMaxAmount() {
        ChipSetting setting = new ChipSetting(ChipColor.GREEN, ChipSettingWeights.AFTERREACH_END, -1, false);
        
        assertEquals(-1, setting.getMaxAmount());
    }

    @Test
    void testCompareToWithBothFixMaxCountAndPriorizeFirst() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.FIX_MAX_COUNT, 5, true);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.FIX_MAX_COUNT, 3, true);
        
        assertEquals(0, setting1.compareTo(setting2));
    }

    @Test
    void testCompareToFixMaxCountPriorizeFirstVsOther() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.FIX_MAX_COUNT, 5, true);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.REST_TILL_END, 3, false);
        
        assertTrue(setting1.compareTo(setting2) < 0);
        assertTrue(setting2.compareTo(setting1) > 0);
    }

    @Test
    void testCompareToFixMaxCountVsRestTillEnd() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.FIX_MAX_COUNT, 5, false);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.REST_TILL_END, 3, false);
        
        assertTrue(setting1.compareTo(setting2) < 0);
        assertTrue(setting2.compareTo(setting1) > 0);
    }

    @Test
    void testCompareToRestTillEndVsAfterReachEnd() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.REST_TILL_END, 5, false);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.AFTERREACH_END, 3, false);
        
        assertTrue(setting1.compareTo(setting2) < 0);
        assertTrue(setting2.compareTo(setting1) > 0);
    }

    @Test
    void testCompareToBothAfterReachEnd() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.AFTERREACH_END, 5, false);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.AFTERREACH_END, 3, false);
        
        assertEquals(0, setting1.compareTo(setting2));
    }

    @Test
    void testCompareToBothRestTillEnd() {
        ChipSetting setting1 = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.REST_TILL_END, 5, false);
        ChipSetting setting2 = new ChipSetting(ChipColor.RED, ChipSettingWeights.REST_TILL_END, 3, false);
        
        assertEquals(0, setting1.compareTo(setting2));
    }

    @Test
    void testGetters() {
        ChipSetting setting = new ChipSetting(ChipColor.YELLOW, ChipSettingWeights.FIX_MAX_COUNT, 7, true);
        
        assertEquals(ChipColor.YELLOW, setting.getChipColor());
        assertEquals(ChipSettingWeights.FIX_MAX_COUNT, setting.getChipSettingWeight());
        assertEquals(7, setting.getMaxAmount());
        assertTrue(setting.isPriorizeFirst());
    }

    @Test
    void testAllChipColors() {
        ChipSetting white = new ChipSetting(ChipColor.WHITE, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting orange = new ChipSetting(ChipColor.ORANGE, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting green = new ChipSetting(ChipColor.GREEN, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting blue = new ChipSetting(ChipColor.BLUE, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting red = new ChipSetting(ChipColor.RED, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting yellow = new ChipSetting(ChipColor.YELLOW, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting purple = new ChipSetting(ChipColor.PURPLE, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        ChipSetting black = new ChipSetting(ChipColor.BLACK, ChipSettingWeights.FIX_MAX_COUNT, 1, false);
        
        assertEquals(ChipColor.WHITE, white.getChipColor());
        assertEquals(ChipColor.ORANGE, orange.getChipColor());
        assertEquals(ChipColor.GREEN, green.getChipColor());
        assertEquals(ChipColor.BLUE, blue.getChipColor());
        assertEquals(ChipColor.RED, red.getChipColor());
        assertEquals(ChipColor.YELLOW, yellow.getChipColor());
        assertEquals(ChipColor.PURPLE, purple.getChipColor());
        assertEquals(ChipColor.BLACK, black.getChipColor());
    }
}
