package com.example.quacksbag.baserules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.gamematerial.Claudron;
import com.example.quacksbag.gamematerial.ClaudronPlayersData;
import com.example.quacksbag.player.DecisionMaker;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.player.ExplosionChoice;
import com.example.quacksbag.player.RubyBuyables;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.PriceRuleset;
import com.example.quacksbag.ruleset.Ruleset;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class RoundClaudronTest {

    private RoundClaudron roundClaudron;


    private final Ruleset mockRuleset = new TestRuleset();


    private RoundBagManager roundBagManager;

    private final int DROP_BONUS = 0;


    @Test
    void constructor_initializesCorrectly() {
        roundBagManager = new RoundBagManager(Collections.EMPTY_LIST);
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        assertEquals(DROP_BONUS, roundClaudron.getCurrentPosition());
        assertFalse(roundClaudron.isExploded());
        // Verify mocks were set (optional, as they are used in other tests)
    }


    @Test
    void isExploded_initialIsFalse() {
        roundBagManager = new RoundBagManager(Collections.emptyList());
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        assertFalse(roundClaudron.isExploded());
    }

    @Test
    void putChipInClaudron_nonWhiteChip_updatesPositionWithValueFromRuleset() {
        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        int chipValueFromRuleset = 2; // As per user requirement: ruleset returns chip's value
        List<Chip> expectedChips = List.of(blueChip);
        roundBagManager = new RoundBagManager(expectedChips);
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        roundClaudron.putChipInClaudron(blueChip);

        assertEquals(DROP_BONUS + chipValueFromRuleset, roundClaudron.getCurrentPosition());
        assertFalse(roundClaudron.isExploded());
    }

    @Test
    void putChipInClaudron_whiteChip_updatesPositionAndFirecracker_notExploded() {
        Chip whiteChip1 = new Chip(ChipColor.WHITE, 1);
        Chip whiteChip2 = new Chip(ChipColor.WHITE, 1);
        List<Chip> expectedChips = Arrays.asList(whiteChip1, whiteChip2);
        roundBagManager = new RoundBagManager(expectedChips);
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        roundClaudron.putChipInClaudron(whiteChip1);

        assertEquals(DROP_BONUS + whiteChip1.getValue(), roundClaudron.getCurrentPosition());
        assertEquals(whiteChip1.getValue(), roundClaudron.getFirecrackerPeaCounter());
        assertFalse(roundClaudron.isExploded());

        roundClaudron.putChipInClaudron(whiteChip2);
        assertEquals(DROP_BONUS + whiteChip1.getValue() + whiteChip2.getValue(), roundClaudron.getCurrentPosition());
        assertEquals(whiteChip1.getValue() + whiteChip2.getValue(), roundClaudron.getFirecrackerPeaCounter());
        assertFalse(roundClaudron.isExploded()); // 1 + 2 = 3, less than 7
    }

    @Test
    void putChipInClaudron_whiteChip_explosionOccurs() {
        Chip whiteChip3 = new Chip(ChipColor.WHITE, 3);
        Chip whiteChip4 = new Chip(ChipColor.WHITE, 4);
        List<Chip> expectedChips = Arrays.asList(whiteChip3, whiteChip4);
        roundBagManager = new RoundBagManager(expectedChips);
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        roundClaudron.putChipInClaudron(whiteChip3); // Counter = 3
        assertEquals(DROP_BONUS + 3, roundClaudron.getCurrentPosition());
        assertEquals(3, roundClaudron.getFirecrackerPeaCounter());
        assertFalse(roundClaudron.isExploded());

        roundClaudron.putChipInClaudron(whiteChip4); // Counter = 3 + 4 = 7
        assertEquals(DROP_BONUS + 3 + 4, roundClaudron.getCurrentPosition());
        assertEquals(7, roundClaudron.getFirecrackerPeaCounter());
        assertTrue(roundClaudron.isExploded());
    }

    @Test
    void putChipInClaudron_whiteChip_alreadyExploded_stillAddsChip() {
        Chip whiteChip1 = new Chip(ChipColor.WHITE, 4);
        Chip whiteChip2 = new Chip(ChipColor.WHITE, 3);
        Chip whiteChip3 = new Chip(ChipColor.WHITE, 1);
        roundBagManager = new RoundBagManager(Collections.emptyList());
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        roundClaudron.putChipInClaudron(whiteChip1); // counter = 4
        roundClaudron.putChipInClaudron(whiteChip2); // counter = 4+3 = 7, exploded
        assertTrue(roundClaudron.isExploded());
        assertEquals(7, roundClaudron.getFirecrackerPeaCounter());
        assertEquals(DROP_BONUS + 4 + 3, roundClaudron.getCurrentPosition());

        roundClaudron.putChipInClaudron(whiteChip3); // counter = 7+1 = 8
        assertTrue(roundClaudron.isExploded()); // Stays exploded
        assertEquals(8, roundClaudron.getFirecrackerPeaCounter());
        assertEquals(DROP_BONUS + 4 + 3 + 1, roundClaudron.getCurrentPosition());
    }

    @Test
    void putChipInClaudron_nonWhiteChipAfterWhiteChips_notExploded() {
        Chip whiteChip = new Chip(ChipColor.WHITE, 2);
        Chip blueChip = new Chip(ChipColor.BLUE, 5);

        List<Chip> expectedChips = Arrays.asList(blueChip, whiteChip);
        roundBagManager = new RoundBagManager(expectedChips);
        roundClaudron = new RoundClaudron(new ClaudronPlayersData(DROP_BONUS,false), mockRuleset, roundBagManager,new testDecisionMaker());
        roundClaudron.putChipInClaudron(whiteChip); // firecracker = 2, position = 0 + 2 = 2
        assertEquals(2, roundClaudron.getFirecrackerPeaCounter());
        assertFalse(roundClaudron.isExploded());

        roundClaudron.putChipInClaudron(blueChip); // firecracker still 2, position = 2 + 5 = 7
        assertEquals(2, roundClaudron.getFirecrackerPeaCounter()); // Unchanged
        assertFalse(roundClaudron.isExploded());
        assertEquals(DROP_BONUS + whiteChip.getValue() + blueChip.getValue(), roundClaudron.getCurrentPosition());

    }

    private static class TestRuleset implements Ruleset {

        @Override
        public int determineValueExecuteInsantEffectAndPutInClaudron(Chip chip, RoundBagManager chipsInClaudron, DecisionMaker decisionMaker, RoundClaudron roundClaudron) {
            return chip.getValue();
        }

        @Override
        public void executePurpleRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {

        }

        @Override
        public void executeGreenRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {

        }

        @Override
        public void executeBlackRoundEndEffect(RoundBagManager chipsInClaudron, PlayerScore playerScore) {

        }


        @Override
        public PriceRuleset getPriceRuleset() {
            return null;
        }

    }
    private static class testDecisionMaker implements DecisionMaker {

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
            return RubyBuyables.NONE;
        }

        @Override
        public void setGameManager(GameManager gameManager) {

        }
    }

    @Test
    void testClaudronCreation() {
        Claudron claudron = new Claudron();
        var bubble = claudron.getBubbleForPosition(45);
        System.out.println("Bubble Value: " + bubble.getBubbleValue());
        System.out.println("Victory Points: " + bubble.getVictoryPoints());
        System.out.println("Robin: " + bubble.isRuby());
    }
}
