package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.List;

public interface ShoppingStrategy {
    List<Chip> decideShopping(GameManager gameManager, int bubbleValue, List<ChipPrice> buyableChips);
}
