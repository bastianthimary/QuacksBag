package com.example.quacksbag.max.strategy.chipset;

import java.util.Set;

public class ChipStrategy {
    private final Set<ChipSetting> settings;

    public ChipStrategy(Set<ChipSetting> settings) {
        this.settings = settings;
    }

    public Set<ChipSetting> getSettings() {
        return settings;
    }
}
