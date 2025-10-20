package com.example.quacksbag.ruleset.implementations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.gamematerial.ClaudronPlayersData;
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
 * Unit Tests für Yellow-Chip Effekt mit Mock DecisionMaker
 * Yellow-Effekt: Wirft den letzten weißen Chip (falls vorhanden) zurück in den Bag
 */
class Ruleset1YellowEffectTest {

    private Ruleset1 ruleset;
    private RoundBagManager bagManager;
    private RoundClaudron roundClaudron;
    private MockYellowDecisionMaker decisionMaker;

    @BeforeEach
    void setUp() {
        ruleset = new Ruleset1();
        RoundStatistic.reset();
        RoundStatistic.getRoundStatistic().startNewRound(1);
        decisionMaker = new MockYellowDecisionMaker();
    }

    @Test
    void testYellowEffect_LastChipIsWhite_ChipThrowsBack() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));
        chipsInBag.add(new Chip(ChipColor.GREEN, 1));

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        // Kessel: RED, dann WHITE
        bagManager.putChipInClaudron(new Chip(ChipColor.RED, 1));
        Chip whiteChip = new Chip(ChipColor.WHITE, 1);
        bagManager.putChipInClaudron(whiteChip);
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();
        int undrawnChipsBeforeYellow = bagManager.getUndrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                yellowChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        assertEquals(0, value);
        // Weißer Chip sollte zurückgeworfen sein
        assertEquals(drawnChipsBeforeYellow, bagManager.getDrawnChips().size());
        assertEquals(undrawnChipsBeforeYellow + 1, bagManager.getUndrawnChips().size());
        // Yellow sollte im Kessel sein
        assertTrue(bagManager.getDrawnChips().stream()
                .anyMatch(c -> ChipColor.YELLOW.equals(c.getColor())));
    }

    @Test
    void testYellowEffect_LastChipIsNotWhite_ChipStays() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        // Kessel: RED, dann BLUE
        bagManager.putChipInClaudron(new Chip(ChipColor.RED, 1));
        Chip blueChip = new Chip(ChipColor.BLUE, 1);
        bagManager.putChipInClaudron(blueChip);
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                yellowChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        assertEquals(1, value);
        // Blauer Chip sollte bleiben, Yellow hinzugefügt
        assertEquals(drawnChipsBeforeYellow + 1, bagManager.getDrawnChips().size());
        assertTrue(bagManager.getDrawnChips().stream()
                .anyMatch(c -> ChipColor.BLUE.equals(c.getColor())));
    }

    @Test
    void testYellowEffect_OnlyYellowInClaudron_NoError() {
        // Arrange
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        // Act & Assert - sollte nicht crashen
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        assertDoesNotThrow(() -> {
            ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                    yellowChip, bagManager, decisionMaker, roundClaudron);
        });

        assertEquals(1, bagManager.getDrawnChips().size());
    }

    @Test
    void testYellowEffect_MultipleWhiteChips_OnlyLastThrowsBack() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        // Kessel: WHITE(1), WHITE(2), dann YELLOW
        Chip white1 = new Chip(ChipColor.WHITE, 1);
        Chip white2 = new Chip(ChipColor.WHITE, 2);
        bagManager.putChipInClaudron(white1);
        bagManager.putChipInClaudron(white2);
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                yellowChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        // Nur WHITE(2) sollte zurückgeworfen sein, WHITE(1) bleibt
        assertEquals(drawnChipsBeforeYellow, bagManager.getDrawnChips().size());
        long whiteCount = bagManager.getDrawnChips().stream()
                .filter(c -> ChipColor.WHITE.equals(c.getColor())).count();
        assertEquals(1, whiteCount); // WHITE(1) bleibt
    }

    @Test
    void testYellowEffect_ColorTriggered() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        bagManager.putChipInClaudron(new Chip(ChipColor.RED, 1));

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                yellowChip, bagManager, decisionMaker, roundClaudron);

        // Assert - Yellow sollte als ausgelöst tracked werden
        assertEquals(1, RoundStatistic.getRoundStatistic()
                .getColorTriggeredTimes().get(ChipColor.YELLOW).get());
    }

    @Test
    void testYellowEffect_WhiteChipWithValue2() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        bagManager.putChipInClaudron(new Chip(ChipColor.RED, 1));
        Chip whiteChip2 = new Chip(ChipColor.WHITE, 2);
        bagManager.putChipInClaudron(whiteChip2);
        int drawnBefore = bagManager.getDrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                yellowChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        // WHITE(2) sollte zurückgeworfen sein
        assertEquals(drawnBefore, bagManager.getDrawnChips().size());
        assertFalse(bagManager.getDrawnChips().stream()
                .anyMatch(c -> ChipColor.WHITE.equals(c.getColor()) && c.getValue() == 2));
    }

    // ============ MOCK DECISION MAKER ============

    /**
     * Mock DecisionMaker speziell für Yellow-Effekt Tests
     */
    static class MockYellowDecisionMaker extends Rulset1DecisionMaker {

        @Override
        public ExplosionChoice makeChoiceOnExplosion() {
            return ExplosionChoice.VICTORYPOINTS;
        }

        @Override
        public List<Chip> doShoppingChoice(int bubbleValue, List<ChipPrice> buyableChips) {
            return new ArrayList<>();
        }

        @Override
        public boolean wantToUseFlask(Chip chip) {
            return false;
        }

        @Override
        public DrawChoice doAnotherDraw() {
            return DrawChoice.END_ROUND;
        }

        @Override
        public RubyBuyables buyDropOrFlask() {
            return RubyBuyables.NONE;
        }

        @Override
        public void setGameManager(GameManager gameManager) {
            // Mock implementation
        }
    }
}
