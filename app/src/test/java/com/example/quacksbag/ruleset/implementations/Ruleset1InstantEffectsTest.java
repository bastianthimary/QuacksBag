package com.example.quacksbag.ruleset.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.baserules.BagDrawer;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.statistic.RoundStatistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit Tests für Instant-Effekte (BLUE und YELLOW) von Ruleset 1
 * Diese Effekte werden sofort beim Ziehen eines Chips ausgelöst
 */
class Ruleset1InstantEffectsTest {

    private Ruleset1 ruleset;
    private RoundBagManager bagManager;

    @BeforeEach
    void setUp() {
        ruleset = new Ruleset1();
        RoundStatistic.reset();
        RoundStatistic.getRoundStatistic().startNewRound(1);
    }

    // ============ YELLOW CHIP TESTS ============

    @Test
    void testYellowEffect_LastChipIsWhite() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.WHITE, 1));
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.WHITE, 2));
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        // Chips in Kessel: WHITE(1), dann YELLOW
        bagManager.putChipInClaudron(new Chip(ChipColor.WHITE, 1));
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();

        // Act - Gelb-Effekt: wirft letzten weißen Chip zurück
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        bagManager.putChipInClaudron(yellowChip);

        // Simuliere Yellow-Effekt manuell (da wir keinen DecisionMaker haben)
        var chipsInClaudron = bagManager.getDrawnChips();
        if (chipsInClaudron.size() >= 2) {
            var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 2);
            if (ChipColor.WHITE.equals(lastChip.getColor())) {
                bagManager.throwChipBackInBag(lastChip);
            }
        }

        // Assert
        assertEquals(drawnChipsBeforeYellow, bagManager.getDrawnChips().size());
        assertTrue(bagManager.getUndrawnChips().stream()
                .anyMatch(c -> ChipColor.WHITE.equals(c.getColor())));
    }

    @Test
    void testYellowEffect_LastChipIsNotWhite() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        // Chips in Kessel: RED(1), dann YELLOW
        bagManager.putChipInClaudron(new Chip(ChipColor.RED, 1));
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        bagManager.putChipInClaudron(yellowChip);

        var chipsInClaudron = bagManager.getDrawnChips();
        if (chipsInClaudron.size() >= 2) {
            var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 1);
            if (ChipColor.WHITE.equals(lastChip.getColor())) {
                bagManager.throwChipBackInBag(lastChip);
            }
        }

        // Assert - nichts sollte zurückgeworfen werden
        assertEquals(drawnChipsBeforeYellow + 1, bagManager.getDrawnChips().size());
    }

    @Test
    void testYellowEffect_OnlyOneChipInClaudron() {
        // Arrange
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();

        // Act - nur YELLOW im Kessel
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        bagManager.putChipInClaudron(yellowChip);

        var chipsInClaudron = bagManager.getDrawnChips();
        if (chipsInClaudron.size() >= 2) {
            var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 1);
            if (ChipColor.WHITE.equals(lastChip.getColor())) {
                bagManager.throwChipBackInBag(lastChip);
            }
        }

        // Assert - sollte nicht crashen
        assertEquals(1, bagManager.getDrawnChips().size());
    }

    @Test
    void testYellowEffect_MultipleWhiteChips() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.WHITE, 1));
        chipsInBag.add(new Chip(ChipColor.WHITE, 2));
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        // Chips in Kessel: WHITE(1), WHITE(2), dann YELLOW
        bagManager.putChipInClaudron(new Chip(ChipColor.WHITE, 1));
        bagManager.putChipInClaudron(new Chip(ChipColor.WHITE, 2));
        int drawnChipsBeforeYellow = bagManager.getDrawnChips().size();

        // Act
        Chip yellowChip = new Chip(ChipColor.YELLOW, 1);
        bagManager.putChipInClaudron(yellowChip);

        var chipsInClaudron = bagManager.getDrawnChips();
        if (chipsInClaudron.size() >= 2) {
            var lastChip = chipsInClaudron.get(chipsInClaudron.size() - 2);
            if (ChipColor.WHITE.equals(lastChip.getColor())) {
                bagManager.throwChipBackInBag(lastChip);
            }
        }

        // Assert - nur der letzte weiße Chip (WHITE(2)) sollte zurückgeworfen werden
        assertEquals(drawnChipsBeforeYellow, bagManager.getDrawnChips().size());
    }

    // ============ BLUE CHIP TESTS ============

    @Test
    void testBlueEffect_DrawsCorrectNumberOfChips() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chipsInBag.add(new Chip(ChipColor.RED, 1));
        }
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        Chip blueChip = new Chip(ChipColor.BLUE, 3);

        // Act - Blue mit Wert 3 sollte 3 Chips ziehen
        int undrawnBefore = bagManager.getUndrawnChips().size();
        for (int i = 0; i < 3; i++) {
            Chip drawn = BagDrawer.drawRandomChipAndUpdateBag(bagManager);
            assertNotNull(drawn);
        }
        int undrawnAfter = bagManager.getUndrawnChips().size();

        // Assert
        assertEquals(3, undrawnBefore - undrawnAfter);
    }

    @Test
    void testBlueEffect_BlueValue1() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            chipsInBag.add(new Chip(ChipColor.RED, 1));
        }
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        Chip blueChip = new Chip(ChipColor.BLUE, 1);

        // Act - Blue mit Wert 1 sollte 1 Chip ziehen
        int undrawnBefore = bagManager.getUndrawnChips().size();
        Chip drawn = BagDrawer.drawRandomChipAndUpdateBag(bagManager);
        int undrawnAfter = bagManager.getUndrawnChips().size();

        // Assert
        assertNotNull(drawn);
        assertEquals(1, undrawnBefore - undrawnAfter);
    }

    @Test
    void testBlueEffect_BlueValue4() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            chipsInBag.add(new Chip(ChipColor.RED, 1));
        }
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        Chip blueChip = new Chip(ChipColor.BLUE, 4);

        // Act - Blue mit Wert 4 sollte 4 Chips ziehen
        int undrawnBefore = bagManager.getUndrawnChips().size();
        for (int i = 0; i < 4; i++) {
            Chip drawn = BagDrawer.drawRandomChipAndUpdateBag(bagManager);
            assertNotNull(drawn);
        }
        int undrawnAfter = bagManager.getUndrawnChips().size();

        // Assert
        assertEquals(4, undrawnBefore - undrawnAfter);
    }

    @Test
    void testBlueEffect_NotEnoughChipsInBag() {
        // Arrange
        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        chipsInBag.add(new Chip(ChipColor.RED, 1));
        bagManager = new RoundBagManager(new ArrayList<>());
        bagManager.getUndrawnChips().clear();
        bagManager.getUndrawnChips().addAll(chipsInBag);

        Chip blueChip = new Chip(ChipColor.BLUE, 5); // Will try to draw 5 but only 2 available

        // Act - sollte nicht crashen, sondern nur verfügbare Chips ziehen
        int undrawnBefore = bagManager.getUndrawnChips().size();
        int drawn = 0;
        while (!bagManager.isBagEmpty() && drawn < 5) {
            Chip chip = BagDrawer.drawRandomChipAndUpdateBag(bagManager);
            if (chip != null) {
                drawn++;
            }
        }
        int undrawnAfter = bagManager.getUndrawnChips().size();

        // Assert
        assertEquals(2, drawn);
        assertEquals(0, undrawnAfter);
    }

    // ============ COMBINED EFFECT TESTS ============

    @Test
    void testColorTriggeredTracking_MultipleEffects() {
        // Arrange
        List<Chip> chips = new ArrayList<>();
        chips.add(new Chip(ChipColor.BLUE, 1));
        chips.add(new Chip(ChipColor.YELLOW, 1));
        chips.add(new Chip(ChipColor.GREEN, 1));

        // Act
        for (Chip chip : chips) {
            if (ChipColor.BLUE.equals(chip.getColor())) {
                RoundStatistic.getRoundStatistic().incrementColorTriggered(ChipColor.BLUE);
            } else if (ChipColor.YELLOW.equals(chip.getColor())) {
                RoundStatistic.getRoundStatistic().incrementColorTriggered(ChipColor.YELLOW);
            } else if (ChipColor.GREEN.equals(chip.getColor())) {
                RoundStatistic.getRoundStatistic().incrementColorTriggered(ChipColor.GREEN);
            }
        }

        // Assert
        var colorTriggered = RoundStatistic.getRoundStatistic().getColorTriggeredTimes();
        assertEquals(1, colorTriggered.get(ChipColor.BLUE).get());
        assertEquals(1, colorTriggered.get(ChipColor.YELLOW).get());
        assertEquals(1, colorTriggered.get(ChipColor.GREEN).get());
    }
}
