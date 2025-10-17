package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.gamematerial.ChipColor;

public class ComboResultUtil {
    public static PriorityColorRelevant determinePriorityColorRelevant(ComboResult comboA, ComboResult comboB, ChipColor priorityColor,boolean maxOneOfPriorityColor) {
        var colorCountA = determineColorCount(comboA, priorityColor);
        var colorCountB = determineColorCount(comboB, priorityColor);
        if (colorCountA == colorCountB) {
            return new PriorityColorRelevant(false, null);
        }
        if (maxOneOfPriorityColor) {
            if(colorCountA == 1 ) {
                return new PriorityColorRelevant(true, comboA);
            } else if (colorCountB == 1) {
                return new PriorityColorRelevant(true, comboB);
            }
        }
        return new PriorityColorRelevant(true, colorCountA > colorCountB ? comboA : comboB);
    }


    public static int determineColorCount(ComboResult combo, ChipColor color) {
        int counter = 0;
        if (combo.getChip1().getChip().getColor().equals(color)) {
            counter++;
        }
        if (combo.getChip2() == null) {
            return counter;
        }
        if (combo.getChip2().getChip().getColor().equals(color)) {
            counter++;
        }
        return counter;
    }

    public static ComboResult determineMaxScore(ComboResult a, ComboResult b) {
        if (a.getTotalValue() > b.getTotalValue()) {
            return a;
        } else if (a.getTotalValue() < b.getTotalValue()) {
            return b;
        } else {
            return determineHigherCost(a, b);
        }
    }

    public static ComboResult determineHigherCost(ComboResult a, ComboResult b) {
        if (a.getTotalCost() < b.getTotalCost()) {
            return b;
        } else {
            return a;
        }
    }


    public static boolean isEqualChipCount(ComboResult a, ComboResult b) {
        return a.getChipCount() == b.getChipCount();
    }
}
