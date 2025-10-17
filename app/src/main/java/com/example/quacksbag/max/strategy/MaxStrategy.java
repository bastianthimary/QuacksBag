package com.example.quacksbag.max.strategy;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.max.strategy.chipset.StrategySteps;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MaxStrategy {

    public abstract ArrayList<Chip> determineBestChipSetForCurrentRound(ArrayList<Chip> chipsInBag, Ruleset ruleset,boolean canExplode);

    public abstract HashMap<Integer, BuyStrategy> determineBuyStrategyByRuleSet(Ruleset ruleset);
    public abstract HashMap<StrategySteps,HashMap<Integer,BuyStrategy>> determineBuyStrategyForChipStrategy();
}
