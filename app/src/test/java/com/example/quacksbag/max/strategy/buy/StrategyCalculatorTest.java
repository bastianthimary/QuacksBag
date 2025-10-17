package com.example.quacksbag.max.strategy.buy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.ruleset.ChipPrice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StrategyCalculatorTest {

    private ChipPrice createChipPrice(int value, ChipColor color, int price) {
        Chip chip = new Chip(color, value);
        return new ChipPrice(chip, price);
    }

    @Test
    public void testCalculateStrategies_emptyChipList() {
        List<ChipPrice> chips = new ArrayList<>();
        int maxBudget = 20;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        assertTrue(strategies.isEmpty(), "Strategies should be empty for an empty chip list");
    }

    @Test
    public void testCalculateStrategies_budgetTooSmall() {
        List<ChipPrice> chips = new ArrayList<>();
        chips.add(createChipPrice(1, ChipColor.BLUE, 10));

        int maxBudget = 9;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        assertTrue(strategies.isEmpty(), "Strategies should be empty if budget is too small");
    }

    @Test
    public void testCalculateStrategies_singleChip() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice blueChip = createChipPrice(1, ChipColor.BLUE, 10);
        chips.add(blueChip);

        int maxBudget = 15;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        assertEquals(6, strategies.size()); // for budgets 10, 11, 12, 13, 14, 15

        for (int budget = 1; budget < blueChip.getPrice(); budget++) {
            assertNull(strategies.get(budget), "Should be no strategy for budget " + budget);
        }

        for (int budget = blueChip.getPrice(); budget <= maxBudget; budget++) {
            BuyStrategy strategy = strategies.get(budget);
            assertEquals(blueChip.getChip(), strategy.getFirstChip());
            assertNull(strategy.getSecondChip());
        }
    }

    @Test
    public void testCalculateStrategies_multipleChips() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(2, ChipColor.WHITE, 4); // lower value, lower cost
        ChipPrice chipB = createChipPrice(3, ChipColor.RED, 10);   // higher value, higher cost
        chips.add(chipA);
        chips.add(chipB);

        int maxBudget = 12;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        assertEquals(strategies.get(10).getFirstChip(), chipA.getChip());
    }

    @Test
    public void testCalculateStrategies_choiceBetweenChips() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(1, ChipColor.WHITE, 5); // lower value, lower cost
        ChipPrice chipB = createChipPrice(2, ChipColor.RED, 8);   // higher value, higher cost
        chips.add(chipA);
        chips.add(chipB);

        int maxBudget = 12;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        // Expected:
        // Budget 1-4: no strategy
        // Budget 5-7: buy chip A (value 1)
        // Budget 8-12: buy chip B (value 2 is better)

        for (int budget = 1; budget < 5; budget++) {
            assertNull(strategies.get(budget), "Should be no strategy for budget " + budget);
        }

        for (int budget = 5; budget < 8; budget++) {
            BuyStrategy strategy = strategies.get(budget);
            assertEquals(chipA.getChip(), strategy.getFirstChip(), "Should buy chip A for budget " + budget);
            assertNull(strategy.getSecondChip());
        }

        for (int budget = 8; budget < 10; budget++) {
            BuyStrategy strategy = strategies.get(budget);
            assertEquals(chipB.getChip(), strategy.getFirstChip(), "Should buy chip B for budget " + budget);
            assertNull(strategy.getSecondChip());
        }
        for (int budget = 10; budget <= maxBudget; budget++) {
            BuyStrategy strategy = strategies.get(budget);
            assertEquals(chipA.getChip(), strategy.getFirstChip(), "Should buy chip A for budget " + budget);
            assertEquals(chipA.getChip(), strategy.getSecondChip());
        }
    }

    @Test
    public void testCalculateStrategies_priorityColor() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(1, ChipColor.BLUE, 4);
        ChipPrice chipB = createChipPrice(1, ChipColor.ORANGE, 5);
        chips.add(chipA);
        chips.add(chipB);
        int maxBudget = 5;

        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE, ChipColor.BLUE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);
        BuyStrategy strategyFor5 = strategies.get(5);
        assertEquals(chipA.getChip(), strategyFor5.getFirstChip());

    }

    @Test
    public void testCalculateStrategies_MaxScore() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(1, ChipColor.BLUE, 4);
        ChipPrice chipB = createChipPrice(1, ChipColor.ORANGE, 5);
        ChipPrice chipC = createChipPrice(4, ChipColor.RED, 10); // Best single chip
        chips.add(chipA);
        chips.add(chipB);
        chips.add(chipC);

        int maxBudget = 15;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);

        BuyStrategy strategyFor8 = strategies.get(8);
        assertEquals(chipA.getChip(), strategyFor8.getFirstChip());
        assertEquals(chipA.getChip(), strategyFor8.getSecondChip());

        BuyStrategy strategyFor9 = strategies.get(9);
        assertEquals(chipA.getChip(), strategyFor9.getFirstChip());
        assertEquals(chipB.getChip(), strategyFor9.getSecondChip());

        BuyStrategy strategyFor10 = strategies.get(10);
        assertEquals(chipC.getChip(), strategyFor10.getFirstChip());
        assertNull(strategyFor10.getSecondChip());
        BuyStrategy strategyFor11 = strategies.get(11);
        assertEquals(strategyFor11, strategyFor10);


        BuyStrategy strategyFor13 = strategies.get(13);
        assertEquals(chipC.getChip(), strategyFor13.getFirstChip());
        assertNull(strategyFor13.getSecondChip());

        BuyStrategy strategyFor14 = strategies.get(14);
        assertEquals(chipA.getChip(), strategyFor14.getFirstChip());
        assertEquals(chipC.getChip(), strategyFor14.getSecondChip());

        BuyStrategy strategyFor15 = strategies.get(15);
        assertEquals(chipB.getChip(), strategyFor15.getFirstChip());
        assertEquals(chipC.getChip(), strategyFor15.getSecondChip());
    }

    @Test
    public void testCalculateStrategies_MaxChipNumber() {
        List<ChipPrice> chips = new ArrayList<>();
        ChipPrice chipA = createChipPrice(1, ChipColor.BLUE, 4);
        ChipPrice chipB = createChipPrice(1, ChipColor.ORANGE, 5);
        ChipPrice chipC = createChipPrice(4, ChipColor.RED, 10); // Best single chip
        chips.add(chipA);
        chips.add(chipB);
        chips.add(chipC);

        int maxBudget = 15;
        StrategyCalculator StrategyCalculator = new StrategyCalculator(chips, ComboResultWeight.MAX_CHIPNUMBER);
        Map<Integer, BuyStrategy> strategies = StrategyCalculator.calculateStrategiesForBudgets( maxBudget);

        BuyStrategy strategyFor8 = strategies.get(8);
        assertEquals(chipA.getChip(), strategyFor8.getFirstChip());
        assertEquals(chipA.getChip(), strategyFor8.getSecondChip());

        BuyStrategy strategyFor9 = strategies.get(9);
        assertEquals(chipA.getChip(), strategyFor9.getFirstChip());
        assertEquals(chipB.getChip(), strategyFor9.getSecondChip());

        BuyStrategy strategyFor10 = strategies.get(10);
        assertEquals(chipB.getChip(), strategyFor10.getFirstChip());
        assertEquals(chipB.getChip(), strategyFor10.getSecondChip());
        BuyStrategy strategyFor11 = strategies.get(11);
        assertEquals(strategyFor11, strategyFor10);


        BuyStrategy strategyFor13 = strategies.get(13);
        assertEquals(chipB.getChip(), strategyFor13.getFirstChip());
        assertEquals(chipB.getChip(), strategyFor13.getSecondChip());

        BuyStrategy strategyFor14 = strategies.get(14);
        assertEquals(chipA.getChip(), strategyFor14.getFirstChip());
        assertEquals(chipC.getChip(), strategyFor14.getSecondChip());

        BuyStrategy strategyFor15 = strategies.get(15);
        assertEquals(chipB.getChip(), strategyFor15.getFirstChip());
        assertEquals(chipC.getChip(), strategyFor15.getSecondChip());
    }


}
