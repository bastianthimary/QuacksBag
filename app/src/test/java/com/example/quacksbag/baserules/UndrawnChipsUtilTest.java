package com.example.quacksbag.baserules;

import static org.junit.jupiter.api.Assertions.*;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UndrawnChipsUtilTest {

    private List<Chip> testChips;

    @BeforeEach
    void setUp() {
        testChips = new ArrayList<>();
        // Add white chips
        testChips.add(new Chip(ChipColor.WHITE, 1));
        testChips.add(new Chip(ChipColor.WHITE, 2));
        testChips.add(new Chip(ChipColor.WHITE, 3));
        // Add colored chips
        testChips.add(new Chip(ChipColor.ORANGE, 1));
        testChips.add(new Chip(ChipColor.GREEN, 2));
        testChips.add(new Chip(ChipColor.BLUE, 3));
        testChips.add(new Chip(ChipColor.RED, 4));
        testChips.add(new Chip(ChipColor.YELLOW, 2));
        testChips.add(new Chip(ChipColor.PURPLE, 1));
        testChips.add(new Chip(ChipColor.BLACK, 5));
    }

    // ========== Tests for findAllNonWhiteChips ==========

    @Test
    void testFindAllNonWhiteChips_WithMixedChips() {
        List<Chip> result = UndrawnChipsUtil.findAllNonWhiteChips(testChips);
        assertEquals(7, result.size());
        for (Chip chip : result) {
            assertNotEquals(ChipColor.WHITE, chip.getColor());
        }
    }

    @Test
    void testFindAllNonWhiteChips_OnlyWhiteChips() {
        List<Chip> whiteOnly = Arrays.asList(
                new Chip(ChipColor.WHITE, 1),
                new Chip(ChipColor.WHITE, 2)
        );
        List<Chip> result = UndrawnChipsUtil.findAllNonWhiteChips(whiteOnly);
        assertEquals(0, result.size());
    }

    @Test
    void testFindAllNonWhiteChips_NoWhiteChips() {
        List<Chip> noWhite = Arrays.asList(
                new Chip(ChipColor.ORANGE, 1),
                new Chip(ChipColor.GREEN, 2)
        );
        List<Chip> result = UndrawnChipsUtil.findAllNonWhiteChips(noWhite);
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllNonWhiteChips_EmptyList() {
        List<Chip> result = UndrawnChipsUtil.findAllNonWhiteChips(new ArrayList<>());
        assertEquals(0, result.size());
    }

    @Test
    void testFindAllNonWhiteChips_NullList() {
        List<Chip> result = UndrawnChipsUtil.findAllNonWhiteChips(null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    // ========== Tests for findChipsByColor ==========

    @Test
    void testFindChipsByColor_ExistingColor() {
        List<Chip> result = UndrawnChipsUtil.findChipsByColor(testChips, ChipColor.WHITE);
        assertEquals(3, result.size());
        for (Chip chip : result) {
            assertEquals(ChipColor.WHITE, chip.getColor());
        }
    }

    @Test
    void testFindChipsByColor_NonExistingColor() {
        List<Chip> whiteOnly = Arrays.asList(new Chip(ChipColor.WHITE, 1));
        List<Chip> result = UndrawnChipsUtil.findChipsByColor(whiteOnly, ChipColor.BLUE);
        assertEquals(0, result.size());
    }

    @Test
    void testFindChipsByColor_NullColor() {
        List<Chip> result = UndrawnChipsUtil.findChipsByColor(testChips, null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testFindChipsByColor_NullList() {
        List<Chip> result = UndrawnChipsUtil.findChipsByColor(null, ChipColor.RED);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testFindChipsByColor_EmptyList() {
        List<Chip> result = UndrawnChipsUtil.findChipsByColor(new ArrayList<>(), ChipColor.RED);
        assertEquals(0, result.size());
    }




    // ========== Tests for countChipsByColor ==========

    @Test
    void testCountChipsByColor_MixedChips() {
        Map<ChipColor, Integer> result = UndrawnChipsUtil.countChipsByColor(testChips);
        assertEquals(Integer.valueOf(3), result.get(ChipColor.WHITE));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.ORANGE));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.GREEN));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.BLUE));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.RED));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.YELLOW));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.PURPLE));
        assertEquals(Integer.valueOf(1), result.get(ChipColor.BLACK));
    }

    @Test
    void testCountChipsByColor_EmptyList() {
        Map<ChipColor, Integer> result = UndrawnChipsUtil.countChipsByColor(new ArrayList<>());
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testCountChipsByColor_NullList() {
        Map<ChipColor, Integer> result = UndrawnChipsUtil.countChipsByColor(null);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testCountChipsByColor_SingleColor() {
        List<Chip> singleColor = Arrays.asList(
                new Chip(ChipColor.RED, 1),
                new Chip(ChipColor.RED, 2),
                new Chip(ChipColor.RED, 3)
        );
        Map<ChipColor, Integer> result = UndrawnChipsUtil.countChipsByColor(singleColor);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(ChipColor.RED));
    }


    // ========== Tests for calculateTotalValue ==========

    @Test
    void testCalculateTotalValue_MixedChips() {
        int result = UndrawnChipsUtil.calculateTotalValue(testChips);
        // WHITE: 1+2+3=6, ORANGE:1, GREEN:2, BLUE:3, RED:4, YELLOW:2, PURPLE:1, BLACK:5
        // Total: 6+1+2+3+4+2+1+5 = 24
        assertEquals(24, result);
    }

    @Test
    void testCalculateTotalValue_EmptyList() {
        int result = UndrawnChipsUtil.calculateTotalValue(new ArrayList<>());
        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalValue_NullList() {
        int result = UndrawnChipsUtil.calculateTotalValue(null);
        assertEquals(0, result);
    }

    @Test
    void testCalculateTotalValue_SingleChip() {
        List<Chip> single = List.of(new Chip(ChipColor.RED, 5));
        int result = UndrawnChipsUtil.calculateTotalValue(single);
        assertEquals(5, result);
    }


    // ========== Tests for containsChipOfColor ==========

    @Test
    void testContainsChipOfColor_ExistingColor() {
        boolean result = UndrawnChipsUtil.containsChipOfColor(testChips, ChipColor.RED);
        assertTrue(result);
    }

    @Test
    void testContainsChipOfColor_NonExistingColor() {
        List<Chip> whiteOnly = List.of(new Chip(ChipColor.WHITE, 1));
        boolean result = UndrawnChipsUtil.containsChipOfColor(whiteOnly, ChipColor.RED);
        assertFalse(result);
    }

    @Test
    void testContainsChipOfColor_NullColor() {
        boolean result = UndrawnChipsUtil.containsChipOfColor(testChips, null);
        assertFalse(result);
    }

    @Test
    void testContainsChipOfColor_NullList() {
        boolean result = UndrawnChipsUtil.containsChipOfColor(null, ChipColor.RED);
        assertFalse(result);
    }

    @Test
    void testContainsChipOfColor_EmptyList() {
        boolean result = UndrawnChipsUtil.containsChipOfColor(new ArrayList<>(), ChipColor.RED);
        assertFalse(result);
    }


}
