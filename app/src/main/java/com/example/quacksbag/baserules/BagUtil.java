package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import java.util.ArrayList;
import java.util.List;

public class BagUtil {
    // default chips for a new player at game start
    public static List<Chip> defaultStartingChips() {
        List<Chip> defaults = new ArrayList<>();
        // 4 x white 1
        for (int i = 0; i < 4; i++) {
            defaults.add(new Chip(ChipColor.WHITE, 1));
        }
        // 2 x white 2
        for (int i = 0; i < 2; i++) {
            defaults.add(new Chip(ChipColor.WHITE, 2));
        }
        // 1 x white 3
        defaults.add(new Chip(ChipColor.WHITE, 3));
        // 1 x orange 1
        defaults.add(new Chip(ChipColor.ORANGE, 1));
        // 1 x green 1
        defaults.add(new Chip(ChipColor.GREEN, 1));
        return defaults;
    }
}
