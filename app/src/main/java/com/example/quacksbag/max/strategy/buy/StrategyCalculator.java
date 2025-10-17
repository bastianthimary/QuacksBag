package com.example.quacksbag.max.strategy.buy;

import static com.example.quacksbag.max.strategy.buy.ComboResultUtil.determineHigherCost;
import static com.example.quacksbag.max.strategy.buy.ComboResultUtil.determineMaxScore;
import static com.example.quacksbag.max.strategy.buy.ComboResultUtil.isEqualChipCount;
import static com.example.quacksbag.max.strategy.buy.ComboResultUtil.determinePriorityColorRelevant;

import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrategyCalculator {
    private final List<ChipPrice> chipPrices;
   private final ComboResultWeight comboResultWeight;
   private ChipColor priorityColor;
   private boolean maxOneOfPriorityColor;

    public StrategyCalculator(List<ChipPrice> chips, ComboResultWeight comboResultWeight) {
        this.chipPrices = chips;
        this.comboResultWeight = comboResultWeight;
    }
    public StrategyCalculator(List<ChipPrice> chips, ComboResultWeight comboResultWeight, ChipColor priorityColor) {
        this.chipPrices = chips;
        this.comboResultWeight = comboResultWeight;
        this.priorityColor=priorityColor;
    }

    public StrategyCalculator(List<ChipPrice> chipPrices, ComboResultWeight comboResultWeight, ChipColor priorityColor, boolean maxOneOfPriorityColor) {
        this.chipPrices = chipPrices;
        this.comboResultWeight = comboResultWeight;
        this.priorityColor = priorityColor;
        this.maxOneOfPriorityColor = maxOneOfPriorityColor;
    }

    public Map<Integer,BuyStrategy> calculateStrategiesForBudgets(
            int maxBudget
    ) {
        // 1. Alle möglichen Käufe generieren
        List<ComboResult> allResults =
                ChipComboCalculator.calculateAllCombosForBudget(chipPrices, maxBudget);

        // 2. Gruppieren nach Kosten: bester Kauf für genau diesen Preis

        Map<Integer, ComboResult> bestPerCost = determineBestOnEachPricePoint(comboResultWeight, allResults);
        bestPerCost = determineBestOnPricePointTillNow(comboResultWeight, bestPerCost);

        // 3. Für jedes Budget <= maxBudget den besten bezahlbaren Kauf bestimmen
        Map<Integer, BuyStrategy> strategies = new HashMap<>();
        BuyStrategy lastUsedBuyStrategy = null;

        for (int budget = 1; budget <= maxBudget; budget++) {
            ComboResult bestPerBudget = bestPerCost.get(budget);
            if (bestPerBudget == null) {
                if (lastUsedBuyStrategy != null) {
                    strategies.put(budget, lastUsedBuyStrategy);
                }
            } else {
                lastUsedBuyStrategy = ComboResultToBuyStrategyMapper.mapToBuyStrategy(bestPerBudget);
                strategies.put(budget, lastUsedBuyStrategy);
            }
        }

        return strategies;
    }

    private Map<Integer, ComboResult> determineBestOnPricePointTillNow(ComboResultWeight comboResultWeight, Map<Integer, ComboResult> bestPerCost) {
        ComboResult bestTillNow = null;
        Map<Integer, ComboResult> bestPerCostTillNow = new HashMap<>();
        for (Integer i : bestPerCost.keySet()) {
            var currentCombo = bestPerCost.get(i);
            if (bestTillNow == null) {
                bestTillNow = currentCombo;
            } else {
                bestTillNow = determineBestCombo(bestTillNow, currentCombo, comboResultWeight);
            }
            bestPerCostTillNow.put(i, bestTillNow);
        }
        return bestPerCostTillNow;
    }

    private Map<Integer, ComboResult> determineBestOnEachPricePoint(ComboResultWeight comboResultWeight, List<ComboResult> allResults) {
        Map<Integer, ComboResult> bestPerCost = new HashMap<>();
        for (ComboResult result : allResults) {
            int cost = result.getTotalCost();
            bestPerCost.merge(cost, result, (oldRes, newRes) -> determineBestCombo(oldRes, newRes, comboResultWeight));
        }
        return bestPerCost;
    }



    private ComboResult determineBestCombo(
            ComboResult a,
            ComboResult b,
            ComboResultWeight comboResultWeight
    ) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (priorityColor != null) {
            var priorityColorRelevant = determinePriorityColorRelevant(a, b, priorityColor, maxOneOfPriorityColor);
            if (priorityColorRelevant.isPriorityColorRelevant()) {
                return priorityColorRelevant.getPriorComboResult();
            }
        }

        boolean resultsHaveEqualChipCount = isEqualChipCount(a, b);
        if (ComboResultWeight.MAX_CHIPNUMBER.equals(comboResultWeight)) {
            if (resultsHaveEqualChipCount) {
                return determineHigherCost(a, b);
            } else {
                return a.getChipCount() == 2 ? a : b;
            }
        } else if (ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE.equals(comboResultWeight)) {
            if (resultsHaveEqualChipCount) {
                return determineMaxScore(a, b);
            } else {
                return a.getChipCount() == 2 ? a : b;
            }
        }
        if (ComboResultWeight.MAX_SCORE.equals(comboResultWeight)) {
            return determineMaxScore(a, b);
        }
        return determineHigherCost(a, b);
    }


}
