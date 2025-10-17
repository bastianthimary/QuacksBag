package com.example.quacksbag.ai.strategy.draw;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;

public class SafeDrawStrategy extends DrawStrategy {
    @Override
    public DrawChoice decideDraw(GameManager gameManager) {
        return canClaudronExplodeNextDraw(gameManager) ? DrawChoice.END_ROUND : DrawChoice.DRAW_NEXT;
    }
}
