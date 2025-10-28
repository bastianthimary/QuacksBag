package com.example.quacksbag.ai.strategy.factory;

import com.example.quacksbag.ai.AiDecisionMakerRuleSet1;
import com.example.quacksbag.ai.strategy.draw.DrawStrategy;
import com.example.quacksbag.ai.strategy.explosion.ExplosionStrategy;
import com.example.quacksbag.ai.strategy.factory.draw.DrawStrategyFactory;
import com.example.quacksbag.ai.strategy.factory.draw.DrawStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.explosion.ExplosionStrategyFactory;
import com.example.quacksbag.ai.strategy.factory.explosion.ExplosionStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.flask.FlaskStrategyFactory;
import com.example.quacksbag.ai.strategy.factory.flask.FlaskStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.ruby.RubyBuyableStrategyFactory;
import com.example.quacksbag.ai.strategy.factory.ruby.RubyBuyableStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.shopping.ShoppingStrategyFactory;
import com.example.quacksbag.ai.strategy.factory.shopping.ShoppingStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.flask.FlaskStrategy;
import com.example.quacksbag.ai.strategy.rubybuyables.RubyBuyableStrategy;
import com.example.quacksbag.ai.strategy.shopping.ShoppingStrategy;

public class AiDesisionMakerRuleSet1Factory {
    public static AiDecisionMakerRuleSet1 createAiDesisionMakerRuleSet1(
            DrawStrategyOptionWrapper drawStrategyOption,
            ExplosionStrategyOptionWrapper explosionStrategyOption,
            ShoppingStrategyOptionWrapper shoppingStrategyOptionWrapper,
            FlaskStrategyOptionWrapper flaskStrategyOption,
            RubyBuyableStrategyOptionWrapper rubyBuyableStrategyOption) {
        DrawStrategy drawStrategy = DrawStrategyFactory.createStrategy(drawStrategyOption);
        ExplosionStrategy explosionStrategy = ExplosionStrategyFactory.createStrategy(explosionStrategyOption);
        ShoppingStrategy shoppingStrategy = ShoppingStrategyFactory.createStrategy(shoppingStrategyOptionWrapper);
        FlaskStrategy flaskStrategy = FlaskStrategyFactory.createStrategy(flaskStrategyOption);
        RubyBuyableStrategy rubyBuyableStrategy = RubyBuyableStrategyFactory.createStrategy(rubyBuyableStrategyOption);
        return new AiDecisionMakerRuleSet1(drawStrategy, explosionStrategy, shoppingStrategy, flaskStrategy, rubyBuyableStrategy);
    }
}
