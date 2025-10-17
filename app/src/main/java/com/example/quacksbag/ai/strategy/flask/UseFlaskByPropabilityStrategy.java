package com.example.quacksbag.ai.strategy.flask;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

public class UseFlaskByPropabilityStrategy implements FlaskStrategy {
    private double propability;

    public UseFlaskByPropabilityStrategy(double propability) {
        this.propability = propability;
    }

    @Override
    public boolean decideUseFlask(GameManager gameManager, Chip chip) {

        var undrawnChips = gameManager.getRoundClaudron().getRoundBagManager().getUndrawnChips();
        int numberOfUndrawnChips = undrawnChips.size();
        int numberOfWhiteChips = (int) undrawnChips.stream()
                .filter(chip1 -> ChipColor.WHITE.equals(chip1.getColor())).count();
        return determinePropabilityOfDrawingWhite(numberOfWhiteChips, numberOfUndrawnChips) <
                propability;
    }

    protected
    static double determinePropabilityOfDrawingWhite(int numberOfWhiteChips, int numberOfUndrawnChips) {
        return (double) numberOfWhiteChips / numberOfUndrawnChips;
    }
}

