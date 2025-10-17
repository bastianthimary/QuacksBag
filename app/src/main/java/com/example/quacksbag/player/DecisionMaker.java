package com.example.quacksbag.player;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.List;

public interface DecisionMaker {
    ExplosionChoice makeChoiceOnExplosion();

    List<Chip> doShoppingChoice(int bubbleValue, List<ChipPrice> buyableChips);

    boolean wantToUseFlask(Chip chip);

    DrawChoice doAnotherDraw();

    RubyBuyables buyDropOrFlask();

    void setGameManager(GameManager gameManager);
}
