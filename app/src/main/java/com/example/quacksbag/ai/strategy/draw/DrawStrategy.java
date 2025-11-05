package com.example.quacksbag.ai.strategy.draw;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.util.RoundClaudronUtil;

public abstract class DrawStrategy {
    public abstract DrawChoice decideDraw(GameManager gameManager);

    public boolean canClaudronExplodeNextDraw(GameManager gameManager) {
        RoundClaudron roundClaudron = gameManager.getRoundClaudron();
        int peaCounter = roundClaudron.getFirecrackerPeaCounter();
        if (peaCounter < 4) {
            return false;
        }
        if (peaCounter == 4) {
            return new RoundClaudronUtil(roundClaudron).containsWhite3Chip();
        }
        return true;
    }

}
