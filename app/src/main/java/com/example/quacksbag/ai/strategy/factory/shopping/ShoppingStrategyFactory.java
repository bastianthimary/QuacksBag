package com.example.quacksbag.ai.strategy.factory.shopping;

import com.example.quacksbag.ai.strategy.shopping.CustomShoppingStrategy;
import com.example.quacksbag.ai.strategy.shopping.CustomShoppingStrategyWithFallback;
import com.example.quacksbag.ai.strategy.shopping.CustomShoppingStrategyWithSecondFallback;
import com.example.quacksbag.ai.strategy.shopping.ShoppingStrategy;

public class ShoppingStrategyFactory {
    public static ShoppingStrategy createStrategy(ShoppingStrategyOptionWrapper shoppingStrategyOptionWrapper) {


        return switch (shoppingStrategyOptionWrapper.getShoppingStrategyOption()) {
            case CUSTOM ->
                    new CustomShoppingStrategy(shoppingStrategyOptionWrapper.getComboResultWeight(), shoppingStrategyOptionWrapper.getWishedChips());
            case CUSTOM_WITH_ONE_FALLBACK -> new CustomShoppingStrategyWithFallback(
                    shoppingStrategyOptionWrapper.getWishedChips(),
                    shoppingStrategyOptionWrapper.getFallbackWishedChips()
            );
            case CUSTOM_WITH_TWO_FALLBACK -> new CustomShoppingStrategyWithSecondFallback(
                    shoppingStrategyOptionWrapper.getPrimaryWish(),
                    shoppingStrategyOptionWrapper.getFallbackWish(),
                    shoppingStrategyOptionWrapper.getSecondFallbackWish()
            );
        };

    }
}
