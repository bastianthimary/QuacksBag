package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;

public class MixedExplosionStrategy implements ExplosionStrategy{
    private final int roundTillAcceptVictoryPoints;

    public MixedExplosionStrategy(int roundTillAcceptVictoryPoints) {
        this.roundTillAcceptVictoryPoints = roundTillAcceptVictoryPoints;
    }

    @Override
    public ExplosionChoice decideExplosion(GameManager gameManager) {
        if (gameManager.getCurrentRound() <= roundTillAcceptVictoryPoints) {
            return ExplosionChoice.BUBBLESHOPPING;
        }
        return ExplosionChoice.VICTORYPOINTS;
    }
}
