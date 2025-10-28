package com.example.quacksbag.ai.strategy.factory.shopping;

import com.example.quacksbag.ai.strategy.shopping.WishedChip;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;

import java.util.List;

public class ShoppingStrategyOptionWrapper {

    private final ShoppingStrategyOption shoppingStrategyOption;
    private final List<WishedChip> wishedChips;
    private final List<WishedChip> fallbackWishedChips;
    private final WishedChip primaryWish;
    private final WishedChip fallbackWish;
    private final WishedChip secondFallbackWish;
    private final ComboResultWeight comboResultWeight;

    public ShoppingStrategyOptionWrapper(ComboResultWeight comboResultWeight, List<WishedChip> wishedChips) {
        this.wishedChips = wishedChips;
        this.comboResultWeight = comboResultWeight;
        this.fallbackWishedChips = null;
        this.primaryWish = null;
        this.fallbackWish = null;
        this.secondFallbackWish = null;
        this.shoppingStrategyOption = ShoppingStrategyOption.CUSTOM;

    }

    public ShoppingStrategyOptionWrapper(List<WishedChip> wishedChips, List<WishedChip> fallbackWishedChips) {
        this.fallbackWishedChips = fallbackWishedChips;
        this.wishedChips = wishedChips;
        this.shoppingStrategyOption = ShoppingStrategyOption.CUSTOM_WITH_ONE_FALLBACK;
        this.comboResultWeight = null;
        this.primaryWish = null;
        this.fallbackWish = null;
        this.secondFallbackWish = null;
    }

    public ShoppingStrategyOptionWrapper(WishedChip primaryWish, WishedChip fallbackWish, WishedChip secondFallbackWish) {
        this.primaryWish = primaryWish;
        this.fallbackWish = fallbackWish;
        this.secondFallbackWish = secondFallbackWish;
        this.shoppingStrategyOption = ShoppingStrategyOption.CUSTOM_WITH_TWO_FALLBACK;
        this.wishedChips = null;
        this.fallbackWishedChips = null;
        this.comboResultWeight = null;
    }

    public ShoppingStrategyOption getShoppingStrategyOption() {
        return shoppingStrategyOption;
    }

    public List<WishedChip> getWishedChips() {
        return wishedChips;
    }

    public List<WishedChip> getFallbackWishedChips() {
        return fallbackWishedChips;
    }

    public WishedChip getPrimaryWish() {
        return primaryWish;
    }

    public WishedChip getFallbackWish() {
        return fallbackWish;
    }

    public WishedChip getSecondFallbackWish() {
        return secondFallbackWish;
    }

    public ComboResultWeight getComboResultWeight() {
        return comboResultWeight;
    }
}
