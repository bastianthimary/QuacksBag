package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.max.strategy.BuyStrategy;

public class ComboResultToBuyStrategyMapper {
    public static BuyStrategy mapToBuyStrategy(ComboResult comboResult) {
        if(comboResult.getChip2() != null) {
            return new BuyStrategy(comboResult.getChip1().getChip(), comboResult.getChip2().getChip());
        }
       return new BuyStrategy(comboResult.getChip1().getChip());
    }
}
