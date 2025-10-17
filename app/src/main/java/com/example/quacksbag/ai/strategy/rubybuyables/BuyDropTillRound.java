package com.example.quacksbag.ai.strategy.rubybuyables;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.RubyBuyables;

public class BuyDropTillRound implements RubyBuyableStrategy {
    private final int round;

    public BuyDropTillRound(int round) {
        this.round = round;
    }

    @Override
    public RubyBuyables decideBuyDropOrFlask(GameManager gameManager) {
        if (gameManager.getCurrentRound() <= round) {
            return RubyBuyables.DROP;
        }

        return RubyBuyables.NONE;
    }
}
