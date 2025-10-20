package com.example.quacksbag.baserules;

import static com.example.quacksbag.statistic.RoundStatistic.getRoundStatistic;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.gamematerial.ClaudronPlayersData;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.List;


public class RoundClaudron {
    private int currentPosition;
    private int firecrackerPeaCounter;
    private boolean exploded;
    private final ClaudronPlayersData claudronPlayersData;
    private final RoundBagManager roundBagManager;
    private final Ruleset ruleset;
    private final DecisionMaker decisionMaker;

    public RoundClaudron(ClaudronPlayersData claudronPlayersData, Ruleset ruleset, RoundBagManager roundBagManager, DecisionMaker decisionMaker) {
        this.currentPosition = claudronPlayersData.getDropBonus();
        this.claudronPlayersData = claudronPlayersData;
        this.ruleset = ruleset;
        this.roundBagManager = roundBagManager;
        this.decisionMaker = decisionMaker;
        this.exploded = false;
    }

    public List<Chip> getChipsInClaudron() {
        return roundBagManager.getDrawnChips();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getFirecrackerPeaCounter() {
        return firecrackerPeaCounter;
    }

    public boolean isExploded() {
        return exploded;
    }

    public RoundBagManager getRoundBagManager() {
        return roundBagManager;
    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public void putChipInClaudron(Chip chip) {
        int value;
        if (ChipColor.WHITE.equals(chip.getColor())) {
            determineExplosion(chip);
            if (checkFlaskOption(chip)) {
                return;
            }
            roundBagManager.putChipInClaudron(chip);
            value = chip.getValue();

        } else {
            value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(chip, roundBagManager, decisionMaker, this);
        }
        currentPosition = currentPosition + value;
    }

    private void determineExplosion(Chip chip) {
        firecrackerPeaCounter = firecrackerPeaCounter + chip.getValue();
        if (firecrackerPeaCounter >= 7) {
            exploded = true;
            getRoundStatistic().explode();
        }
    }

    private boolean checkFlaskOption(Chip chip) {
        if (!exploded && claudronPlayersData.isFlaskFilled()) {
            boolean wantUseFlask = decisionMaker.wantToUseFlask(chip);
            if (wantUseFlask) {
                claudronPlayersData.useFlask();
                roundBagManager.throwChipBackInBag(chip);
                return true;
            }
        }
        return false;
    }
}
