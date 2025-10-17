package com.example.quacksbag.max.strategy.buy;

import com.example.quacksbag.ruleset.ChipPrice;

import java.util.ArrayList;
import java.util.List;

public class ChipComboCalculator {

    public static List<ComboResult> calculateAllCombosForBudget(
            List<ChipPrice> chips,
            int budget
    ) {
        List<ComboResult> results = new ArrayList<>();

        // --- Einzelchips prüfen ---
        for (ChipPrice c : chips) {
            if (c.getPrice() <= budget) {
                int totalValue = c.getChip().getValue();

                results.add(new ComboResult(c, null, totalValue, c.getPrice()));
            }
        }

        // --- Doppelkombinationen prüfen ---
        for (int i = 0; i < chips.size(); i++) {
            for (int j = i; j < chips.size(); j++) { // j=i erlaubt Doppelkäufe
                ChipPrice c1 = chips.get(i);
                ChipPrice c2 = chips.get(j);

                int totalCost = c1.getPrice() + c2.getPrice();
                if (totalCost > budget) continue;

                int totalValue = c1.getChip().getValue() + c2.getChip().getValue();

                results.add(new ComboResult(c1, c2, totalValue, totalCost));
            }
        }

        // --- Sortierung ---
        results.sort((o1, o2) -> {
            int cmp = Double.compare(o2.getTotalValue(), o1.getTotalValue());
            if (cmp != 0) return cmp;

            // sonst nach Kosten (niedriger zuerst)
            return Integer.compare(o1.getTotalCost(), o2.getTotalCost());
        });

        return results;
    }
}
