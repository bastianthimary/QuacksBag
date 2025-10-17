package com.example.quacksbag.max.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.buy.ChipComboCalculator;
import com.example.quacksbag.max.strategy.buy.ComboResult;
import com.example.quacksbag.ruleset.ChipPrice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



public class ChipComboCalculatorTest {

    // Helper method to create ChipPrice for tests
    private ChipPrice createChipPrice(int value, ChipColor color, int price) {
        Chip chip = new Chip( color,value); // Assuming a Chip constructor like this
        return new ChipPrice(chip, price);
    }

    @Test
    public void testCalculateAllCombos_ForBudget_emptyChipList() {
        List<ChipPrice> chips = new ArrayList<>();
        int budget = 10;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertTrue( results.isEmpty(),"Results should be empty for an empty chip list");
    }

    @Test
    public void testCalculateAllCombos_ForBudget_budgetTooSmall() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 5));
        chips.add(createChipPrice(2, ChipColor.RED, 8));

        int budget = 4;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertTrue( results.isEmpty(),"Results should be empty if budget is too small");
    }
    @Test
    public void testCalculateAllCombos_ForBudget_validBudget() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 5));
        chips.add(createChipPrice(2, ChipColor.RED, 8));

        int budget = 5;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertEquals( 1,results.size());
    }
    @Test
    public void testCalculateAllCombos_ForBudget_validHighBudget() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 5));
        chips.add(createChipPrice(2, ChipColor.RED, 8));

        int budget = 8;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertEquals( 2,results.size());
    }
    @Test
    public void testCalculateAllCombos_ForBudget_validHigh2Budget() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 5));
        chips.add(createChipPrice(2, ChipColor.RED, 8));

        int budget = 13;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertEquals( 4,results.size());
    }
    @Test
    public void testCalculateAllCombos_ForBudget_validHigh3Budget() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 5));
        chips.add(createChipPrice(2, ChipColor.BLUE, 10));
        chips.add(createChipPrice(4, ChipColor.BLUE, 19));
        chips.add(createChipPrice(1, ChipColor.RED, 6));
        chips.add(createChipPrice(2, ChipColor.RED, 10));
        chips.add(createChipPrice(4, ChipColor.RED, 16));

        int budget = 26;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, budget);
        assertEquals( 22,results.size());
    }
    @Test
    public void testCalculateAllCombos_ForBudget_validHigh4Budget() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(1, ChipColor.BLUE, 4);
        ChipPrice chipB = createChipPrice(1, ChipColor.ORANGE, 5);
        ChipPrice chipC = createChipPrice(4, ChipColor.RED, 10); // Best single chip
        chips.add(chipA);
        chips.add(chipB);
        chips.add(chipC);

        int maxBudget = 15;
        List<ComboResult> results = ChipComboCalculator.calculateAllCombosForBudget(chips, maxBudget);
        assertEquals( 8,results.size());
    }



}