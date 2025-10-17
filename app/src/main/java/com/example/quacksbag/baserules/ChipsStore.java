package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import java.util.ArrayList;
import java.util.List;

public class ChipsStore {
    // Predefined presets available to "buy" (color + value)
    public static List<Chip> getAllKindsOfChips() {
        List<Chip> buyable = new ArrayList<>();
        // White (Knallerbsen) - common starter values (1,2,3)
        buyable.add(new Chip(ChipColor.WHITE, 1));
        buyable.add(new Chip(ChipColor.WHITE, 2));
        buyable.add(new Chip(ChipColor.WHITE, 3));

        // Colored ingredients (typically come in 1/2/4 variants in many books)
        buyable.add(new Chip(ChipColor.ORANGE, 1));
        buyable.add(new Chip(ChipColor.GREEN, 1));
        buyable.add(new Chip(ChipColor.GREEN, 2));
        buyable.add(new Chip(ChipColor.GREEN, 4));

        buyable.add(new Chip(ChipColor.BLUE, 1));
        buyable.add(new Chip(ChipColor.BLUE, 2));
        buyable.add(new Chip(ChipColor.BLUE, 4));

        buyable.add(new Chip(ChipColor.RED, 1));
        buyable.add(new Chip(ChipColor.RED, 2));
        buyable.add(new Chip(ChipColor.RED, 4));

        buyable.add(new Chip(ChipColor.YELLOW, 1));
        buyable.add(new Chip(ChipColor.YELLOW, 2));
        buyable.add(new Chip(ChipColor.YELLOW, 4));

        buyable.add(new Chip(ChipColor.PURPLE, 1));

        buyable.add(new Chip(ChipColor.BLACK, 1));

        return buyable;
    }

    public static List<Chip> getBuyableChipsForRound(int round) {
        List<Chip> allChips = getAllKindsOfChips();
        List<Chip> buyableChips = UndrawnChipsUtil.findAllNonWhiteChips(allChips);
        if (round > 2) {
            return buyableChips;
        }
        buyableChips = new ArrayList<>(buyableChips);
        buyableChips.removeAll(UndrawnChipsUtil.findChipsByColor(buyableChips, ChipColor.PURPLE));
        if (round == 2) {
            return buyableChips;
        }
        buyableChips.removeAll(UndrawnChipsUtil.findChipsByColor(buyableChips, ChipColor.YELLOW));
        return buyableChips;
    }

}
