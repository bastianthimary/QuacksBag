package com.example.quacksbag.gamematerial;


import static com.example.quacksbag.statistic.GameStatistic.getGameStatistic;
import static com.example.quacksbag.statistic.RoundStatistic.getRoundStatistic;

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
        getGameStatistic().incrementDropBonus();
    }

    public boolean isFlaskFilled() {
        return flaskFilled;
    }

    public boolean useFlask() {
        if (isFlaskFilled()) {
            getRoundStatistic().useFlask();
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
