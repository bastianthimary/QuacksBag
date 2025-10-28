
package com.example.quacksbag.ai.strategy.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BuyableChipFilterTest {

    private BuyableChipFilter buyableChipFilter;
    private List<WishedChip> wantedChips;

    @BeforeEach
    void setUp() {
        wantedChips = new ArrayList<>();
    }

    @Test
    void determineStillNeededColors_NoLimit() {
        wantedChips.add(new WishedChip(ChipColor.BLUE));
        buyableChipFilter = new BuyableChipFilter(wantedChips);

        List<Chip> chipsInBag = new ArrayList<>();
        List<ChipColor> stillNeededColors = buyableChipFilter.determineStillNeededColors(chipsInBag);

        assertEquals(1, stillNeededColors.size());
        assertEquals(ChipColor.BLUE, stillNeededColors.get(0));
    }

    @Test
    void determineStillNeededColors_WithLimit_NotReached() {
        wantedChips.add(new WishedChip(2, ChipColor.BLUE));
        buyableChipFilter = new BuyableChipFilter(wantedChips);

        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));

        List<ChipColor> stillNeededColors = buyableChipFilter.determineStillNeededColors(chipsInBag);

        assertEquals(1, stillNeededColors.size());
        assertEquals(ChipColor.BLUE, stillNeededColors.get(0));
    }

    @Test
    void determineStillNeededColors_WithLimit_Reached() {
        wantedChips.add(new WishedChip(1, ChipColor.BLUE));
        buyableChipFilter = new BuyableChipFilter(wantedChips);

        List<Chip> chipsInBag = new ArrayList<>();
        chipsInBag.add(new Chip(ChipColor.BLUE, 1));

        List<ChipColor> stillNeededColors = buyableChipFilter.determineStillNeededColors(chipsInBag);

        assertTrue(stillNeededColors.isEmpty());
    }

    @Test
    void addStillNeededColors_NoLimit() {
        WishedChip wantedChip = new WishedChip(ChipColor.RED);
        List<Chip> chipsInBag = new ArrayList<>();
        List<ChipColor> stillNeededColors = new ArrayList<>();
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());

        buyableChipFilter.addStillNeededColors(chipsInBag, wantedChip, stillNeededColors);

        assertEquals(1, stillNeededColors.size());
        assertEquals(ChipColor.RED, stillNeededColors.get(0));
    }

    @Test
    void addStillNeededColors_LimitNotReached() {
        WishedChip wantedChip = new WishedChip(3, ChipColor.YELLOW);
        List<Chip> chipsInBag = List.of(new Chip(ChipColor.YELLOW, 1));
        List<ChipColor> stillNeededColors = new ArrayList<>();
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());


        buyableChipFilter.addStillNeededColors(chipsInBag, wantedChip, stillNeededColors);

        assertEquals(1, stillNeededColors.size());
        assertEquals(ChipColor.YELLOW, stillNeededColors.get(0));
    }

    @Test
    void addStillNeededColors_LimitReached() {
        WishedChip wantedChip = new WishedChip(1, ChipColor.GREEN);
        List<Chip> chipsInBag = List.of(new Chip(ChipColor.GREEN, 1));
        List<ChipColor> stillNeededColors = new ArrayList<>();
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());


        buyableChipFilter.addStillNeededColors(chipsInBag, wantedChip, stillNeededColors);

        assertTrue(stillNeededColors.isEmpty());
    }


    @Test
    void isLimitNotYetReached_True() {
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());
        WishedChip wantedChip = new WishedChip(2, ChipColor.BLUE);
        List<Chip> chipsInBag = List.of(new Chip(ChipColor.BLUE, 1));

        assertTrue(buyableChipFilter.isLimitNotYetReached(chipsInBag, wantedChip));
    }

    @Test
    void isLimitNotYetReached_False() {
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());
        WishedChip wantedChip = new WishedChip(1, ChipColor.BLUE);
        List<Chip> chipsInBag = List.of(new Chip(ChipColor.BLUE, 1));

        assertFalse(buyableChipFilter.isLimitNotYetReached(chipsInBag, wantedChip));
    }

    @Test
    void countChipsInBag() {
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());
        WishedChip wantedChip = new WishedChip(ChipColor.BLUE);
        List<Chip> chipsInBag = List.of(new Chip(ChipColor.BLUE, 1), new Chip(ChipColor.BLUE, 2));

        assertEquals(2, buyableChipFilter.countChipsInBag(chipsInBag, wantedChip));
    }

    @Test
    void filterBuyableChipsByColorAndWitchStillNeededByNeededColors() {
        buyableChipFilter = new BuyableChipFilter(new ArrayList<>());
        List<ChipPrice> buyableChips = List.of(
                new ChipPrice(new Chip(ChipColor.BLUE, 1), 5),
                new ChipPrice(new Chip(ChipColor.RED, 1), 6)
        );
        List<ChipColor> stillNeededColors = List.of(ChipColor.BLUE);

        List<ChipPrice> filteredChips = buyableChipFilter.filterBuyableChipsByNeededColors(buyableChips, stillNeededColors);

        assertEquals(1, filteredChips.size());
        assertEquals(ChipColor.BLUE, filteredChips.get(0).getChip().getColor());
    }
}
