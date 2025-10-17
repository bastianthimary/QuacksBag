package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class BagManager {


    // master list of purchased chips (kept across rounds in session)
    private final List<Chip> purchasedChips = new ArrayList<>();


    public BagManager() {

    }

    // Start a new round: bag = defaults + purchased copies
    public RoundBagManager startNewRound() {
        return new RoundBagManager(purchasedChips);
    }

    // Add (purchase) N copies of a given preset chip (adds to purchasedChips and to current bag)
    public void purchaseChipPreset(Chip preset) {
        Logger.result("Purchase Chip: "+preset.toString());
        Chip newChip = preset.copy();
        purchasedChips.add(newChip);

    }
    public List<Chip> getPurchasedChips(){
        return purchasedChips;

    }
}