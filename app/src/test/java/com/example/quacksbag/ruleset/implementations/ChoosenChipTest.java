package com.example.quacksbag.ruleset.implementations;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChoosenChipTest {

    @Test
    void testChoosenChipCreation() {
        Chip chip = new Chip(ChipColor.BLUE, 2);
        ChoosenChip choosenChip = new ChoosenChip(chip);
        
        assertNotNull(choosenChip);
    }

    @Test
    void testGetChip() {
        Chip chip = new Chip(ChipColor.RED, 3);
        ChoosenChip choosenChip = new ChoosenChip(chip);
        
        Chip retrievedChip = choosenChip.getChip();
        
        assertNotNull(retrievedChip);
        assertEquals(chip, retrievedChip);
        assertEquals(ChipColor.RED, retrievedChip.getColor());
        assertEquals(3, retrievedChip.getValue());
    }

    @Test
    void testChoosenChipWithDifferentChips() {
        Chip whiteChip = new Chip(ChipColor.WHITE, 1);
        Chip blueChip = new Chip(ChipColor.BLUE, 2);
        Chip redChip = new Chip(ChipColor.RED, 3);
        
        ChoosenChip chosen1 = new ChoosenChip(whiteChip);
        ChoosenChip chosen2 = new ChoosenChip(blueChip);
        ChoosenChip chosen3 = new ChoosenChip(redChip);
        
        assertEquals(whiteChip, chosen1.getChip());
        assertEquals(blueChip, chosen2.getChip());
        assertEquals(redChip, chosen3.getChip());
    }

    @Test
    void testChoosenChipReturnsSameInstance() {
        Chip chip = new Chip(ChipColor.GREEN, 2);
        ChoosenChip choosenChip = new ChoosenChip(chip);
        
        Chip retrieved1 = choosenChip.getChip();
        Chip retrieved2 = choosenChip.getChip();
        
        assertSame(retrieved1, retrieved2);
    }
}
