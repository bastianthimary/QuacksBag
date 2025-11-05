package com.example.quacksbag.ai.strategy.flask;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;

public class OnlyEndgame3ChipStrategy implements FlaskStrategy {
    @Override
    public boolean decideUseFlask(GameManager gameManager, Chip chip) {
        return gameManager.getCurrentRound() > 7
                && chip.getValue() == 3;
    }
}
