package com.example.quacksbag.ai.strategy.factory.ruby;

import com.example.quacksbag.ai.strategy.rubybuyables.BuyDropTillRound;
import com.example.quacksbag.ai.strategy.rubybuyables.BuyFlaskTillRound;
import com.example.quacksbag.ai.strategy.rubybuyables.RubyBuyableStrategy;

public class RubyBuyableStrategyFactory {
     public static RubyBuyableStrategy createStrategy(RubyBuyableStrategyOptionWrapper option) {
         return switch (option.getOption()) {
             case BUY_DROP_TILL_ROUND
                 -> new BuyDropTillRound(option.getRound());
             case BUY_FLASK_TILL_ROUND -> new BuyFlaskTillRound(option.getRound());
         };
     }
}
