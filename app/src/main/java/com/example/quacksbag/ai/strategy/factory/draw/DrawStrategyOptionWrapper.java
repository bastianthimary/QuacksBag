package com.example.quacksbag.ai.strategy.factory.draw;

public class DrawStrategyOptionWrapper {
    private final DrawStrategyOption option;
    private final double probabilityInZeroDot;

    public DrawStrategyOptionWrapper(DrawStrategyOption option) {
        this.option = option;
        this.probabilityInZeroDot = 0;
    }

    public DrawStrategyOptionWrapper(DrawStrategyOption option, double probabilityInZeroDot) {
        this.option = option;
        this.probabilityInZeroDot = probabilityInZeroDot;
    }

    public DrawStrategyOption getOption() {
        return option;
    }

    public double getProbabilityInZeroDot() {
        return probabilityInZeroDot;
    }
}
