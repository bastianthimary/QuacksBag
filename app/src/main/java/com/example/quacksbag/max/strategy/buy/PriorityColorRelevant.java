package com.example.quacksbag.max.strategy.buy;

public class PriorityColorRelevant {
    private final boolean isPriorityColorRelevant;
    private final ComboResult priorComboResult;

    public PriorityColorRelevant(boolean isPriorityColorRelevant, ComboResult priorComboResult) {
        this.isPriorityColorRelevant = isPriorityColorRelevant;
        this.priorComboResult = priorComboResult;
    }

    public boolean isPriorityColorRelevant() {
        return isPriorityColorRelevant;
    }

    public ComboResult getPriorComboResult() {
        return priorComboResult;
    }
}
