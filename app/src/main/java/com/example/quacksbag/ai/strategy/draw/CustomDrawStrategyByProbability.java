package com.example.quacksbag.ai.strategy.draw;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.util.RoundBagManagerUtil;
import com.example.quacksbag.util.RoundClaudronUtil;

public class CustomDrawStrategyByProbability extends DrawStrategy {
    private final double probabilityInZeroDot;

    public CustomDrawStrategyByProbability(double probabilityInZeroDot) {
        this.probabilityInZeroDot = probabilityInZeroDot;
    }

    @Override
    public DrawChoice decideDraw(GameManager gameManager) {
        if (!canClaudronExplodeNextDraw(gameManager)) {
            return DrawChoice.DRAW_NEXT;
        }
        if (isTheProbabilityToExplodeHigherThanMyLimit(gameManager)) {
            return DrawChoice.END_ROUND;
        }
        return DrawChoice.DRAW_NEXT;
    }

    protected boolean isTheProbabilityToExplodeHigherThanMyLimit(GameManager gameManager) {
        var roundClaudron = gameManager.getRoundClaudron();
        var fireCrackerCounter = roundClaudron.getFirecrackerPeaCounter();
        var limitForExplosion = 7 - fireCrackerCounter;
        var numberOfWhiteChipsWhichCanExplode = new RoundClaudronUtil(roundClaudron).findNumberOfWhiteChipValueOverLimit(limitForExplosion);

        var numberOfChipsInClaudron = new RoundBagManagerUtil(roundClaudron.getRoundBagManager()).getNumberOfChipsInClaudron();
        double probabilityToExplode = (double) numberOfWhiteChipsWhichCanExplode / numberOfChipsInClaudron;
        return probabilityToExplode > probabilityInZeroDot;
    }
}
