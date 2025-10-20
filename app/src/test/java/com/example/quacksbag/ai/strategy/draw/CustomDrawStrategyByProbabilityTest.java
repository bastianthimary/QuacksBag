package com.example.quacksbag.ai.strategy.draw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;


class CustomDrawStrategyByProbabilityTest {

    private CustomDrawStrategyByProbability strategy;

    @Mock
    private GameManager gameManager;

    @Mock
    private RoundClaudron roundClaudron;

    @Mock
    private RoundBagManager roundBagManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new CustomDrawStrategyByProbability(0.5);

        // Setup default mocks
        when(gameManager.getRoundClaudron()).thenReturn(roundClaudron);
        when(roundClaudron.getRoundBagManager()).thenReturn(roundBagManager);
    }

    @Test
    @DisplayName("Should return DRAW_NEXT when claudron cannot explode")
    void testDecideDrawWhenClaudronCannotExplode() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(2); // Less than 4

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    void testDecideDrawWhenProbabilityHigherThanLimit() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        // Create chips: 3 drawn chips, 2 undrawn white chips with value >= 2
        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));
        drawnChips.add(new Chip(ChipColor.YELLOW, 2));
        drawnChips.add(new Chip(ChipColor.BLACK, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));
        undrawnChips.add(new Chip(ChipColor.WHITE, 2));
        undrawnChips.add(new Chip(ChipColor.RED, 4));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 2/3 = 0.666... which is > 0.5

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.END_ROUND, result);
    }

    @Test
    @DisplayName("Should return DRAW_NEXT when explosion probability is lower than limit")
    void testDecideDrawWhenProbabilityLowerThanLimit() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        // Create chips: 10 drawn chips, 1 undrawn white chip with value >= 2
        ArrayList<Chip> drawnChips = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            drawnChips.add(new Chip(ChipColor.RED, 1));
        }

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));
        undrawnChips.add(new Chip(ChipColor.RED, 5));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 1/10 = 0.1 which is < 0.5

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    @DisplayName("Should return DRAW_NEXT when explosion probability equals limit")
    void testDecideDrawWhenProbabilityEqualsLimit() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        // Create chips: 2 drawn chips, 1 undrawn white chip with value >= 2
        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));
        drawnChips.add(new Chip(ChipColor.YELLOW, 2));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));
        undrawnChips.add(new Chip(ChipColor.RED, 5));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 1/2 = 0.5 which equals 0.5 (not higher)

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    void testExplosionLimitCalculation() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(3);

        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));
        drawnChips.add(new Chip(ChipColor.RED, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 2));
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));
        undrawnChips.add(new Chip(ChipColor.WHITE, 1));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 2/2 = 1.0 which is > 0.5

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    @DisplayName("Should filter only WHITE chips in probability calculation")
    void testOnlyWhiteChipsAreConsidered() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.RED, 4));
        undrawnChips.add(new Chip(ChipColor.YELLOW, 4));
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));
        undrawnChips.add(new Chip(ChipColor.BLACK, 1));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 1/1 = 1.0 which is > 0.5

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    @DisplayName("Should handle zero undrawn chips gracefully")
    void testZeroUndrawnChips() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Probability = 0/1 = 0.0 which is < 0.5

        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.DRAW_NEXT, result);
    }

    @Test
    void testCustomProbabilityLimit() {
        // Arrange
        CustomDrawStrategyByProbability customStrategy = new CustomDrawStrategyByProbability(0.3);

        when(gameManager.getRoundClaudron()).thenReturn(roundClaudron);
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(5);

        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));
        drawnChips.add(new Chip(ChipColor.RED, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 3));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);

        // Act
        DrawChoice result = customStrategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.END_ROUND, result);
    }

    @Test
    @DisplayName("Should handle high firecracker counter (limit becomes 0 or negative)")
    void testHighFirecrackerCounter() {
        // Arrange
        when(roundClaudron.getFirecrackerPeaCounter()).thenReturn(6); // limit = 7 - 7 = 0

        ArrayList<Chip> drawnChips = new ArrayList<>();
        drawnChips.add(new Chip(ChipColor.RED, 1));

        ArrayList<Chip> undrawnChips = new ArrayList<>();
        undrawnChips.add(new Chip(ChipColor.WHITE, 1));
        undrawnChips.add(new Chip(ChipColor.WHITE, 1));

        when(roundBagManager.getDrawnChips()).thenReturn(drawnChips);
        when(roundBagManager.getUndrawnChips()).thenReturn(undrawnChips);


        // Act
        DrawChoice result = strategy.decideDraw(gameManager);

        // Assert
        assertEquals(DrawChoice.END_ROUND, result);
    }
}
