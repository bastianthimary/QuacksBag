package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UndrawnChipsUtil {

    public static List<Chip> findAllNonWhiteChips(List<Chip> chips) {
        if (chips == null) {
            return new ArrayList<>();
        }
        return chips.stream()
                .filter(chip -> chip.getColor() != ChipColor.WHITE)
                .collect(Collectors.toList());
    }

    public static List<Chip> findChipsByColor(List<Chip> chips, ChipColor color) {
        if (chips == null || color == null) {
            return new ArrayList<>();
        }
        return chips.stream()
                .filter(chip -> chip.getColor() == color)
                .collect(Collectors.toList());
    }



    public static Map<ChipColor, Integer> countChipsByColor(List<Chip> chips) {
        if (chips == null) {
            return new HashMap<>();
        }
        Map<ChipColor, Integer> colorCount = new HashMap<>();
        for (Chip chip : chips) {
            colorCount.put(chip.getColor(), colorCount.getOrDefault(chip.getColor(), 0) + 1);
        }
        return colorCount;
    }

    public static int calculateTotalValue(List<Chip> chips) {
        if (chips == null) {
            return 0;
        }
        return chips.stream()
                .mapToInt(Chip::getValue)
                .sum();
    }


    public static boolean containsChipOfColor(List<Chip> chips, ChipColor color) {
        if (chips == null || color == null) {
            return false;
        }
        return chips.stream()
                .anyMatch(chip -> chip.getColor() == color);
    }


}
