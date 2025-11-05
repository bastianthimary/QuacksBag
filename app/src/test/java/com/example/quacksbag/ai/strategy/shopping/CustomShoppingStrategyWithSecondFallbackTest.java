package com.example.quacksbag.ai.strategy.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.ChipPriceDeterminer;
import com.example.quacksbag.ruleset.implementations.PriceRuleset1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class CustomShoppingStrategyWithSecondFallbackTest {

    private CustomShoppingStrategyWithSecondFallback strategy;
    private GameManager mockGameManager;


    @BeforeEach
    void setUp() {
        mockGameManager = mock(GameManager.class);
    }


    @Test
    void shouldCreateStrategyWithCustomAndWithoutFallback() {
        // Arrange
        WishedChip primaryWish = new WishedChip(ChipColor.YELLOW);

        // Act
        strategy = new CustomShoppingStrategyWithSecondFallback(
                primaryWish, null, null
        );

        // Assert
        assertFalse(strategy.hasFallbackWish());
    }

    @Test
    void shouldCreateStrategyWithCustomAndNullFallback() {
        // Arrange
        WishedChip primaryWish = new WishedChip(ChipColor.YELLOW);

        // Act
        strategy = new CustomShoppingStrategyWithSecondFallback(
                primaryWish, null, null
        );

        // Assert
        assertFalse(strategy.hasFallbackWish());
    }

    @Test
    void shouldCreateStrategyWithCustomAndFallback() {
        // Arrange
        WishedChip primaryWish = new WishedChip(ChipColor.YELLOW);
        WishedChip fallbackWish = new WishedChip(ChipColor.GREEN);

        // Act
        strategy = new CustomShoppingStrategyWithSecondFallback(
                primaryWish, fallbackWish, null
        );

        // Assert
        assertTrue(strategy.hasFallbackWish());
    }

    @Test
    void shouldCreateStrategyWithSecondFallback() {
        // Arrange
        WishedChip primaryWish = new WishedChip(ChipColor.YELLOW);
        WishedChip fallbackWish = new WishedChip(ChipColor.GREEN);
        WishedChip secondFallbackWish = new WishedChip(ChipColor.ORANGE);

        // Act
        strategy = new CustomShoppingStrategyWithSecondFallback(
                primaryWish, fallbackWish, secondFallbackWish
        );

        // Assert
        assertTrue(strategy.hasFallbackWish());
        assertTrue(strategy.hasSecondFallbackWish());
    }

    @Test
    void testDecideShoppingValue3() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 3, chipPrices);
        assertEquals(1, shopping.size());
        assertEquals(ChipColor.ORANGE, shopping.get(0).getColor());
        assertEquals(1, shopping.get(0).getValue());

    }

    @Test
    void testDecideShoppingValue4() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 4, chipPrices);
        assertEquals(1, shopping.size());
        assertEquals(ChipColor.GREEN, shopping.get(0).getColor());
        assertEquals(1, shopping.get(0).getValue());

    }

    @Test
    void testDecideShoppingValue8() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 8, chipPrices);
        assertEquals(2, shopping.size());
        assertEquals(ChipColor.GREEN, shopping.get(0).getColor());
        assertEquals(1, shopping.get(0).getValue());
        assertEquals(ChipColor.GREEN, shopping.get(1).getColor());
        assertEquals(1, shopping.get(1).getValue());
    }

    @Test
    void testDecideShoppingValue9() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 9, chipPrices);
        assertEquals(1, shopping.size());
        assertEquals(ChipColor.PURPLE, shopping.get(0).getColor());
        assertEquals(1, shopping.get(0).getValue());
    }

    @Test
    void testDecideShoppingValue13() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 13, chipPrices);
        assertEquals(2, shopping.size());
        assertTrue(shopping.stream().anyMatch(chip -> ChipColor.PURPLE.equals(chip.getColor()) && chip.getValue() == 1));
        assertTrue(shopping.stream().anyMatch(chip -> ChipColor.GREEN.equals(chip.getColor()) && chip.getValue() == 1));
    }

    @Test
    void testDecideShoppingValue17() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 17, chipPrices);
        assertEquals(2, shopping.size());
        assertTrue(shopping.stream().anyMatch(chip -> ChipColor.PURPLE.equals(chip.getColor()) && chip.getValue() == 1));
        assertTrue(shopping.stream().anyMatch(chip -> ChipColor.GREEN.equals(chip.getColor()) && chip.getValue() == 2));
    }

    @Test
    void testDecideShoppingValue18() {
        List<ChipPrice> chipPrices = setupForDecideShoppingtests();
        //Assert
        List<Chip> shopping = strategy.decideShopping(mockGameManager, 18, chipPrices);
        assertEquals(2, shopping.size());
        assertEquals(ChipColor.PURPLE, shopping.get(0).getColor());
        assertEquals(1, shopping.get(0).getValue());
        assertEquals(ChipColor.PURPLE, shopping.get(1).getColor());
        assertEquals(1, shopping.get(1).getValue());
    }

    private List<ChipPrice> setupForDecideShoppingtests() {
        // Arrange
        WishedChip primaryWish = new WishedChip(ChipColor.PURPLE);
        WishedChip fallbackWish = new WishedChip(ChipColor.GREEN);
        strategy = new CustomShoppingStrategyWithSecondFallback(
                primaryWish, fallbackWish, null
        );
        ChipPriceDeterminer chipPriceDeterminer = new ChipPriceDeterminer(new PriceRuleset1());
        List<ChipPrice> chipPrices = chipPriceDeterminer.determinePrices();
        PlayerScore playerScore = new PlayerScore("Player1");
        when(mockGameManager.getPlayerScore()).thenReturn(playerScore);
        return chipPrices;
    }
}
