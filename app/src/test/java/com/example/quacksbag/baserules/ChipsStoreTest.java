package com.example.quacksbag.baserules;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import org.junit.jupiter.api.Test;

import java.util.List;

class ChipsStoreTest {
    private ChipsStore chipsStore;

    @Test
    void testBuyableChips3Round() {
        chipsStore = new ChipsStore();
        List<Chip> list = chipsStore.getBuyableChipsForRound(3);

        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.WHITE)));
        assertTrue(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.YELLOW)));
        assertTrue(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.PURPLE)));
    }
    @Test
    void testBuyableChips2Round() {
        chipsStore = new ChipsStore();
        List<Chip> list = chipsStore.getBuyableChipsForRound(2);

        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.WHITE)));
        assertTrue(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.YELLOW)));
        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.PURPLE)));
    }
    @Test
    void testBuyableChips1Round() {
        chipsStore = new ChipsStore();
        List<Chip> list = chipsStore.getBuyableChipsForRound(1);

        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.WHITE)));
        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.YELLOW)));
        assertFalse(list.stream().anyMatch(chip -> chip.getColor().equals(ChipColor.PURPLE)));
    }

}