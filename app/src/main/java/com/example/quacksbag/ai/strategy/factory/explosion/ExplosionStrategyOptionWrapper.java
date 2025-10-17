package com.example.quacksbag.ai.strategy.factory.explosion;

public class ExplosionStrategyOptionWrapper {
    private final ExplosionStrategyOption option;
private int roundTillAcceptVictoryPoints;

    public ExplosionStrategyOptionWrapper(ExplosionStrategyOption option) {
        this.option = option;
    }
    public ExplosionStrategyOptionWrapper(ExplosionStrategyOption option, int roundTillAcceptVictoryPoints) {
        this.option = option;
        this.roundTillAcceptVictoryPoints=roundTillAcceptVictoryPoints;
    }

    public ExplosionStrategyOption getOption() {
        return option;
    }
    public int getRoundTillAcceptVictoryPoints() {
        return roundTillAcceptVictoryPoints;
    }
}
