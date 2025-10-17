package com.example.quacksbag.ai.strategy.factory.flask;

public class FlaskStrategyOptionWrapper {
    private final FlaskStrategyOption option;
    private final double propability;
    public FlaskStrategyOptionWrapper(FlaskStrategyOption option) {
        this.option = option;
        this.propability = 0;
    }
    public FlaskStrategyOptionWrapper(FlaskStrategyOption option, double propability) {
        this.option = option;
        this.propability = propability;
    }

    public FlaskStrategyOption getOption() {
        return option;
    }

    public double getPropability() {
        return propability;
    }
}
