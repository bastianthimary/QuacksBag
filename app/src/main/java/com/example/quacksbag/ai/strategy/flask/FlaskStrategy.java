package com.example.quacksbag.ai.strategy.flask;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;

public interface FlaskStrategy {
    boolean decideUseFlask(GameManager gameManager, Chip chip);

}
