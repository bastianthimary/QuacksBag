package com.example.quacksbag.ruleset.implementations;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.player.DecisionMaker;

import java.util.List;

public abstract class Rulset1DecisionMaker implements DecisionMaker {
    public ChoosenChip chooseChip(List<Chip> chips) {
        return new ChoosenChip(chips.get(0));
    }
}
