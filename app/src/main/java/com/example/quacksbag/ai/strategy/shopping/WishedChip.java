package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.gamematerial.ChipColor;

public class WishedChip {
    private int numberOfChips=-1;
    private final ChipColor chipColor;

    public WishedChip(ChipColor chipColor) {
        this.chipColor = chipColor;
    }

    public WishedChip(int numberOfChips, ChipColor chipColor) {
        this.numberOfChips = numberOfChips;
        this.chipColor = chipColor;
    }
    public boolean isLimited(){
        return numberOfChips != -1;
    }

    public ChipColor getChipColor() {
        return chipColor;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

}
