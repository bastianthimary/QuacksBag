package com.example.quacksbag.ai.strategy.factory.shopping;

import com.example.quacksbag.ai.strategy.shopping.CustomShoppingStrategy;
import com.example.quacksbag.ai.strategy.shopping.ShoppingStrategy;
import com.example.quacksbag.ai.strategy.shopping.WishedChip;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;

import java.util.List;

public class ShoppingStrategyFactory {
    public static ShoppingStrategy createStrategy(ComboResultWeight comboResultWeight, List<WishedChip> wantedChips){
        return new CustomShoppingStrategy(comboResultWeight, wantedChips);
    }
}
