package com.example.quacksbag.util;

import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.ChipColor;

public class RoundClaudronUtil {
    private final RoundClaudron roundClaudron;

    public RoundClaudronUtil(RoundClaudron roundClaudron) {
        this.roundClaudron = roundClaudron;
    }

    public long findNumberOfWhiteChipValueOverLimit(int limitForExplosion) {
        return roundClaudron.getRoundBagManager()
                .getUndrawnChips().stream()
                .filter(chip -> chip.getColor().equals(ChipColor.WHITE) && chip.getValue() >= limitForExplosion)
                .count();
    }

    public boolean containsWhite3Chip() {
        return roundClaudron.getRoundBagManager().getDrawnChips().stream().noneMatch(chip -> chip.getColor().equals(ChipColor.WHITE) && chip.getValue() == 3);
    }

}
