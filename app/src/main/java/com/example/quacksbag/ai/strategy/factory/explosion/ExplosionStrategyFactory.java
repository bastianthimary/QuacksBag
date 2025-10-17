package com.example.quacksbag.ai.strategy.factory.explosion;

import com.example.quacksbag.ai.strategy.explosion.ExplosionStrategy;
import com.example.quacksbag.ai.strategy.explosion.MixedExplosionStrategy;
import com.example.quacksbag.ai.strategy.explosion.PureShoppingExplosionStrategy;
import com.example.quacksbag.ai.strategy.explosion.PureVictroyExplosionStrategy;

public class ExplosionStrategyFactory {
    public static ExplosionStrategy createStrategy(ExplosionStrategyOptionWrapper options) {
        return switch (options.getOption()) {
            case PURE_SHOPPING -> new PureShoppingExplosionStrategy();
            case PURE_VICTORYPOINTS -> new PureVictroyExplosionStrategy();
            case MIXED -> new MixedExplosionStrategy(options.getRoundTillAcceptVictoryPoints());
        };
    }

}
