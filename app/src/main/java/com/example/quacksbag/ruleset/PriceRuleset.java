package com.example.quacksbag.ruleset;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import java.util.ArrayList;
import java.util.List;

public abstract class PriceRuleset {
    public ChipPrice determinePrice(Chip chip) {
        int value;
        switch (chip.getColor()) {
            case ORANGE:
                value = 1;
                break;
            case BLUE:
                value = determineBlueByValue(chip.getValue());
                break;
            case RED:
                value = determineRedByValue(chip.getValue());
                break;
            case YELLOW:
                value = determineYellowByValue(chip.getValue());
                break;
            case BLACK:
                value = determineBlackByValue(chip.getValue());
                break;
            case GREEN:
                value = determineGreenByValue(chip.getValue());
                break;
            case PURPLE:
                value = determinePurpleByValue(chip.getValue());
                break;
            default:
                value = 0;
                break;
        }
        return new ChipPrice(chip, value);
    }

    protected abstract int determineGreenByValue(int chipValue);

    protected abstract int determineRedByValue(int chipValue);

    protected abstract int determineBlueByValue(int chipValue);

    protected abstract int determineYellowByValue(int chipValue);

    protected abstract int determinePurpleByValue(int chipValue);

    protected abstract int determineBlackByValue(int chipValue);

    public List<ChipPrice> determinePricesByColor(ChipColor chipColor) {
        List<ChipPrice> prices = new ArrayList<>();
        switch (chipColor) {
            case ORANGE:
                prices.add(new ChipPrice(new Chip(ChipColor.ORANGE, 1), 3));
                break;
            case BLUE:
                prices.add(new ChipPrice(new Chip(ChipColor.BLUE, 1), determineBlueByValue(1)));
                prices.add(new ChipPrice(new Chip(ChipColor.BLUE, 2), determineBlueByValue(2)));
                prices.add(new ChipPrice(new Chip(ChipColor.BLUE, 4), determineBlueByValue(4)));
                break;
            case RED:
                prices.add(new ChipPrice(new Chip(ChipColor.RED, 1), determineRedByValue(1)));
                prices.add(new ChipPrice(new Chip(ChipColor.RED, 2), determineRedByValue(2)));
                prices.add(new ChipPrice(new Chip(ChipColor.RED, 4), determineRedByValue(4)));
                break;
            case YELLOW:
                prices.add(new ChipPrice(new Chip(ChipColor.YELLOW, 1), determineYellowByValue(1)));
                prices.add(new ChipPrice(new Chip(ChipColor.YELLOW, 2), determineYellowByValue(2)));
                prices.add(new ChipPrice(new Chip(ChipColor.YELLOW, 4), determineYellowByValue(4)));
                break;
            case BLACK:
                prices.add(new ChipPrice(new Chip(ChipColor.BLACK, 1), determineBlackByValue(1)));
                break;
            case GREEN:
                prices.add(new ChipPrice(new Chip(ChipColor.GREEN, 1), determineGreenByValue(1)));
                prices.add(new ChipPrice(new Chip(ChipColor.GREEN, 2), determineGreenByValue(2)));
                prices.add(new ChipPrice(new Chip(ChipColor.GREEN, 4), determineGreenByValue(4)));
                break;
            case PURPLE:
                prices.add(new ChipPrice(new Chip(ChipColor.PURPLE, 1), determinePurpleByValue(1)));
                break;
            default:
                break;
        }
        return prices;

    }
}
