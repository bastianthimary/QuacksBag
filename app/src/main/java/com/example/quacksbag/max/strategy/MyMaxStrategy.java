package com.example.quacksbag.max.strategy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.chipset.ChipSetting;
import com.example.quacksbag.max.strategy.chipset.ChipSettingWeights;
import com.example.quacksbag.max.strategy.chipset.ChipStrategy;
import com.example.quacksbag.max.strategy.chipset.StrategySteps;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class MyMaxStrategy extends MaxStrategy {
    private BuyStrategySetting buyStrategySetting;
    private ChipStrategy chipStrategy;
    private Ruleset ruleset;

    public MyMaxStrategy(ChipStrategy chipStrategy, BuyStrategySetting buyStrategySetting) {
        this.chipStrategy = chipStrategy;
        this.buyStrategySetting = buyStrategySetting;
    }

    @Override
    public ArrayList<Chip> determineBestChipSetForCurrentRound(ArrayList<Chip> chipsInBag, Ruleset ruleset, boolean canExplode) {
        return null;
    }

    @Override
    public HashMap<Integer, BuyStrategy> determineBuyStrategyByRuleSet(Ruleset ruleset) {
        return null;
    }

    @Override
    public HashMap<StrategySteps, HashMap<Integer, BuyStrategy>> determineBuyStrategyForChipStrategy() {
        for (ChipSetting setting : chipStrategy.getSettings()) {
            setting.getChipSettingWeight();
        }

        return null;
    }

    private boolean isEarlyGameAndHasOneColorSetting() {
        long distinctColorCount = chipStrategy.getSettings().stream()
                .filter(chipSetting ->
                        !ChipSettingWeights.AFTERREACH_END.equals(chipSetting.getChipSettingWeight()) &&
                                chipSetting.getChipColor() != null) // Filter for "early game" settings that have a color
                .map(ChipSetting::getChipColor)             // Get the color from each setting
                .distinct()                                 // Keep only unique colors
                .count();                                   // Count the number of unique colors

        return distinctColorCount == 1;
    }

    private boolean isEarlyGameAndHasMoreThanOneColorSetting() {
        long distinctColorCount = chipStrategy.getSettings().stream()
                .filter(chipSetting ->
                        !ChipSettingWeights.AFTERREACH_END.equals(chipSetting.getChipSettingWeight()) &&
                                chipSetting.getChipColor() != null) // Filter for "early game" settings that have a color
                .map(ChipSetting::getChipColor)             // Get the color from each setting
                .distinct()                                 // Keep only unique colors
                .count();                                   // Count the number of unique colors

        return distinctColorCount > 1; // Return true if there is more than one distinct color
    }

    private Set<ChipColor> getEarlyGameColorsFromSettings() {
        var distinctColorCount = chipStrategy.getSettings().stream()
                .filter(chipSetting ->
                        !ChipSettingWeights.AFTERREACH_END.equals(chipSetting.getChipSettingWeight()) &&
                                chipSetting.getChipColor() != null)
                .map(ChipSetting::getChipColor)
                .collect(Collectors.toSet());
        return distinctColorCount;
    }

    private ChipColor getEarlyGameOneColorSetting() {
        return chipStrategy.getSettings().stream()
                .filter(chipSetting ->
                        !ChipSettingWeights.AFTERREACH_END.equals(chipSetting.getChipSettingWeight()) &&
                                chipSetting.getChipColor() != null) // Filter for "early game" settings that have a color
                .map(ChipSetting::getChipColor)             // Get the color from each setting
                .distinct().findAny().get();
    }



    private boolean isASingleValueColor(ChipColor color) {
        return false;
    }

    private HashMap<Integer, BuyStrategy> determineBuyStrategyByOneValueAndOneColor(Chip chip, int price) {
        HashMap<Integer, BuyStrategy> buyStrategy = new HashMap<>();
        for (int i = 1; i < 36; i++) {
            if (i > price && i < 2 * price) {
                buyStrategy.put(i, new BuyStrategy(new Chip(chip.getColor(), chip.getValue())));
            } else if (i >= 2 * price) {
                buyStrategy.put(i, new BuyStrategy(new Chip(chip.getColor(), chip.getValue()), new Chip(chip.getColor(), chip.getValue())));
            }
        }
        return buyStrategy;
    }
}
