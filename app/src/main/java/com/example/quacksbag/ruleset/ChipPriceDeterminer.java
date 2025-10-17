package com.example.quacksbag.ruleset;

import com.example.quacksbag.baserules.ChipsStore;

import java.util.List;
import java.util.stream.Collectors;

public class ChipPriceDeterminer {
    PriceRuleset priceRuleset;

    public ChipPriceDeterminer(PriceRuleset priceRuleset) {
        this.priceRuleset = priceRuleset;
    }

    public List<ChipPrice> determinePrices() {
        var allKindsOfChips = ChipsStore.getAllKindsOfChips();
        return allKindsOfChips.stream()
                .map(priceRuleset::determinePrice)
                .collect(Collectors.toList());
    }
}
