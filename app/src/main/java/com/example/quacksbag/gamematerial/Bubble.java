package com.example.quacksbag.gamematerial;

public class Bubble {
    private final int bubbleValue;
    private final boolean ruby;
    private final int victoryPoints;

    public Bubble(int bubbleValue) {
        this.bubbleValue = bubbleValue;
        this.ruby = false;
        this.victoryPoints = 0;
    }

    public Bubble(int bubbleValue, boolean ruby) {
        this.bubbleValue = bubbleValue;
        this.ruby = ruby;
        this.victoryPoints = 0;
    }

    public Bubble(int bubbleValue, boolean ruby, int victoryPoints) {
        this.bubbleValue = bubbleValue;
        this.ruby = ruby;
        this.victoryPoints = victoryPoints;
    }

    public int getBubbleValue() {
        return bubbleValue;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean isRuby() {
        return ruby;
    }
}
