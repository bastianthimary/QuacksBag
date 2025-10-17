package com.example.quacksbag.ai.strategy.factory.ruby;

import static com.example.quacksbag.ai.strategy.factory.ruby.RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND;

import com.example.quacksbag.ai.strategy.rubybuyables.BuyDropTillRound;
import com.example.quacksbag.ai.strategy.rubybuyables.RubyBuyableStrategy;

public class RubyBuyableStrategyFactory {
     public static RubyBuyableStrategy createStrategy(RubyBuyableStrategyOptionWrapper option) {
         return switch (option.getOption()) {
             case BUY_DROP_TILL_ROUND
                 -> new BuyDropTillRound(option.getRound());
         };
     }
}
