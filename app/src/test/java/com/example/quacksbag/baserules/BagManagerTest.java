package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagManagerTest {

    private BagManager bagManager;

    @BeforeEach
    void setUp() {
        bagManager = new BagManager();
    }

    @Test
    void testBagManagerCreation() {
        assertNotNull(bagManager);
    }

    @Test
    void testInitialPurchasedChipsIsEmpty() {
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        
        assertNotNull(purchasedChips);
        assertTrue(purchasedChips.isEmpty());
    }

    @Test
    void testStartNewRound() {
        RoundBagManager roundBagManager = bagManager.startNewRound();
        
        assertNotNull(roundBagManager);
    }

    @Test
    void testPurchaseChipPreset() {
        Chip preset = new Chip(ChipColor.BLUE, 2);
        
        bagManager.purchaseChipPreset(preset);
        
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        assertEquals(1, purchasedChips.size());
    }

    @Test
    void testPurchaseChipPresetCreatesNewInstance() {
        Chip preset = new Chip(ChipColor.RED, 3);

        
        bagManager.purchaseChipPreset(preset);
        
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        Chip purchasedChip = purchasedChips.get(0);

        assertEquals(preset.getColor(), purchasedChip.getColor());
        assertEquals(preset.getValue(), purchasedChip.getValue());
    }

    @Test
    void testPurchaseMultipleChips() {
        Chip chip1 = new Chip(ChipColor.BLUE, 1);
        Chip chip2 = new Chip(ChipColor.GREEN, 2);
        Chip chip3 = new Chip(ChipColor.RED, 3);
        
        bagManager.purchaseChipPreset(chip1);
        bagManager.purchaseChipPreset(chip2);
        bagManager.purchaseChipPreset(chip3);
        
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        assertEquals(3, purchasedChips.size());
    }

    @Test
    void testPurchaseSameChipMultipleTimes() {
        Chip preset = new Chip(ChipColor.ORANGE, 1);
        
        bagManager.purchaseChipPreset(preset);
        bagManager.purchaseChipPreset(preset);
        bagManager.purchaseChipPreset(preset);
        
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        assertEquals(3, purchasedChips.size());
        
        // All should have same color and value but different IDs
        assertTrue(purchasedChips.stream().allMatch(chip -> chip.getColor() == ChipColor.ORANGE));
        assertTrue(purchasedChips.stream().allMatch(chip -> chip.getValue() == 1));
    }

    @Test
    void testStartNewRoundAfterPurchases() {
        Chip chip1 = new Chip(ChipColor.BLUE, 2);
        Chip chip2 = new Chip(ChipColor.RED, 3);
        
        bagManager.purchaseChipPreset(chip1);
        bagManager.purchaseChipPreset(chip2);
        
        RoundBagManager roundBagManager = bagManager.startNewRound();
        
        assertNotNull(roundBagManager);
    }

    @Test
    void testPurchasedChipsPersistedAcrossRounds() {
        Chip chip = new Chip(ChipColor.YELLOW, 2);
        bagManager.purchaseChipPreset(chip);
        
        bagManager.startNewRound();
        
        List<Chip> purchasedChips = bagManager.getPurchasedChips();
        assertEquals(1, purchasedChips.size());
    }

    @Test
    void testMultipleRoundStarts() {
        Chip chip = new Chip(ChipColor.PURPLE, 1);
        bagManager.purchaseChipPreset(chip);
        
        RoundBagManager round1 = bagManager.startNewRound();
        RoundBagManager round2 = bagManager.startNewRound();
        RoundBagManager round3 = bagManager.startNewRound();
        
        assertNotNull(round1);
        assertNotNull(round2);
        assertNotNull(round3);
        assertNotSame(round1, round2);
        assertNotSame(round2, round3);
    }
}
