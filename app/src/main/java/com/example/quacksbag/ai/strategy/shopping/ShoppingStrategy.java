package com.example.quacksbag.ai.strategy.shopping;

import androidx.annotation.Nullable;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.List;

public abstract class ShoppingStrategy {
    public abstract List<Chip> decideShopping(GameManager gameManager, int bubbleValue, List<ChipPrice> buyableChips);

    @Nullable
    protected static List<Chip> orangeFallBackIfBuyStrategyIsStillNull(BuyStrategy buyStrategy, int bubbleValue, List<Chip> shoppingDecision) {
        if (buyStrategy == null) {
            if (bubbleValue >= 3 && bubbleValue < 6) {
                shoppingDecision.add(new Chip(ChipColor.ORANGE, 1));
            } else if (bubbleValue >= 6) {
                shoppingDecision.add(new Chip(ChipColor.ORANGE, 1));
                shoppingDecision.add(new Chip(ChipColor.ORANGE, 1));
            }
        }
        return shoppingDecision;
    }
}
