package com.example.quacksbag.ai.strategy.flask;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.util.RoundBagManagerUtil;

public class UseFlaskByProbabilityStrategy implements FlaskStrategy {
    private final double probability;

    public UseFlaskByProbabilityStrategy(double probability) {
        this.probability = probability;
    }

    @Override
    public boolean decideUseFlask(GameManager gameManager, Chip chip) {
        RoundBagManagerUtil roundBagManagerUtil = new RoundBagManagerUtil(gameManager.getRoundClaudron().getRoundBagManager());
        int numberOfUndrawnChips = roundBagManagerUtil.getNumberOfChipsInClaudron();
        int numberOfWhiteChips = roundBagManagerUtil.getNumberOfWhiteChipsInClaudron();
        return calcProbabilityOfDrawingWhite(numberOfWhiteChips, numberOfUndrawnChips) <
                probability;
    }

    protected
    static double calcProbabilityOfDrawingWhite(int numberOfWhiteChips, int numberOfUndrawnChips) {
        return (double) numberOfWhiteChips / numberOfUndrawnChips;
    }
}

