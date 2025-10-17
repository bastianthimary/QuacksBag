package com.example.quacksbag.ai.strategy.factory.flask;

import com.example.quacksbag.ai.strategy.flask.FlaskStrategy;
import com.example.quacksbag.ai.strategy.flask.OnlyEndgame3ChipStrategy;
import com.example.quacksbag.ai.strategy.flask.UseFlaskByPropabilityStrategy;

public class FlaskStrategyFactory {
    public static FlaskStrategy createStrategy(FlaskStrategyOptionWrapper options) {
        return switch (options.getOption()) {
            case ONLY_ENDGAME_3_CHIP -> new OnlyEndgame3ChipStrategy();
            case USE_FLASK_BY_PROBABILITY -> new UseFlaskByPropabilityStrategy(options.getPropability());
        };
    }
}
