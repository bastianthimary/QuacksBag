package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.gamematerial.ChipColor;

public class WishedChip {
    private int numberOfChips=-1;
    private ChipColor chipColor;

    public WishedChip(ChipColor chipColor) {
        this.chipColor = chipColor;
    }

    public WishedChip(int numberOfChips, ChipColor chipColor) {
        this.numberOfChips = numberOfChips;
        this.chipColor = chipColor;
    }
    public boolean isLimited(){
        if(numberOfChips==-1){
            return false;
        }
        return true;
    }

    public ChipColor getChipColor() {
        return chipColor;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

}
