package com.example.quacksbag.ai.strategy.factory.ruby;

public class RubyBuyableStrategyOptionWrapper {
    private final RubyBuyableStrategyOption option;
    private final int round;

    public RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption option, int round) {
        this.option = option;
        this.round = round;
    }

    public RubyBuyableStrategyOption getOption() {
        return option;
    }

    public int getRound() {
        return round;
    }
}
