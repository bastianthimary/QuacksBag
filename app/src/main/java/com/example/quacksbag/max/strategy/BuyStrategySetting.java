package com.example.quacksbag.max.strategy;

import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;
import com.example.quacksbag.max.strategy.chipset.StrategySteps;

public class BuyStrategySetting {
    private final StrategySteps strategySteps;
    private final ComboResultWeight comboResultWeight;
    private final ChipColor priorityColor;

    public BuyStrategySetting(StrategySteps strategySteps, ComboResultWeight comboResultWeight, ChipColor priorityColor) {
        this.strategySteps = strategySteps;
        this.comboResultWeight = comboResultWeight;
        this.priorityColor = priorityColor;
    }

    public StrategySteps getStrategySteps() {
        return strategySteps;
    }

    public ComboResultWeight getComboResultWeight() {
        return comboResultWeight;
    }

    public ChipColor getPriorityColor() {
        return priorityColor;
    }
}
