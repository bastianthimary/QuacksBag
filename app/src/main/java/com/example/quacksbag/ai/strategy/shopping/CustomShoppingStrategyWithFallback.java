package com.example.quacksbag.ai.strategy.shopping;

import androidx.annotation.Nullable;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;
import com.example.quacksbag.max.strategy.buy.StrategyCalculator;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.PriceRuleset;
import com.example.quacksbag.ruleset.implementations.PriceRuleset1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CustomShoppingStrategyWithFallback extends ShoppingStrategy {

    private final List<WishedChip> primaryWishList;
    private final List<WishedChip> fallbackWishList;
    private List<ChipPrice> primaryPrices;
    private List<ChipPrice> fallbackPrices;
    private BuyStrategy buyStrategy;


    public CustomShoppingStrategyWithFallback(List<WishedChip> primaryWishList, List<WishedChip> fallbackWishList) {
        this.primaryWishList = primaryWishList;
        this.fallbackWishList = fallbackWishList;
    }

    @Override
    public List<Chip> decideShopping(GameManager gameManager, int bubbleValue, List<ChipPrice> buyableChips) {
        if (bubbleValue < 3) {
            return new ArrayList<>();
        }
        primaryPrices = new BuyableChipFilter(primaryWishList).filterBuyableChipsByColorAndWitchStillNeeded(gameManager, buyableChips);
        if (hasFallbackWishList()) {
            fallbackPrices = new BuyableChipFilter(fallbackWishList).filterBuyableChipsByColorAndWitchStillNeeded(gameManager, buyableChips);
        }
        verifyFallbackPricesCheaperThanPrimaryPrices();
        StrategyCalculator strategyCalculator = new StrategyCalculator(primaryPrices, ComboResultWeight.MAX_SCORE);

        Map<Integer, BuyStrategy> strategiesForBudgets = strategyCalculator.calculateStrategiesForBudgets(bubbleValue);
        buyStrategy = strategiesForBudgets.get(bubbleValue);

        return determineShoppingDecision(bubbleValue);
    }

    private void verifyFallbackPricesCheaperThanPrimaryPrices() {
        if (hasFallbackWishList()) {
            primaryPrices.sort(Comparator.comparingInt(ChipPrice::getPrice));
            fallbackPrices.sort(Comparator.comparingInt(ChipPrice::getPrice));
            var lowestPrimary = checkPriceForPosition(primaryPrices, 0);
            var lowestFallback = checkPriceForPosition(fallbackPrices, 0);
            if (lowestPrimary < lowestFallback) {
                throw new IllegalArgumentException("Fallback prices are not cheaper than primary prices");
            }
        }
    }

    private List<Chip> determineShoppingDecision(int bubbleValue) {

        List<Chip> shoppingDecision = new ArrayList<>();
        List<Chip> shoppingDecision1 = fillBuyStrategyIfNotFull(bubbleValue, shoppingDecision);
        if (shoppingDecision1 != null && !shoppingDecision1.isEmpty()) {
            return shoppingDecision1;
        }
        shoppingDecision.add(buyStrategy.getFirstChip());
        if (buyStrategy.getSecondChip() != null) {
            shoppingDecision.add(buyStrategy.getSecondChip());
        }
        return shoppingDecision;
    }

    @Nullable
    private List<Chip> fillBuyStrategyIfNotFull(int bubbleValue, List<Chip> shoppingDecision) {
        if (buyStrategy == null) {
            StrategyCalculator strategyCalculator = new StrategyCalculator(fallbackPrices, ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE);
            Map<Integer, BuyStrategy> strategiesForBudgets = strategyCalculator.calculateStrategiesForBudgets(bubbleValue);
            buyStrategy = strategiesForBudgets.get(bubbleValue);
            List<Chip> shoppingDecision1 = orangeFallBackIfBuyStrategyIsStillNull(buyStrategy, bubbleValue, shoppingDecision);
            if (shoppingDecision1 != null && !shoppingDecision1.isEmpty()) {
                return shoppingDecision1;
            }
        } else if (buyStrategy.getChipNumber() == 1) {
            addSecondChipIfCanAffort(bubbleValue);
        }
        return null;
    }

    private void addSecondChipIfCanAffort(int bubbleValue) {
        PriceRuleset priceRuleset = new PriceRuleset1();
        var firstChip = buyStrategy.getFirstChip();
        var firstChipPrice = priceRuleset.determinePrice(firstChip);
        var leftBudget = bubbleValue - firstChipPrice.getPrice();
        if (leftBudget > 0) {
            int i = 0;
            var prices = priceRuleset.determinePricesByColor(
                    fallbackWishList.get(i).getChipColor());
            Chip valuestChipForBudget = null;
            while (leftBudget >= checkPriceForPosition(prices, i)) {
                valuestChipForBudget = prices.get(i).getChip();
                i++;

            }
            if (valuestChipForBudget != null) {
                buyStrategy.setSecondChip(valuestChipForBudget);
            }
        }
    }

    private static int checkPriceForPosition(List<ChipPrice> prices, int position) {
        if (prices.size() > position) {
            return prices.get(position).getPrice();
        }
        return 9999;
    }

    protected boolean hasFallbackWishList() {
        return fallbackWishList != null && !fallbackWishList.isEmpty();
    }


}
