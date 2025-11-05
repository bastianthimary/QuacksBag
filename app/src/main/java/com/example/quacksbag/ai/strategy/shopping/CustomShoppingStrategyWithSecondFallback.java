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
import java.util.List;
import java.util.Map;

public class CustomShoppingStrategyWithSecondFallback extends ShoppingStrategy {

    private final WishedChip primaryWish;
    private final WishedChip fallbackWish;
    private final WishedChip secondFallbackWish;
    private List<ChipPrice> primaryPrices;
    private List<ChipPrice> fallbackPrices;
    private List<ChipPrice> secondFallbackPrices;
    private BuyStrategy buyStrategy;

    public CustomShoppingStrategyWithSecondFallback(WishedChip primaryWish, WishedChip fallbackWish, WishedChip secondFallbackWish) {
        this.primaryWish = primaryWish;
        this.fallbackWish = fallbackWish;
        this.secondFallbackWish = secondFallbackWish;
    }

    @Override
    public List<Chip> decideShopping(GameManager gameManager, int bubbleValue, List<ChipPrice> buyableChips) {

        setupPrices(gameManager, buyableChips);
        StrategyCalculator strategyCalculator = new StrategyCalculator(primaryPrices, ComboResultWeight.MAX_SCORE);
        Map<Integer, BuyStrategy> strategiesForBudgets = strategyCalculator.calculateStrategiesForBudgets(bubbleValue);
        buyStrategy = strategiesForBudgets.get(bubbleValue);

        return determineShoppingDecision(bubbleValue);
    }

    private void setupPrices(GameManager gameManager, List<ChipPrice> buyableChips) {
        primaryPrices = new BuyableChipFilter(List.of(primaryWish))
                .filterBuyableChipsByColorAndWitchStillNeeded(gameManager, buyableChips);
        if (hasFallbackWish()) {
            fallbackPrices = new BuyableChipFilter(List.of(fallbackWish)).filterBuyableChipsByColorAndWitchStillNeeded(gameManager, buyableChips);
        }
        if (hasSecondFallbackWish()) {
            secondFallbackPrices = new BuyableChipFilter(List.of(secondFallbackWish)).filterBuyableChipsByColorAndWitchStillNeeded(gameManager, buyableChips);
        }
    }

    private List<Chip> determineShoppingDecision(int bubbleValue) {

        List<Chip> shoppingDecision = new ArrayList<>();
        shoppingDecision = fillBuyStrategyIfNotFull(bubbleValue, shoppingDecision);
        if (!shoppingDecision.isEmpty()) {
            return shoppingDecision;
        }
        shoppingDecision.add(buyStrategy.getFirstChip());
        if (buyStrategy.getSecondChip() != null) {
            shoppingDecision.add(buyStrategy.getSecondChip());
        }
        return shoppingDecision;
    }


    private List<Chip> fillBuyStrategyIfNotFull(int bubbleValue, List<Chip> shoppingDecision) {
        if (buyStrategy == null) {
            buyStrategy = fillBuyStrategyWithFallBack(bubbleValue);
            shoppingDecision = orangeFallBackIfBuyStrategyIsStillNull(buyStrategy, bubbleValue, shoppingDecision);
            if (shoppingDecision != null) {
                return shoppingDecision;
            }
        } else if (buyStrategy.getChipNumber() == 1) {
            addSecondChipIfCanAffort(buyStrategy, bubbleValue);
        }
        return shoppingDecision;
    }

    @Nullable
    private BuyStrategy fillBuyStrategyWithFallBack(int bubbleValue) {
        if (hasFallbackWish()) {
            buyStrategy = determineBuyStrategyForPrices(fallbackPrices, bubbleValue);
        }
        if (buyStrategy == null && hasSecondFallbackWish()) {
            buyStrategy = determineBuyStrategyForPrices(secondFallbackPrices, bubbleValue);
        }
        return buyStrategy;
    }

    @Nullable
    private BuyStrategy determineBuyStrategyForPrices(List<ChipPrice> secondFallbackPrices, int bubbleValue) {
        BuyStrategy buyStrategy;
        StrategyCalculator strategyCalculator = new StrategyCalculator(secondFallbackPrices, ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE);
        Map<Integer, BuyStrategy> strategiesForBudgets = strategyCalculator.calculateStrategiesForBudgets(bubbleValue);
        buyStrategy = strategiesForBudgets.get(bubbleValue);
        return buyStrategy;
    }


    private void addSecondChipIfCanAffort(BuyStrategy buyStrategy, int bubbleValue) {
        PriceRuleset priceRuleset = new PriceRuleset1();
        var firstChip = buyStrategy.getFirstChip();
        var firstChipPrice = priceRuleset.determinePrice(firstChip);

        addWishedChipIfItsInBudget(buyStrategy, bubbleValue, firstChipPrice, priceRuleset, fallbackWish);
        if (buyStrategy.getChipNumber() == 1) {
            addWishedChipIfItsInBudget(buyStrategy, bubbleValue, firstChipPrice, priceRuleset, secondFallbackWish);
        }
    }

    private static void addWishedChipIfItsInBudget(BuyStrategy buyStrategy, int bubbleValue, ChipPrice firstChipPrice, PriceRuleset priceRuleset, WishedChip whisedChip) {
        var leftBudget = bubbleValue - firstChipPrice.getPrice();
        if (leftBudget > 0) {
            int i = 0;
            var prices = priceRuleset.determinePricesByColor(whisedChip.getChipColor());
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

    public List<ChipPrice> getPrimaryPrices() {
        return primaryPrices;
    }

    public List<ChipPrice> getFallbackPrices() {
        return fallbackPrices;
    }

    public List<ChipPrice> getSecondFallbackPrices() {
        return secondFallbackPrices;
    }

    protected boolean hasFallbackWish() {
        return fallbackWish != null;
    }

    protected boolean hasSecondFallbackWish() {
        return secondFallbackWish != null;
    }


}
