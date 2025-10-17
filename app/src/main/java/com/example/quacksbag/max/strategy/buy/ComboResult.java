package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.ruleset.ChipPrice;

public class ComboResult {
    private final ChipPrice chip1;
    private final ChipPrice chip2;
    private final int totalValue;
    private final int totalCost;


    public ComboResult(ChipPrice chip1, ChipPrice chip2, int totalValue, int totalCost) {
        this.chip1 = chip1;
        this.chip2 = chip2;
        this.totalValue = totalValue;
        this.totalCost = totalCost;
    }

    public ChipPrice getChip1() { return chip1; }
    public ChipPrice getChip2() { return chip2; }
    public int getTotalValue() { return totalValue; }
    public int getTotalCost() { return totalCost; }

    public int getChipCount(){
        return (chip2 == null) ? 1 : 2;
    }
    @Override
    public String toString() {
        return "[" + chip1 + " + " + chip2 +
                "] => Wert=" + totalValue +
                ", Kosten=" + totalCost;
    }
}
