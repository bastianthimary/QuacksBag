package com.example.quacksbag.ruleset;

import com.example.quacksbag.gamematerial.Chip;

public class ChipPrice {
    private final Chip chip;
    private final int price;
    public ChipPrice(Chip chip, int price) {
        this.chip = chip;
        this.price = price;
    }
    public Chip getChip() {
        return chip;
    }
    public int getPrice() {
        return price;
    }
}
