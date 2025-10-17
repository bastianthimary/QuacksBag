package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;

import java.util.Random;

public class BagDrawer {
    private static final Random random = new Random();

    public static Chip drawRandomChipAndUpdateBag(RoundBagManager bagManager) {
        if (bagManager.isBagEmpty()) return null;
        int idx = random.nextInt(bagManager.currentNumberOfUndrawnChips());
        return bagManager.drawChipFromBag(idx);
    }
}
