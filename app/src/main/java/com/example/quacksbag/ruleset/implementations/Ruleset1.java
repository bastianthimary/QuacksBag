package com.example.quacksbag.ruleset.implementations;

import static com.example.quacksbag.gamematerial.ChipColor.BLUE;
import static com.example.quacksbag.gamematerial.ChipColor.YELLOW;

import com.example.quacksbag.baserules.BagDrawer;
import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.baserules.UndrawnChipsUtil;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.logging.Logger;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.ruleset.PriceRuleset;
import com.example.quacksbag.ruleset.Ruleset;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ruleset1 implements Ruleset {
    private Rulset1DecisionMaker decisionMaker;
    private RoundClaudron roundClaudron;

    @Override
    public int determineValueExecuteInsantEffectAndPutInClaudron(Chip chip, RoundBagManager roundBagManager, DecisionMaker decisionMaker, RoundClaudron roundClaudron) {

        this.decisionMaker = (Rulset1DecisionMaker) decisionMaker;
        this.roundClaudron = roundClaudron;
        int value = determineValue(chip, roundBagManager);
        roundBagManager.putChipInClaudron(chip);
        executeInstantEffect(chip, roundBagManager);

        return value;
    }

    @Override
    public void executePurpleRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        long purpleCount = chipsInClaudron.getDrawnChips().stream().filter(chip -> chip.getColor().equals(ChipColor.PURPLE)).count();
        if (purpleCount == 1) {
            playerScore.addVictoryPoints(1);
        } else if (purpleCount == 2) {
            playerScore.addVictoryPoints(1);
            playerScore.addRuby();
        } else if (purpleCount >= 3) {
            playerScore.addVictoryPoints(2);
            playerScore.addClaudronDropBonus();
        }
    }

    @Override
    public void executeGreenRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        var drawnChips = chipsInClaudron.getDrawnChips();
        int numberOfChips = drawnChips.size();
        if (ChipColor.GREEN.equals(drawnChips.get(numberOfChips - 1).getColor())) {
            playerScore.addRuby();
        }
        if (ChipColor.GREEN.equals(drawnChips.get(numberOfChips - 2).getColor())) {
            playerScore.addRuby();
        }
    }

    @Override
    public void executeBlackRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        var drawnChips = chipsInClaudron.getDrawnChips();
        if (UndrawnChipsUtil.containsChipOfColor(drawnChips, ChipColor.BLACK)) {
            Logger.resultBase("Black Triggered");
            playerScore.addClaudronDropBonus();
        }
    }

    private void executeInstantEffect(Chip chip, RoundBagManager roundBagManager) {
        if (BLUE.equals(chip.getColor())) {
            doBlueEffect(chip, roundBagManager);
        } else if (YELLOW.equals(chip.getColor())) {
            doYellowEffect(roundBagManager);
        }
    }

    private void doBlueEffect(Chip chip, RoundBagManager chipsInClaudron) {
        int howManyChipsWillDrawn = chip.getValue();
        var drawnChips = drawChips(chipsInClaudron, howManyChipsWillDrawn);
        ChoosenChip choosenChip = decisionMaker.chooseChip(drawnChips);

        removeChoosenChipFromDrawnChips(choosenChip, drawnChips);
        throwChipsBackInBag(chipsInClaudron, drawnChips);

        putChipInClaudron(choosenChip);
    }

    private void doYellowEffect(RoundBagManager roundBagManager) {
        var chipsInClaudron = roundBagManager.getDrawnChips();
        var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 2);
        //vorletzte da Gelb bereits hinzugefügt wurde in der runde
        if (ChipColor.WHITE.equals(lastChip.getColor())) {
            roundBagManager.throwChipBackInBag(lastChip);
        }
    }

    private static void removeChoosenChipFromDrawnChips(ChoosenChip choosenChip, List<Chip> drawnChips) {
        if (choosenChip != null) {
            drawnChips.remove(choosenChip.getChip());
        }
    }

    private void putChipInClaudron(ChoosenChip choosenChip) {
        if (choosenChip != null) {
            roundClaudron.putChipInClaudron(choosenChip.getChip());
        }
    }

    private static void throwChipsBackInBag(RoundBagManager chipsInClaudron, List<Chip> drawnChips) {
        drawnChips.forEach(chipsInClaudron::throwChipBackInBag);
    }

    private List<Chip> drawChips(RoundBagManager chipsInClaudron, int howManyChipsWillDrawn) {
        return IntStream.range(0, howManyChipsWillDrawn)
                .mapToObj(i -> BagDrawer.drawRandomChipAndUpdateBag(chipsInClaudron)).collect(Collectors.toList());
    }

    private int determineValue(Chip chip, RoundBagManager chipsInClaudron) {
        if (chip.getColor().equals(ChipColor.RED)) {
            return determineRedValue(chip, chipsInClaudron);
        }
        return chip.getValue();
    }

    private int determineRedValue(Chip redChip, RoundBagManager chipsInClaudron) {
        long orangeChipsCount = chipsInClaudron.getDrawnChips().stream()
                .filter(chip -> chip.getColor().equals(ChipColor.ORANGE)).count();
        if (orangeChipsCount == 0) {
            return redChip.getValue();
        } else if (orangeChipsCount == 1 || orangeChipsCount == 2) {
            return redChip.getValue() + 1;
        } else {
            return redChip.getValue() + 2;
        }
    }


    public PriceRuleset getPriceRuleset() {
        return new PriceRuleset1();
    }
}
