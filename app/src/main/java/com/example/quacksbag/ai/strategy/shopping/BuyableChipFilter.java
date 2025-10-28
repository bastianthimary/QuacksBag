package com.example.quacksbag.ai.strategy.shopping;

import com.example.quacksbag.baserules.BagUtil;
import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.baserules.UndrawnChipsUtil;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.ruleset.ChipPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuyableChipFilter {

    private final List<WishedChip> wishedChips;

    public BuyableChipFilter(List<WishedChip> wishedChips) {
        this.wishedChips = wishedChips;
    }

    public List<ChipPrice> filterBuyableChipsByColorAndWitchStillNeeded(GameManager gameManager, List<ChipPrice> buyableChips) {
        var chipsInBag = new ArrayList<>(gameManager.getPlayerScore().getBagManager().getPurchasedChips());
        chipsInBag.addAll(BagUtil.defaultStartingChips());
        List<ChipColor> stillNeededColors = determineStillNeededColors(chipsInBag);
        return filterBuyableChipsByNeededColors(buyableChips, stillNeededColors);
    }


    protected List<ChipColor> determineStillNeededColors(List<Chip> chipsInBag) {
        List<ChipColor> stillNeededColors = new ArrayList<>();
        wishedChips.forEach(wantedChip -> addStillNeededColors(chipsInBag, wantedChip, stillNeededColors));
        return stillNeededColors;
    }

    protected void addStillNeededColors(List<Chip> chipsInBag, WishedChip wantedChip, List<ChipColor> stillNeededColors) {
        if (wantedChip.isLimited()) {
            if (isLimitNotYetReached(chipsInBag, wantedChip)) {
                stillNeededColors.add(wantedChip.getChipColor());
            }
        } else {
            stillNeededColors.add(wantedChip.getChipColor());
        }
    }

    protected boolean isLimitNotYetReached(List<Chip> chipsInBag, WishedChip wantedChip) {
        return wantedChip.getNumberOfChips() > countChipsInBag(chipsInBag, wantedChip);
    }

    protected int countChipsInBag(List<Chip> chipsInBag, WishedChip wantedChip) {
        return UndrawnChipsUtil.findChipsByColor(chipsInBag, wantedChip.getChipColor()).size();
    }

    public List<ChipPrice> filterBuyableChipsByNeededColors(List<ChipPrice> buyableChips, List<ChipColor> stillNeededColors) {
        return buyableChips.stream().filter(chipPrice -> stillNeededColors.contains(chipPrice.getChip().getColor())).collect(Collectors.toList());
    }

}