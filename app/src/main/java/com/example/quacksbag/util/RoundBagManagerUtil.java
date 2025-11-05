package com.example.quacksbag.util;

import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.gamematerial.ChipColor;

public class RoundBagManagerUtil {
    private final RoundBagManager roundBagManager;

    public RoundBagManagerUtil(RoundBagManager roundBagManager) {
        this.roundBagManager = roundBagManager;
    }

    public int getNumberOfChipsInClaudron() {
        return roundBagManager.getUndrawnChips().size();
    }

    public int getNumberOfWhiteChipsInClaudron() {
        return (int) roundBagManager.getUndrawnChips().stream()
                .filter(chip -> ChipColor.WHITE.equals(chip.getColor())).count();
    }

}
