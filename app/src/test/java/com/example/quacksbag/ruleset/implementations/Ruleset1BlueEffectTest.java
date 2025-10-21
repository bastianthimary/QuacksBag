package com.example.quacksbag.ruleset.implementations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * Unit Tests für Blue-Chip Effekt mit Mock DecisionMaker
 * Blue-Effekt: Zieht N Chips aus dem Bag, Spieler wählt einen, Rest geht zurück
 */
class Ruleset1BlueEffectTest {

    private Ruleset1 ruleset;
    private RoundBagManager bagManager;
    private RoundClaudron roundClaudron;
    private MockBlueDecisionMaker decisionMaker;

    @BeforeEach
    void setUp() {
        ruleset = new Ruleset1();
        RoundStatistic.reset();
        RoundStatistic.getRoundStatistic().startNewRound(1);
        decisionMaker = new MockBlueDecisionMaker();
    }


    @Test
    void testBlueEffect_ChooseFirstChip() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
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

        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        decisionMaker.setChooseFirstChip(true);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                blueChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        assertEquals(3, value);
        // Blue Chip sollte im Kessel sein
        assertTrue(bagManager.getDrawnChips().stream()
                .anyMatch(c -> ChipColor.BLUE.equals(c.getColor())));
        //
        assertEquals(2, bagManager.getDrawnChips().size()); // Blue + 1 chosen
    }

    @Test
    void testBlueEffect_ChooseSecondChip() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.GREEN, 1));
        chipsInBag.add(new Chip(ChipColor.YELLOW, 1));
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        decisionMaker.setChooseFirstChip(false); // Wähle nicht den ersten

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                blueChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        assertEquals(3, value);
        assertTrue(bagManager.getDrawnChips().stream()
                .anyMatch(c -> ChipColor.BLUE.equals(c.getColor())));
    }

    @Test
    void testBlueEffect_Value1_DrawsOneChip() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
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

        Chip blueChip = new Chip(ChipColor.BLUE, 1);
        decisionMaker.setChooseFirstChip(true);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                blueChip, bagManager, decisionMaker, roundClaudron);

        // Assert
        assertEquals(2, value);
        // Blue + 1 gezogener Chip = 2
        assertEquals(2, bagManager.getDrawnChips().size());
    }

    @Test
    void testBlueEffect_Value4_DrawsFourChips() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chipsInBag.add(new Chip(ChipColor.RED, 1));
        }
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                decisionMaker
        );

        Chip blueChip = new Chip(ChipColor.BLUE, 4);
        decisionMaker.setChooseFirstChip(true);

        // Act
        int value = ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                blueChip, bagManager, decisionMaker, roundClaudron);
        // Assert
        assertEquals(5, value);
        assertEquals(2, bagManager.getDrawnChips().size());
    }

    @Test
    void testBlueEffect_NotEnoughChipsInBag() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
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

        Chip blueChip = new Chip(ChipColor.BLUE, 5); // Versucht 5 zu ziehen, aber nur 2 vorhanden
        decisionMaker.setChooseFirstChip(true);

        // Act & Assert - sollte nicht crashen
        assertDoesNotThrow(() -> {
            ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                    blueChip, bagManager, decisionMaker, roundClaudron);
        });
    }

    @Test
    void testBlueEffect_ColorTriggered() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
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

        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        decisionMaker.setChooseFirstChip(true);

        // Act
        ruleset.determineValueExecuteInsantEffectAndPutInClaudron(
                blueChip, bagManager, decisionMaker, roundClaudron);

        // Assert - Blue sollte als ausgelöst tracked werden
        assertEquals(1, RoundStatistic.getRoundStatistic()
                .getColorTriggeredTimes().get(ChipColor.BLUE).get());
    }

    @Test
    void testBlueEffect_NestedBlueChip_RecursiveEffect() {
        // Arrange
        // Szenario: Blue 2 wird gelegt, zieht 2 Chips
        // Spieler wählt einen blauen Chip aus (wenn vorhanden), nicht-blaue gehen zurück
        // Der blaue Chip wird gelegt und zieht 1 Chip
        // Spieler wählt einen grünen Chip aus (wenn vorhanden)
        // Erwartung: 
        // - Mindestens 2 Chips in drawn (Blue 2 + mindestens 1 weiterer)
        // - Alle nicht gewählten Chips sollten in undrawn sein
        // - Keine Chips sollten verloren gehen

        List<Chip> chipsInBag = new ArrayList<>();
        Chip red1 = new Chip(ChipColor.RED, 1);
        Chip blue1 = new Chip(ChipColor.BLUE, 1);
        Chip green1 = new Chip(ChipColor.GREEN, 1);

        chipsInBag.add(red1);
        chipsInBag.add(blue1);
        chipsInBag.add(green1);

        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        roundClaudron = new RoundClaudron(
                new ClaudronPlayersData(0, true),
                ruleset,
                bagManager,
                new NestedBlueDecisionMaker()
        );

        Chip blueChip2 = new Chip(ChipColor.BLUE, 2);

        // Act
        roundClaudron.putChipInClaudron(blueChip2);

        // Assert
        List<Chip> drawnChips = bagManager.getDrawnChips();
        List<Chip> undrawnChips = bagManager.getUndrawnChips();

        // Wichtigste Assertions:
        // 1. Blue 2 sollte in drawn sein
        assertTrue(drawnChips.stream()
                        .anyMatch(c -> ChipColor.BLUE.equals(c.getColor()) && c.getValue() == 2),
                "Blue 2 sollte in drawn sein");

        // 2. Gesamtzahl der Chips sollte 4 sein (Blue 2 + die 3 ursprünglichen)
        int totalChips = drawnChips.size() + undrawnChips.size();
        assertEquals(4, totalChips, "Sollte insgesamt 4 Chips haben (Blue 2 + 3 ursprüngliche)");

        // 3. Mindestens 1 Chip sollte in drawn sein (neben Blue 2)
        assertTrue(drawnChips.size() >= 2, "Sollte mindestens 2 Chips in drawn haben (Blue 2 + mindestens 1 weiterer)");

        // 4. Die Chips sollten korrekt verteilt sein:
        // - Wenn Blue 1 in drawn ist, dann sollte Red 1 in undrawn sein
        boolean blue1InDrawn = drawnChips.stream()
                .anyMatch(c -> ChipColor.BLUE.equals(c.getColor()) && c.getValue() == 1);

        if (blue1InDrawn) {
            // Blue 1 wurde gewählt, also sollte Red 1 zurück in undrawn sein
            assertTrue(undrawnChips.stream()
                            .anyMatch(c -> (ChipColor.RED.equals(c.getColor()) || ChipColor.GREEN.equals(c.getColor())) && c.getValue() == 1),
                    "Red 1 sollte in undrawn sein, wenn Blue 1 gewählt wurde");
        }
    }

    // ============ MOCK DECISION MAKER ============

    /**
     * Mock DecisionMaker speziell für Blue-Effekt Tests
     */
    static class MockBlueDecisionMaker extends Rulset1DecisionMaker {
        private boolean chooseFirstChip = true;

        void setChooseFirstChip(boolean chooseFirst) {
            this.chooseFirstChip = chooseFirst;
        }

        @Override
        public ChoosenChip chooseChipForBlueEffect(List<Chip> chips) {
            if (chips == null || chips.isEmpty()) {
                return null;
            }
            if (chooseFirstChip) {
                return new ChoosenChip(chips.get(0));
            } else {
                return new ChoosenChip(chips.get(chips.size() - 1));
            }
        }

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

    /**
     * Mock DecisionMaker für nested Blue-Effekt Test
     * Wählt Chips basierend auf ihrer Farbe:
     * - Wenn Blue vorhanden: wähle Blue
     * - Sonst: wähle Green
     * - Sonst: wähle den ersten
     */
    static class NestedBlueDecisionMaker extends Rulset1DecisionMaker {
        @Override
        public ChoosenChip chooseChipForBlueEffect(List<Chip> chips) {
            if (chips == null || chips.isEmpty()) {
                return null;
            }

            // Versuche zuerst einen blauen Chip zu finden
            for (Chip chip : chips) {
                if (ChipColor.BLUE.equals(chip.getColor())) {
                    return new ChoosenChip(chip);
                }
            }

            // Wenn kein blauer Chip, versuche einen grünen Chip zu finden
            for (Chip chip : chips) {
                if (ChipColor.GREEN.equals(chip.getColor())) {
                    return new ChoosenChip(chip);
                }
            }

            // Fallback: wähle den ersten Chip
            return new ChoosenChip(chips.get(0));
        }

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
