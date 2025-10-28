package com.example.quacksbag.max.strategy;

import androidx.annotation.NonNull;

import com.example.quacksbag.gamematerial.Chip;

public class BuyStrategy {
    private final Chip firstChip;
    private Chip secondChip;

    public BuyStrategy(Chip firstChip) {
        this.firstChip = firstChip;
    }

    public BuyStrategy(Chip firstChip, Chip secondChip) {
        this.firstChip = firstChip;
        this.secondChip = secondChip;
    }

    @NonNull
    public Chip getFirstChip() {
        return firstChip;
    }

    public Chip getSecondChip() {
        return secondChip;
    }

    public int getChipNumber() {
        return secondChip != null ? 2 : 1;
    }

    public void setSecondChip(Chip secondChip) {
        this.secondChip = secondChip;
    }
}
