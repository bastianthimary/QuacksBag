package com.example.quacksbag.ruleset.implementations;

import static com.example.quacksbag.gamematerial.ChipColor.BLUE;
import static com.example.quacksbag.gamematerial.ChipColor.YELLOW;
import static com.example.quacksbag.statistic.RoundStatistic.getRoundStatistic;

import com.example.quacksbag.baserules.BagDrawer;
import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.baserules.UndrawnChipsUtil;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
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
        value += executeInstantEffect(chip, roundBagManager);

        return value;
    }

    @Override
    public void executePurpleRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        long purpleCount = chipsInClaudron.getDrawnChips().stream().filter(chip -> chip.getColor().equals(ChipColor.PURPLE)).count();
        if (purpleCount == 1) {
            playerScore.addVictoryPoints(1);
            getRoundStatistic().incrementColorTriggered(ChipColor.PURPLE);

        } else if (purpleCount == 2) {
            playerScore.addVictoryPoints(1);
            playerScore.addRuby();
            getRoundStatistic().incrementColorTriggered(ChipColor.PURPLE);
        } else if (purpleCount >= 3) {
            playerScore.addVictoryPoints(2);
            playerScore.addClaudronDropBonus();
            getRoundStatistic().incrementColorTriggered(ChipColor.PURPLE);
        }
    }

    @Override
    public void executeGreenRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        var drawnChips = chipsInClaudron.getDrawnChips();
        int numberOfChips = drawnChips.size();
        if (numberOfChips < 2) {
            return;
        }
        if (ChipColor.GREEN.equals(drawnChips.get(numberOfChips - 1).getColor())) {
            playerScore.addRuby();
            getRoundStatistic().incrementColorTriggered(ChipColor.GREEN);
        }
        if (ChipColor.GREEN.equals(drawnChips.get(numberOfChips - 2).getColor())) {
            playerScore.addRuby();
            getRoundStatistic().incrementColorTriggered(ChipColor.GREEN);
        }
    }

    @Override
    public void executeBlackRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {
        var drawnChips = chipsInClaudron.getDrawnChips();
        if (UndrawnChipsUtil.containsChipOfColor(drawnChips, ChipColor.BLACK)) {
            getRoundStatistic().incrementColorTriggered(ChipColor.BLACK);
            playerScore.addClaudronDropBonus();
        }
    }

    private int executeInstantEffect(Chip chip, RoundBagManager roundBagManager) {
        if (BLUE.equals(chip.getColor())) {
            getRoundStatistic().incrementColorTriggered(ChipColor.BLUE);
            return doBlueEffect(chip, roundBagManager);
        } else if (YELLOW.equals(chip.getColor())) {
            getRoundStatistic().incrementColorTriggered(ChipColor.YELLOW);
            return doYellowEffect(roundBagManager);
        }
        return 0;
    }

    private int doBlueEffect(Chip chip, RoundBagManager chipsInRoundBag) {
        int howManyChipsWillDrawn = chip.getValue();
        var drawnChips = drawChips(chipsInRoundBag, howManyChipsWillDrawn);
        ChoosenChip choosenChip = decisionMaker.chooseChipForBlueEffect(drawnChips);

        removeChoosenChipFromDrawnChips(choosenChip, drawnChips);
        // Lege die nicht gewählten Chips direkt zurück in undrawnChipsInBag
        // (nicht über throwChipBackInBag, da diese Chips nie in drawnChips waren)
        drawnChips.forEach(chipsInRoundBag.getUndrawnChips()::add);
        if (choosenChip == null || choosenChip.getChip() == null) {
            return 0;
        }
        putChipInClaudron(choosenChip);
        return choosenChip.getChip().getValue();
    }

    private int doYellowEffect(RoundBagManager roundBagManager) {
        var chipsInClaudron = roundBagManager.getDrawnChips();
        if (chipsInClaudron.size() < 2) {
            return 0;
        }
        var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 2);
        //vorletzte da Gelb bereits hinzugefügt wurde in der runde
        if (ChipColor.WHITE.equals(lastChip.getColor())) {
            roundBagManager.throwChipBackInBag(lastChip);
            return lastChip.getValue() * -1;
        }
        return 0;
    }

    private static void removeChoosenChipFromDrawnChips(ChoosenChip choosenChip, List<Chip> drawnChips) {
        if (choosenChip != null) {
            drawnChips.remove(choosenChip.getChip());
        }
    }

    private void putChipInClaudron(ChoosenChip choosenChip) {
        if (choosenChip != null && choosenChip.getChip() != null) {
            // Chip direkt in den Claudron legen, OHNE weitere Effektverarbeitung
            // um Rekursion zu vermeiden (der Chip wurde bereits durch doBlueEffect gezogen)
            roundClaudron.getRoundBagManager().putChipInClaudron(choosenChip.getChip());
        }
    }

    private static void throwChipsBackInBag(RoundBagManager chipsInClaudron, List<Chip> drawnChips) {
        drawnChips.forEach(chipsInClaudron::throwChipBackInBag);
    }

    private List<Chip> drawChips(RoundBagManager chipsInClaudron, int howManyChipsWillDrawn) {
        return IntStream.range(0, howManyChipsWillDrawn)
                .mapToObj(i -> BagDrawer.drawRandomChipAndUpdateBag(chipsInClaudron))
                .filter(chip -> chip != null)  // Null-Chips filtern (z.B. wenn Bag leer ist)
                .collect(Collectors.toList());
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
