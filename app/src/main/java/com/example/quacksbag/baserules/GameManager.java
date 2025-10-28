package com.example.quacksbag.baserules;

import static com.example.quacksbag.statistic.GameStatistic.getGameStatistic;
import static com.example.quacksbag.statistic.RoundStatistic.getRoundStatistic;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.Claudron;
import com.example.quacksbag.logging.Logger;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.ruleset.Ruleset;

public class GameManager {

    private final Ruleset ruleset;
    private final PlayerScore playerScore;
    private final DecisionMaker decisionMaker;
    private RoundClaudron roundClaudron;
    private int currentRound;

    public GameManager(Ruleset ruleset, String name, DecisionMaker decisionMaker) {
        playerScore = new PlayerScore(name);
        this.ruleset = ruleset;
        this.decisionMaker = decisionMaker;
        decisionMaker.setGameManager(this);
    }

    public int playGame() {
        for (currentRound = 1; currentRound < 10; currentRound++) {
            getRoundStatistic().startNewRound(currentRound);
            Logger.info("Round: " + currentRound);
            BagManager bagManager = playerScore.getBagManager();
            roundClaudron = new RoundClaudron(playerScore.getClaudronPlayersData(), ruleset,
                    new RoundBagManager(bagManager.getPurchasedChips()), decisionMaker);
            DrawChoice drawChoice = DrawChoice.DRAW_NEXT;
            while (!roundClaudron.isExploded() && drawChoice == DrawChoice.DRAW_NEXT) {
                Chip chip = BagDrawer.drawRandomChipAndUpdateBag(roundClaudron.getRoundBagManager());
                roundClaudron.putChipInClaudron(chip);
                drawChoice = decisionMaker.doAnotherDraw();
                if (reachedEndOfClaudron()) {
                    drawChoice = DrawChoice.END_ROUND;
                }
            }
            Logger.info("Go to Finish Round: " + currentRound);
            RoundFinishManager roundFinishManager = new RoundFinishManager(roundClaudron, ruleset, playerScore, decisionMaker, currentRound);
            Logger.debug("Init RoundFinishManager finished");
            roundFinishManager.excecuteRoundFinish();
            doStatistics();
        }
        var victoryPoints = playerScore.getVictoryPoints();
        Logger.result("Game finished with " + victoryPoints + " victory points");
        return victoryPoints;
    }

    private boolean reachedEndOfClaudron() {
        return roundClaudron.getCurrentPosition() >= 53;
    }

    private void doStatistics() {
        var position = roundClaudron.getCurrentPosition();
        getRoundStatistic().setEndposition(position);
        var bubble = new Claudron().getBubbleForPosition(position);
        getRoundStatistic().setShoppingPoints(bubble.getBubbleValue());
        getRoundStatistic().setWasRubyBubble(bubble.isRuby());
        getRoundStatistic().setVictoryPoints(bubble.getVictoryPoints());
        getRoundStatistic().setDrawnChips(roundClaudron.getRoundBagManager().getDrawnChips());
        getRoundStatistic().setAllChipsInBag(playerScore.getBagManager().getPurchasedChips());
        getGameStatistic().updateFromRoundStatistic(getRoundStatistic());
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public PlayerScore getPlayerScore() {
        return playerScore;
    }

    public RoundClaudron getRoundClaudron() {
        return roundClaudron;
    }
}
