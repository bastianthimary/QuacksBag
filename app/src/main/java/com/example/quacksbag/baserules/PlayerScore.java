package com.example.quacksbag.baserules;

import static com.example.quacksbag.statistic.GameStatistic.getGameStatistic;

import com.example.quacksbag.gamematerial.ClaudronPlayersData;

public class PlayerScore {
    private final String name;

    private BagManager bagManager;
    private final ClaudronPlayersData claudronPlayersData;
    private int rubyCount;
    private int victoryPoints;

    public PlayerScore(String name) {
        this.name = name;
        claudronPlayersData = new ClaudronPlayersData(0, true);
        rubyCount = 1;
        victoryPoints = 0;
        bagManager = new BagManager();
    }

    public BagManager getBagManager() {
        return bagManager;
    }

    public void setBagManager(BagManager bagManager) {
        this.bagManager = bagManager;
    }

    public int getDropBonus() {
        return claudronPlayersData.getDropBonus();
    }

    public int getRubyCount() {
        return rubyCount;
    }

    public void setRubyCount(int rubyCount) {
        this.rubyCount = rubyCount;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }


    public void addRuby() {
        getGameStatistic().addRuby();
        rubyCount++;
    }

    public void payRuby() {
        if (rubyCount > 1) {
            rubyCount = rubyCount - 2;
        }
    }

    public void addVictoryPoints(int victoryPoints) {
        this.victoryPoints += victoryPoints;
    }

    public ClaudronPlayersData getClaudronPlayersData() {
        return claudronPlayersData;
    }
    public void addClaudronDropBonus() {
        claudronPlayersData.addDropBonus();
    }

    public void useFlask() {
        claudronPlayersData.useFlask();
    }
    public boolean fillFlask() {
        return claudronPlayersData.fillFlask();
    }
}
