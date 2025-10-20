package com.example.quacksbag.baserules;

import static com.example.quacksbag.statistic.GameStatistic.getGameStatistic;

import com.example.quacksbag.gamematerial.Bubble;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.Claudron;
import com.example.quacksbag.logging.Logger;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.player.ExplosionChoice;
import com.example.quacksbag.player.RubyBuyables;
import com.example.quacksbag.player.ShoppingUtil;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.List;

public class RoundFinishManager {
    private final RoundClaudron roundClaudron;
    private final Claudron claudron;
    private final Ruleset ruleset;
    private final PlayerScore playerScore;
    private final DecisionMaker decisionMaker;
    private final int currentRound;


    public RoundFinishManager(RoundClaudron roundClaudron, Ruleset ruleset, PlayerScore playerScore, DecisionMaker decisionMaker, int currentRound) {
        this.roundClaudron = roundClaudron;
        this.ruleset = ruleset;
        this.playerScore = playerScore;
        this.decisionMaker = decisionMaker; // DecisionMaker initialisieren
        claudron = new Claudron();
        this.currentRound = currentRound;
    }

    public void excecuteRoundFinish() {

        excecuteChipEffects();
        excecuteEarnings();
        buyDropOrFlask();
    }


    private void excecuteChipEffects() {
        ruleset.executeBlackRoundEndEffect(roundClaudron.getRoundBagManager(), playerScore);
        ruleset.executeGreenRoundEndEffect(roundClaudron.getRoundBagManager(), playerScore);
        ruleset.executePurpleRoundEndEffect(roundClaudron.getRoundBagManager(), playerScore);
        Logger.debug("Chip Effects finished");
    }

    private void excecuteEarnings() {
        if (isRubyField()) {
            playerScore.addRuby();
        }
        if (currentRound == 9) {
            doLastRoundVictoryPointCalculation();
            return;
        }
        if (roundClaudron.isExploded()) {
            ExplosionChoice choice = decisionMaker.makeChoiceOnExplosion();
            executeShoppingOrTakeVictoryPoints(choice);
        } else {
            executeShoppingOrTakeVictoryPoints(ExplosionChoice.VICTORYPOINTS);
            executeShoppingOrTakeVictoryPoints(ExplosionChoice.BUBBLESHOPPING);
        }
        Logger.debug("Earnings finished");
    }

    private void doLastRoundVictoryPointCalculation() {
        var bubble = claudron.getBubbleForPosition(roundClaudron.getCurrentPosition());
        int bubbleValue = calculateVictoryPointsForPosition(bubble.getBubbleValue());
        playerScore.addVictoryPoints(bubbleValue);
        Logger.result("Last round victory points calculation:  Points=" + bubbleValue);
        playerScore.addVictoryPoints(calculateVictoryPointsForRubyAtLastRound(playerScore.getRubyCount()));
    }

    protected int calculateVictoryPointsForPosition(int position) {
        if (position < 5) {
            return 0;
        }
        return (position - 5) / 5 + 1;
    }

    private int calculateVictoryPointsForRubyAtLastRound(int rubyCount) {
        if (rubyCount < 2) {
            return 0;
        }
        return (rubyCount - 1) / 2 + 1;
    }

    private boolean isRubyField() {
        var bubble = claudron.getBubbleForPosition(roundClaudron.getCurrentPosition());
        return bubble.isRuby();
    }

    private void executeShoppingOrTakeVictoryPoints(ExplosionChoice choice) {
        Bubble reachedBubble = claudron.getBubbleForPosition(roundClaudron.getCurrentPosition());

        if (ExplosionChoice.VICTORYPOINTS.equals(choice)) {
            addVictoryPoints(reachedBubble);
        } else {
            doShopping(reachedBubble);
        }
    }

    private void addVictoryPoints(Bubble reachedBubble) {
        playerScore.addVictoryPoints(reachedBubble.getVictoryPoints());
    }

    private void doShopping(Bubble reachedBubble) {
        Logger.result("Do Shopping Round: " + currentRound + " BubbleValue: " + reachedBubble.getBubbleValue() + "");
        var shoppingList = ShoppingUtil.determineShoppingList(ruleset, currentRound);
        List<Chip> buyedChips = decisionMaker.doShoppingChoice(reachedBubble.getBubbleValue(), shoppingList);
        buyedChips.forEach(chip -> {
            playerScore.getBagManager().purchaseChipPreset(chip);
            getGameStatistic().addBuyedChip(chip);
        });

    }

    private void buyDropOrFlask() {
        RubyBuyables rubyBuyAble;
        do {
            if (playerScore.getRubyCount() >= 2) {
                rubyBuyAble = decisionMaker.buyDropOrFlask();
                excecuteRubyBuyAble(rubyBuyAble);
            } else {
                rubyBuyAble = RubyBuyables.NONE;
            }
        } while (!RubyBuyables.NONE.equals(rubyBuyAble));
        Logger.debug("BuyDropOrFlask finished");
    }

    private void excecuteRubyBuyAble(RubyBuyables rubyBuyAble) {
        if (RubyBuyables.DROP.equals(rubyBuyAble)) {
            playerScore.payRuby();
            playerScore.addClaudronDropBonus();
        } else if (RubyBuyables.FLASK.equals(rubyBuyAble)) {
            if (playerScore.fillFlask()) {
                playerScore.payRuby();
            }
        }
    }
}
