package com.example.quacksbag.ai.strategy.draw;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;

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
        if (isTheProbabilityToExplodeHigherThanMyLimit(gameManager)){
            return DrawChoice.END_ROUND;
        }
        return DrawChoice.DRAW_NEXT;
    }

    protected boolean isTheProbabilityToExplodeHigherThanMyLimit(GameManager gameManager) {
        var roundClaudron = gameManager.getRoundClaudron();
        var fireCrackerCounter = roundClaudron.getFirecrackerPeaCounter();
        var limitForExplosion = 7 - fireCrackerCounter;
        var numberOfWhiteChipsWhichCanExplode = roundClaudron.getRoundBagManager()
                .getUndrawnChips().stream()
                .filter(chip -> chip.getColor().equals(ChipColor.WHITE) && chip.getValue() >= limitForExplosion)
                .count();

        var numberofChipsinClaudron = roundClaudron.getRoundBagManager().getUndrawnChips().size();
        double probabilityToExplode = (double) numberOfWhiteChipsWhichCanExplode / numberofChipsinClaudron;
        return probabilityToExplode> probabilityInZeroDot;
    }
}
