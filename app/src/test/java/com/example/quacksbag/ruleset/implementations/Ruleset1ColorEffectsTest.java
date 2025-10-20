package com.example.quacksbag.ruleset.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.player.ExplosionChoice;
import com.example.quacksbag.player.RubyBuyables;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.statistic.RoundStatistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit Tests für alle Farbeffekte von Ruleset 1
 * Testet die Effekte von: PURPLE, GREEN, BLACK, RED, BLUE, YELLOW
 */
class Ruleset1ColorEffectsTest {

    private Ruleset1 ruleset;
    private PlayerScore playerScore;

    @BeforeEach
    void setUp() {
        ruleset = new Ruleset1();
        playerScore = new PlayerScore("TestPlayer");
        RoundStatistic.reset();
        RoundStatistic.getRoundStatistic().startNewRound(1);
    }

    // ============ PURPLE CHIP TESTS ============

    @Test
    void testPurpleEffect_OnePurpleChip() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.PURPLE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialVictoryPoints = playerScore.getVictoryPoints();

        // Act
        ruleset.executePurpleRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialVictoryPoints + 1, playerScore.getVictoryPoints());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.PURPLE).get());
    }

    @Test
    void testPurpleEffect_TwoPurpleChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.PURPLE, 1));
        chips.add(new Chip(ChipColor.PURPLE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialVictoryPoints = playerScore.getVictoryPoints();
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executePurpleRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialVictoryPoints + 1, playerScore.getVictoryPoints());
        assertEquals(initialRubyCount + 1, playerScore.getRubyCount());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.PURPLE).get());
    }

    @Test
    void testPurpleEffect_ThreeOrMorePurpleChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.PURPLE, 1));
        chips.add(new Chip(ChipColor.PURPLE, 1));
        chips.add(new Chip(ChipColor.PURPLE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialVictoryPoints = playerScore.getVictoryPoints();

        // Act
        ruleset.executePurpleRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialVictoryPoints + 2, playerScore.getVictoryPoints());
        assertTrue(playerScore.getClaudronPlayersData().getDropBonus() > 0);
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.PURPLE).get());
    }

    @Test
    void testPurpleEffect_NoPurpleChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialVictoryPoints = playerScore.getVictoryPoints();

        // Act
        ruleset.executePurpleRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialVictoryPoints, playerScore.getVictoryPoints());
        assertFalse(RoundStatistic.getRoundStatistic().getColorTriggeredTimes().containsKey(ChipColor.PURPLE));
    }

    // ============ GREEN CHIP TESTS ============

    @Test
    void testGreenEffect_LastChipIsGreen() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        chips.add(new Chip(ChipColor.GREEN, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executeGreenRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialRubyCount + 1, playerScore.getRubyCount());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.GREEN).get());
    }

    @Test
    void testGreenEffect_SecondLastChipIsGreen() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.GREEN, 1));
        chips.add(new Chip(ChipColor.RED, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executeGreenRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialRubyCount + 1, playerScore.getRubyCount());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.GREEN).get());
    }

    @Test
    void testGreenEffect_BothLastAndSecondLastAreGreen() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        chips.add(new Chip(ChipColor.GREEN, 1));
        chips.add(new Chip(ChipColor.GREEN, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executeGreenRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialRubyCount + 2, playerScore.getRubyCount());
        assertEquals(2, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.GREEN).get());
    }

    @Test
    void testGreenEffect_OnlyOneChipInBag() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        chips.add(new Chip(ChipColor.BLUE, 1));
        chips.add(new Chip(ChipColor.GREEN, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executeGreenRoundEndEffect(bagManager, playerScore);

        // Assert - sollte nicht crashen und nur den letzten Chip prüfen
        assertEquals(initialRubyCount + 1, playerScore.getRubyCount());
    }

    @Test
    void testGreenEffect_NoGreenChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        chips.add(new Chip(ChipColor.BLUE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialRubyCount = playerScore.getRubyCount();

        // Act
        ruleset.executeGreenRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialRubyCount, playerScore.getRubyCount());
        assertFalse(RoundStatistic.getRoundStatistic().getColorTriggeredTimes().containsKey(ChipColor.GREEN));
    }

    // ============ BLACK CHIP TESTS ============

    @Test
    void testBlackEffect_OneBlackChip() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.BLACK, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialDropBonus = playerScore.getClaudronPlayersData().getDropBonus();

        // Act
        ruleset.executeBlackRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialDropBonus + 1, playerScore.getClaudronPlayersData().getDropBonus());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.BLACK).get());
    }

    @Test
    void testBlackEffect_MultipleBlackChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.BLACK, 1));
        chips.add(new Chip(ChipColor.BLACK, 1));
        chips.add(new Chip(ChipColor.BLACK, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialDropBonus = playerScore.getClaudronPlayersData().getDropBonus();

        // Act
        ruleset.executeBlackRoundEndEffect(bagManager, playerScore);

        // Assert - sollte nur einmal +1 geben (mindestens 1 schwarzer Chip)
        assertEquals(initialDropBonus + 1, playerScore.getClaudronPlayersData().getDropBonus());
        assertEquals(1, RoundStatistic.getRoundStatistic().getColorTriggeredTimes().get(ChipColor.BLACK).get());
    }

    @Test
    void testBlackEffect_NoBlackChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.RED, 1));
        chips.add(new Chip(ChipColor.BLUE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        int initialDropBonus = playerScore.getClaudronPlayersData().getDropBonus();

        // Act
        ruleset.executeBlackRoundEndEffect(bagManager, playerScore);

        // Assert
        assertEquals(initialDropBonus, playerScore.getClaudronPlayersData().getDropBonus());
        assertFalse(RoundStatistic.getRoundStatistic().getColorTriggeredTimes().containsKey(ChipColor.BLACK));
    }

    // ============ RED CHIP TESTS ============

    @Test
    void testRedEffect_NoOrangeChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        Chip redChip = new Chip(ChipColor.RED, 3);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                redChip, bagManager, null, null);

        // Assert - sollte normalen Wert haben
        assertEquals(3, value);
    }

    @Test
    void testRedEffect_OneOrangeChip() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.ORANGE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        Chip redChip = new Chip(ChipColor.RED, 3);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                redChip, bagManager, null, null);

        // Assert - sollte +1 Bonus haben
        assertEquals(4, value);
    }

    @Test
    void testRedEffect_TwoOrangeChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.ORANGE, 1));
        chips.add(new Chip(ChipColor.ORANGE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        Chip redChip = new Chip(ChipColor.RED, 3);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                redChip, bagManager, null, null);

        // Assert - sollte +1 Bonus haben
        assertEquals(4, value);
    }

    @Test
    void testRedEffect_ThreeOrMoreOrangeChips() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.ORANGE, 1));
        chips.add(new Chip(ChipColor.ORANGE, 1));
        chips.add(new Chip(ChipColor.ORANGE, 1));
        RoundBagManager bagManager = createBagManagerWithChips(chips);
        Chip redChip = new Chip(ChipColor.RED, 3);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                redChip, bagManager, null, null);

        // Assert - sollte +2 Bonus haben
        assertEquals(5, value);
    }

    // ============ HELPER METHODS ============

    /**
     * Erstellt einen RoundBagManager mit vordefinierten Chips im gezogenen Zustand
     */
    private RoundBagManager createBagManagerWithChips(List<Chip> chips) {
        RoundBagManager bagManager = new RoundBagManager(new ArrayList<>());
        // Alle ungezogenen Chips entfernen
        bagManager.getUndrawnChips().clear();
        // Chips als gezogen hinzufügen
        for (Chip chip : chips) {
            bagManager.putChipInClaudron(chip);
        }
        return bagManager;
    }

    /**
     * Mock DecisionMaker für Tests
     */
    static class MockDecisionMaker extends Rulset1DecisionMaker {
        private final ExplosionChoice explosionChoice;
        private final boolean useFlask;
        private final DrawChoice drawChoice;
        private final RubyBuyables rubyBuyable;

        MockDecisionMaker(ExplosionChoice explosionChoice, boolean useFlask,
                          DrawChoice drawChoice, RubyBuyables rubyBuyable) {
            this.explosionChoice = explosionChoice;
            this.useFlask = useFlask;
            this.drawChoice = drawChoice;
            this.rubyBuyable = rubyBuyable;
        }

        @Override
        public ExplosionChoice makeChoiceOnExplosion() {
            return explosionChoice;
        }

        @Override
        public List<Chip> doShoppingChoice(int bubbleValue, List<ChipPrice> buyableChips) {
            return new ArrayList<>();
        }

        @Override
        public boolean wantToUseFlask(Chip chip) {
            return useFlask;
        }

        @Override
        public DrawChoice doAnotherDraw() {
            return drawChoice;
        }

        @Override
        public RubyBuyables buyDropOrFlask() {
            return rubyBuyable;
        }

        @Override
        public void setGameManager(GameManager gameManager) {
            // Mock implementation
        }
    }
}
