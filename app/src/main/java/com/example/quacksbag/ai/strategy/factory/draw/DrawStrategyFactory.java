package com.example.quacksbag.ai.strategy.factory.draw;

import com.example.quacksbag.ai.strategy.draw.CustomDrawStrategyByProbability;
import com.example.quacksbag.ai.strategy.draw.DrawStrategy;
import com.example.quacksbag.ai.strategy.draw.SafeDrawStrategy;

public class DrawStrategyFactory {

    public static DrawStrategy createStrategy(DrawStrategyOptionWrapper options) {
        switch (options.getOption()) {
            case SAFE_DRAW:
                return new SafeDrawStrategy();
            case CUSTOM_DRAW_BY_PROBABILITY:
                return new CustomDrawStrategyByProbability(options.getProbabilityInZeroDot());

        }
        return null;
    }
}
