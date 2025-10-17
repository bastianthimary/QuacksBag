package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;

import java.util.Random;

public class BagDrawer {

    public static Chip drawRandomChipAndUpdateBag(RoundBagManager bagManager) {
        if (bagManager.isBagEmpty()) return null;
        int idx = new Random().nextInt(bagManager.currentNumberOfUndrawnChips());
        return bagManager.drawChipFromBag(idx);
    }
}
