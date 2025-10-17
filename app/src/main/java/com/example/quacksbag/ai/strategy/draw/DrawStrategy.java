package com.example.quacksbag.ai.strategy.draw;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;

public abstract class DrawStrategy {
       public abstract DrawChoice decideDraw(GameManager gameManager);

       public boolean canClaudronExplodeNextDraw(GameManager gameManager){
              RoundClaudron roundClaudron = gameManager.getRoundClaudron();
              int peaCounter = roundClaudron.getFirecrackerPeaCounter();
              if (peaCounter < 4) {
                     return false;
              }
              if (peaCounter == 4) {
                     if (roundClaudron.getRoundBagManager().getDrawnChips().stream().anyMatch(chip -> chip.getColor().equals(ChipColor.WHITE) && chip.getValue() == 3)) {
                            return false;
                     }
              }
              return true;
       }
}
