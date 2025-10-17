package com.example.quacksbag.gamematerial;

import com.example.quacksbag.util.SimulationStatistics;

public class ClaudronPlayersData {
    int dropBonus;
    boolean flaskFilled;

    public ClaudronPlayersData(int dropBonus, boolean flaskFilled) {
        this.dropBonus = dropBonus;
        this.flaskFilled = flaskFilled;
    }

    public int getDropBonus() {
        return dropBonus;
    }

    public void addDropBonus() {
        this.dropBonus++;
        SimulationStatistics.getInstance().incrementDropBonus();
    }

    public boolean isFlaskFilled() {
        return flaskFilled;
    }

    public boolean useFlask() {
        if (isFlaskFilled()) {
            flaskFilled = false;
            return true;
        }
        return false;
    }

    public boolean fillFlask() {
        if (!isFlaskFilled()) {
        flaskFilled = true;
        return true;
        }
        return false;
    }
}
