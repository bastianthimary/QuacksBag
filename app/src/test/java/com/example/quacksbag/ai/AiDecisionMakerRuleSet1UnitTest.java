package com.example.quacksbag.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.quacksbag.ai.strategy.draw.DrawStrategy;
import com.example.quacksbag.ai.strategy.explosion.ExplosionStrategy;
import com.example.quacksbag.ai.strategy.flask.FlaskStrategy;
import com.example.quacksbag.ai.strategy.rubybuyables.RubyBuyableStrategy;
import com.example.quacksbag.ai.strategy.shopping.ShoppingStrategy;
import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.implementations.ChoosenChip;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class AiDecisionMakerRuleSet1UnitTest {

    private AiDecisionMakerRuleSet1 aiDecisionMaker;

    @Mock
    private DrawStrategy drawStrategy;

    @Mock
    private ExplosionStrategy explosionStrategy;

    @Mock
    private ShoppingStrategy shoppingStrategy;

    @Mock
    private FlaskStrategy flaskStrategy;

    @Mock
    private RubyBuyableStrategy rubyBuyableStrategy;

    @Mock
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aiDecisionMaker = new AiDecisionMakerRuleSet1(
                drawStrategy,
                explosionStrategy,
                shoppingStrategy,
                flaskStrategy,
                rubyBuyableStrategy
        );
        aiDecisionMaker.setGameManager(gameManager);
    }

    @Nested
    @DisplayName("chooseChipForBlueEffect Tests")
    class ChooseChipForBlueEffectTests {

        @Test
        @DisplayName("Should return null when no non-white chips are present")
        void testNoNonWhiteChips() {
            List<Chip> drawnChips = List.of(
                    new Chip(ChipColor.WHITE, 1),
                    new Chip(ChipColor.WHITE, 2),
                    new Chip(ChipColor.WHITE, 3)
            );
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNull(result);
        }

        @Test
        @DisplayName("Should return single non-white chip when only one is present")
        void testSingleNonWhiteChip() {
            Chip purpleChip = new Chip(ChipColor.PURPLE, 5);
            List<Chip> drawnChips = List.of(
                    new Chip(ChipColor.WHITE, 1),
                    purpleChip,
                    new Chip(ChipColor.WHITE, 2)
            );
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(purpleChip, result.getChip());
        }

        @Test
        @DisplayName("Should choose PURPLE over other colors (highest priority)")
        void testPurpleHasHighestPriority() {
            Chip purpleChip = new Chip(ChipColor.PURPLE, 3);
            Chip blackChip = new Chip(ChipColor.BLACK, 4);
            Chip blueChip = new Chip(ChipColor.BLUE, 5);
            List<Chip> drawnChips = List.of(purpleChip, blackChip, blueChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.PURPLE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose BLACK over BLUE and GREEN (priority 2)")
        void testBlackHasSecondPriority() {
            Chip blackChip = new Chip(ChipColor.BLACK, 3);
            Chip blueChip = new Chip(ChipColor.BLUE, 5);
            Chip greenChip = new Chip(ChipColor.GREEN, 4);
            List<Chip> drawnChips = List.of(blackChip, blueChip, greenChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.BLACK, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose BLUE over GREEN (priority 3)")
        void testBlueHasThirdPriority() {
            Chip blueChip = new Chip(ChipColor.BLUE, 1);
            Chip greenChip = new Chip(ChipColor.GREEN, 2);
            List<Chip> drawnChips = List.of(blueChip, greenChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.BLUE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose higher value when colors have same priority (no ORANGE in bag)")
        void testSameColorPriorityChooseHigherValue() {
            Chip blackChip1 = new Chip(ChipColor.BLACK, 3);
            Chip blackChip2 = new Chip(ChipColor.BLACK, 5);
            List<Chip> drawnChips = List.of(blackChip1, blackChip2);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(5, result.getChip().getValue());
        }

        @Test
        @DisplayName("Should choose ORANGE with priority 4 when ORANGE is in drawn chips")
        void testOrangeWithPriority4WhenOrangeInBag() {
            Chip orangeChip = new Chip(ChipColor.ORANGE, 3);
            Chip redChip = new Chip(ChipColor.RED, 4);
            Chip greenChip = new Chip(ChipColor.GREEN, 5);
            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(orangeChip, redChip, greenChip));

            // Mock the RoundBagManager to return drawn chips including ORANGE
            mockRoundBagManagerWithChips(drawnChips);

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.ORANGE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose RED with priority 5 when ORANGE is in drawn chips")
        void testRedWithPriority5WhenOrangeInBag() {
            Chip orangeChip = new Chip(ChipColor.ORANGE, 1);
            Chip redChip = new Chip(ChipColor.RED, 2);
            Chip greenChip = new Chip(ChipColor.GREEN, 4);

            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(orangeChip, redChip, greenChip));
            mockRoundBagManagerWithChips(drawnChips);

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.ORANGE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose ORANGE with priority 6 when ORANGE is NOT in drawn chips")
        void testOrangeWithPriority6WhenNoOrangeInBag() {
            Chip orangeChip = new Chip(ChipColor.ORANGE, 1);
            Chip greenChip = new Chip(ChipColor.GREEN, 2);

            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(orangeChip, greenChip));
            // Mock the RoundBagManager to return drawn chips WITHOUT ORANGE
            mockRoundBagManagerWithChips(new ArrayList(List.of(greenChip)));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.ORANGE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose RED with priority 9 when ORANGE is NOT in drawn chips")
        void testRedWithPriority9WhenNoOrangeInBag() {
            Chip redChip = new Chip(ChipColor.RED, 3);
            Chip greenChip = new Chip(ChipColor.GREEN, 5);
            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(redChip, greenChip));

            mockRoundBagManagerWithChips(new ArrayList<>(List.of(greenChip)));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.GREEN, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should choose GREEN (priority 8) over WHITE")
        void testGreenHasPriority8() {
            Chip greenChip = new Chip(ChipColor.GREEN, 3);
            Chip whiteChip = new Chip(ChipColor.WHITE, 5);
            List<Chip> drawnChips = List.of(greenChip, whiteChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.GREEN, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should handle empty list")
        void testEmptyList() {
            List<Chip> drawnChips = new ArrayList<>();
            mockRoundBagManagerWithChips(new ArrayList<>());

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNull(result);
        }

        @Test
        @DisplayName("Should handle multiple chips of same color and choose highest value")
        void testMultipleSameColorChooseHighest() {
            Chip purpleChip1 = new Chip(ChipColor.PURPLE, 2);
            Chip purpleChip2 = new Chip(ChipColor.PURPLE, 4);
            Chip purpleChip3 = new Chip(ChipColor.PURPLE, 3);
            List<Chip> drawnChips = List.of(purpleChip1, purpleChip2, purpleChip3);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(4, result.getChip().getValue());
        }

        @Test
        @DisplayName("Should choose PURPLE (priority 1) over ORANGE (priority 4) even with lower value")
        void testPurpleLowerValueBeatsOrangeHigherValue() {
            Chip purpleChip = new Chip(ChipColor.PURPLE, 1);
            Chip orangeChip = new Chip(ChipColor.ORANGE, 10);
            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(orangeChip, purpleChip));
            mockRoundBagManagerWithChips(drawnChips);

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.PURPLE, result.getChip().getColor());
        }

        @Test
        @DisplayName("Should handle complex scenario with all colors")
        void testComplexScenarioWithAllColors() {
            ArrayList<Chip> drawnChips = new ArrayList<>(List.of(
                    new Chip(ChipColor.WHITE, 10),
                    new Chip(ChipColor.GREEN, 9),
                    new Chip(ChipColor.RED, 8),
                    new Chip(ChipColor.ORANGE, 7),
                    new Chip(ChipColor.BLUE, 6),
                    new Chip(ChipColor.BLACK, 5),
                    new Chip(ChipColor.PURPLE, 4)
            ));

            mockRoundBagManagerWithChips(drawnChips);

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.PURPLE, result.getChip().getColor());
            assertEquals(4, result.getChip().getValue());
        }

        @Test
        @DisplayName("Should update choice when better chip is found")
        void testUpdateChoiceWhenBetterChipFound() {
            Chip blackChip1 = new Chip(ChipColor.BLACK, 2);
            Chip blackChip2 = new Chip(ChipColor.BLACK, 5);
            Chip blueChip = new Chip(ChipColor.BLUE, 10);
            List<Chip> drawnChips = List.of(blackChip1, blackChip2, blueChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.BLACK, result.getChip().getColor());
            assertEquals(5, result.getChip().getValue());
        }

        @Test
        @DisplayName("Should handle YELLOW color (priority 9)")
        void testYellowColorHasPriority9() {
            Chip yellowChip = new Chip(ChipColor.YELLOW, 5);
            Chip greenChip = new Chip(ChipColor.GREEN, 5);
            List<Chip> drawnChips = List.of(yellowChip, greenChip);
            mockRoundBagManagerWithChips(new ArrayList<>(drawnChips));

            ChoosenChip result = aiDecisionMaker.chooseChipForBlueEffect(drawnChips);

            assertNotNull(result);
            assertEquals(ChipColor.GREEN, result.getChip().getColor());
        }
    }

    @Nested
    @DisplayName("Color Priority Tests")
    class ColorPriorityTests {

        @Test
        @DisplayName("PURPLE should have priority 1")
        void testPurplePriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.PURPLE);
            assertEquals(1, priority);
        }

        @Test
        @DisplayName("BLACK should have priority 2")
        void testBlackPriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.BLACK);
            assertEquals(2, priority);
        }

        @Test
        @DisplayName("BLUE should have priority 3")
        void testBluePriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.BLUE);
            assertEquals(3, priority);
        }

        @Test
        @DisplayName("ORANGE should have priority 4 when ORANGE in bag")
        void testOrangePriority4WithOrangeInBag() {
            ArrayList<Chip> chipsInBag = new ArrayList<>(List.of(new Chip(ChipColor.ORANGE, 1)));
            mockRoundBagManagerWithChips(chipsInBag);
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.ORANGE);
            assertEquals(4, priority);
        }

        @Test
        @DisplayName("ORANGE should have priority 6 when NO ORANGE in bag")
        void testOrangePriority6WithoutOrangeInBag() {
            mockRoundBagManagerWithChips(new ArrayList<>(List.of(new Chip(ChipColor.RED, 1))));
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.ORANGE);
            assertEquals(6, priority);
        }

        @Test
        @DisplayName("RED should have priority 5 when ORANGE in bag")
        void testRedPriority5WithOrangeInBag() {
            ArrayList<Chip> chipsInBag = new ArrayList<>(List.of(new Chip(ChipColor.ORANGE, 1)));
            mockRoundBagManagerWithChips(chipsInBag);
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.RED);
            assertEquals(5, priority);
        }

        @Test
        @DisplayName("RED should have priority 7 when NO ORANGE in bag")
        void testRedPriority7WithoutOrangeInBag() {
            mockRoundBagManagerWithChips(new ArrayList<>(List.of(new Chip(ChipColor.RED, 1))));
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.RED);
            assertEquals(9, priority);
        }

        @Test
        @DisplayName("GREEN should have priority 8")
        void testGreenPriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.GREEN);
            assertEquals(8, priority);
        }

        @Test
        @DisplayName("WHITE should have priority 9 (default)")
        void testWhitePriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.WHITE);
            assertEquals(9, priority);
        }

        @Test
        @DisplayName("YELLOW should have priority 9 (default)")
        void testYellowPriority() {
            mockRoundBagManagerWithChips(new ArrayList<>());
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.YELLOW);
            assertEquals(9, priority);
        }

        @Test
        @DisplayName("Multiple Red chips in bag should still give priority 4")
        void testMultipleRedChipsInBag() {
            ArrayList<Chip> chipsInBag = new ArrayList<>(List.of(
                    new Chip(ChipColor.RED, 1),
                    new Chip(ChipColor.RED, 2),
                    new Chip(ChipColor.RED, 4)
            ));
            mockRoundBagManagerWithChips(chipsInBag);
            int priority = aiDecisionMaker.determineColorPriority(ChipColor.RED);
            assertEquals(9, priority);
        }
    }

    @Nested
    @DisplayName("Strategy Delegation Tests")
    class StrategyDelegationTests {

        @Test
        @DisplayName("makeChoiceOnExplosion should delegate to explosionStrategy")
        void testMakeChoiceOnExplosionDelegation() {
            aiDecisionMaker.makeChoiceOnExplosion();
            verify(explosionStrategy).decideExplosion(gameManager);
        }

        @Test
        @DisplayName("doShoppingChoice should delegate to shoppingStrategy")
        void testDoShoppingChoiceDelegation() {
            int bubbleValue = 10;

            aiDecisionMaker.doShoppingChoice(bubbleValue, null);
            verify(shoppingStrategy).decideShopping(gameManager, bubbleValue, null);
        }

        @Test
        @DisplayName("wantToUseFlask should delegate to flaskStrategy")
        void testWantToUseFlaskDelegation() {
            Chip chip = new Chip(ChipColor.BLUE, 5);
            aiDecisionMaker.wantToUseFlask(chip);
            verify(flaskStrategy).decideUseFlask(gameManager, chip);
        }

        @Test
        @DisplayName("doAnotherDraw should delegate to drawStrategy")
        void testDoAnotherDrawDelegation() {
            aiDecisionMaker.doAnotherDraw();
            verify(drawStrategy).decideDraw(gameManager);
        }

        @Test
        @DisplayName("buyDropOrFlask should delegate to rubyBuyableStrategy")
        void testBuyDropOrFlaskDelegation() {
            aiDecisionMaker.buyDropOrFlask();
            verify(rubyBuyableStrategy).decideBuyDropOrFlask(gameManager);
        }
    }

    @Nested
    @DisplayName("GameManager Setter Tests")
    class GameManagerSetterTests {

        @Test
        @DisplayName("setGameManager should store the GameManager instance")
        void testSetGameManager() {
            GameManager newGameManager = mock(GameManager.class);
            aiDecisionMaker.setGameManager(newGameManager);

            // Verify by calling a method that uses gameManager
            aiDecisionMaker.makeChoiceOnExplosion();
            verify(explosionStrategy).decideExplosion(newGameManager);
        }
    }

    // Helper methods
    private void mockRoundBagManagerWithChips(ArrayList<Chip> chips) {
        var roundBagManager = mock(com.example.quacksbag.baserules.RoundBagManager.class);
        when(roundBagManager.getDrawnChips()).thenReturn(chips);

        var roundClaudron = mock(com.example.quacksbag.baserules.RoundClaudron.class);
        when(roundClaudron.getRoundBagManager()).thenReturn(roundBagManager);

        when(gameManager.getRoundClaudron()).thenReturn(roundClaudron);
    }


}
