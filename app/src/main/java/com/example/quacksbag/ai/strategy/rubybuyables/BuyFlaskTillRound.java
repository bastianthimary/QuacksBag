package com.example.quacksbag.ai.strategy.rubybuyables;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.RubyBuyables;

public class BuyFlaskTillRound implements RubyBuyableStrategy {
    private final int round;

    public BuyFlaskTillRound(int round) {
        this.round = round;
    }

    @Override
    public RubyBuyables decideBuyDropOrFlask(GameManager gameManager) {
        if (gameManager.getCurrentRound() <= round) {
            return RubyBuyables.FLASK;
        }

        return RubyBuyables.NONE;
    }
}
