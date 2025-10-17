package com.example.quacksbag.max;

import com.example.quacksbag.baserules.BagManager;
import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.baserules.RoundFinishManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.BuyStrategy;
import com.example.quacksbag.max.strategy.MaxStrategy;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.player.ExplosionChoice;
import com.example.quacksbag.player.RubyBuyables;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MaxGame {
    private MaxStrategy maxStrategy;
    private Ruleset ruleset;
    private PlayerScore playerScore;
    private BagManager bagManager;
    private DecisionMaker decisionMaker;

    public int playMaxGame() {
        playerScore = new PlayerScore("Max");
        HashMap<Integer, BuyStrategy> buyStrategy = maxStrategy.determineBuyStrategyByRuleSet(ruleset);

        RoundBagManager roundBagManager = new RoundBagManager(Collections.emptyList());
        for (int currentRound = 1; currentRound < 10; currentRound++) {
            RoundClaudron roundClaudron = new RoundClaudron(playerScore.getClaudronPlayersData(),
                    ruleset, new RoundBagManager(bagManager.getPurchasedChips()), decisionMaker);

            var chipsForRound = maxStrategy.determineBestChipSetForCurrentRound(roundBagManager.getUndrawnChips(), ruleset, true);

            chipsForRound.forEach(chip -> {
                Chip drawedChip = roundBagManager.drawChipFromBag(
                        getIdForAChipWithValue(chip.getValue(),
                                chip.getColor(), roundBagManager.getUndrawnChips()));
                roundClaudron.putChipInClaudron(drawedChip);
            });


            roundClaudron.getChipsInClaudron();
            RoundFinishManager roundFinishManager = new RoundFinishManager(roundClaudron, ruleset, playerScore, decisionMaker, currentRound);
            roundFinishManager.excecuteRoundFinish();
        }
        return playerScore.getVictoryPoints();
    }

    private int getIdForAChipWithValue(int value, ChipColor chipColor, ArrayList<Chip> chips) {
        var searchedChip = chips.stream()
                .filter(chip -> chip.getValue() == value && chip.getColor() == chipColor)
                .findFirst();
        return searchedChip.map(chips::indexOf).orElse(-1);
    }


    private class MaxDecisionMaker implements DecisionMaker {
        @Override
        public ExplosionChoice makeChoiceOnExplosion() {
            return null;
        }

        @Override
        public List<Chip> doShoppingChoice(int bubbleValue, List<ChipPrice> buyableChips) {
            return Collections.emptyList();
        }

        @Override
        public boolean wantToUseFlask(Chip chip) {
            return false;
        }

        @Override
        public DrawChoice doAnotherDraw() {
            return null;
        }

        @Override
        public RubyBuyables buyDropOrFlask() {
            return null;
        }

        @Override
        public void setGameManager(GameManager gameManager) {

        }
    }
}
