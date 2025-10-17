package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.logging.Logger;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;
import com.example.quacksbag.max.strategy.buy.StrategyCalculator;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomShoppingStrategy implements ShoppingStrategy {
    private final ComboResultWeight comboResultWeight;
    private final DetermineBuyableChipsByStrategy determineBuyableChipsByStrategy;

    public CustomShoppingStrategy(ComboResultWeight comboResultWeight, List<WishedChip> wantedChips) {
        this.comboResultWeight = comboResultWeight;
        this.determineBuyableChipsByStrategy = new DetermineBuyableChipsByStrategy(wantedChips);
    }

    @Override
    public List<Chip> decideShopping(GameManager gameManager, int bubbleValue, List<ChipPrice> buyableChips) {
        Logger.result("DecideShopping Round: " + gameManager.getCurrentRound() + " BubbleValue: " + bubbleValue + "");
        List<ChipPrice> filteredChipsByStrategy = determineBuyableChipsByStrategy.filterBuyableChips(gameManager, buyableChips);

        StrategyCalculator strategyCalculator = new StrategyCalculator(filteredChipsByStrategy, comboResultWeight);
        Map<Integer, BuyStrategy> strategiesForBudgets = strategyCalculator.calculateStrategiesForBudgets(bubbleValue);
        BuyStrategy buyStrategy = strategiesForBudgets.get(bubbleValue);
        return determineShoppingDecision(buyStrategy);
    }

    private List<Chip> determineShoppingDecision(BuyStrategy buyStrategy) {
        List<Chip> shoppingDecision = new ArrayList<>();
        if (buyStrategy == null) {
            shoppingDecision.add(new Chip(ChipColor.ORANGE, 1));
            return shoppingDecision;
        }
        shoppingDecision.add(buyStrategy.getFirstChip());
        if (buyStrategy.getSecondChip() != null) {
            shoppingDecision.add(buyStrategy.getSecondChip());
        }
        return shoppingDecision;
    }


}
