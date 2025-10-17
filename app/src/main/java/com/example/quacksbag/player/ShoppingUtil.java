package com.example.quacksbag.player;

import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.ChipPriceDeterminer;
import com.example.quacksbag.ruleset.PriceRuleset;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.List;

public class ShoppingUtil {

    public static List<ChipPrice> determineShoppingList(Ruleset ruleset, int playRound) {
        PriceRuleset priceRuleset = ruleset.getPriceRuleset();
        ChipPriceDeterminer chipPriceDeterminer = new ChipPriceDeterminer(priceRuleset);
        List<ChipPrice> chipPrices = chipPriceDeterminer.determinePrices();
        if (playRound > 2) {
            return chipPrices;
        }
        removePurpleChips(chipPrices);
        if (playRound > 1) {
            return chipPrices;
        }removeYellowChips(chipPrices);
        return chipPrices;
    }

    private static void removeYellowChips(List<ChipPrice> chipPrices) {
        chipPrices.removeIf(chipPrice -> chipPrice.getChip().getColor().equals(ChipColor.YELLOW));
    }

    private static void removePurpleChips(List<ChipPrice> chipPrices) {
        chipPrices.removeIf(chipPrice -> chipPrice.getChip().getColor().equals(ChipColor.PURPLE));
    }
}
