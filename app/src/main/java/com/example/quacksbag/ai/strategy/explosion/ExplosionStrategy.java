package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;

public interface ExplosionStrategy {
    ExplosionChoice decideExplosion(GameManager gameManager);
}
