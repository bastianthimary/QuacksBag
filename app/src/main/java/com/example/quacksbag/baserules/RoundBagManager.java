package com.example.quacksbag.baserules;

import static com.example.quacksbag.baserules.BagUtil.defaultStartingChips;

import com.example.quacksbag.gamematerial.Chip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class RoundBagManager {

    private final ArrayList<Chip> drawnChips = new ArrayList<>();

    private final ArrayList<Chip> undrawnChipsInBag = new ArrayList<>();


    public RoundBagManager(List<Chip> purchasedChips) {
        initUndrawnChipsInBag(purchasedChips);
    }

    private void initUndrawnChipsInBag(List<Chip> purchasedChips) {
        // Always add default starting chips for each round
        undrawnChipsInBag.addAll(defaultStartingChips());
        // add purchased chips (copies)
        for (Chip p : purchasedChips) {
            undrawnChipsInBag.add(p.copy());
        }
        Collections.shuffle(undrawnChipsInBag);
    }


    public boolean throwChipBackInBag(Chip chip) {
        boolean removed = drawnChips.remove(chip);
        if (removed) {
            undrawnChipsInBag.add(chip);
            Collections.shuffle(undrawnChipsInBag);
            return true;
        }
        return false;
    }


    public boolean isBagEmpty() {
        return undrawnChipsInBag.isEmpty();
    }

    public ArrayList<Chip> getDrawnChips() {
        return drawnChips;
    }

    public ArrayList<Chip> getUndrawnChips() {
        return undrawnChipsInBag;
    }

    public int currentNumberOfUndrawnChips() {
        return undrawnChipsInBag.size();
    }

    public int currentNumberOfDrawnChips() {
        return drawnChips.size();
    }

    public Chip drawChipFromBag(int chipId) {
        return undrawnChipsInBag.remove(chipId);
    }

    public void putChipInClaudron(Chip chip) {
        drawnChips.add(chip);
    }
}
