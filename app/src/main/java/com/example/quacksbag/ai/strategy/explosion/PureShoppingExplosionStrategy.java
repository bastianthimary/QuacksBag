package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;

public class PureShoppingExplosionStrategy implements ExplosionStrategy{
    @Override
    public ExplosionChoice decideExplosion(GameManager gameManager) {
        return ExplosionChoice.BUBBLESHOPPING;
    }
}
