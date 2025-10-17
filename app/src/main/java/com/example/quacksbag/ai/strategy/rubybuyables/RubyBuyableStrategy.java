package com.example.quacksbag.ai.strategy.rubybuyables;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.RubyBuyables;

public interface RubyBuyableStrategy {
    RubyBuyables decideBuyDropOrFlask(GameManager gameManager);
}
