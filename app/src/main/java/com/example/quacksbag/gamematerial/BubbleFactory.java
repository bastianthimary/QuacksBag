package com.example.quacksbag.gamematerial;

public class BubbleFactory {
    public static Bubble createBubble(int bubbleValue) {
        return new Bubble(bubbleValue);
    }

    public static Bubble createRubyBubble(int bubbleValue) {
        return new Bubble(bubbleValue, true);
    }

    public static Bubble createBubble(int bubbleValue, int victoryPoints) {
        return new Bubble(bubbleValue, false, victoryPoints);
    }

    public static Bubble createRubyBubble(int bubbleValue, int victoryPoints) {
        return new Bubble(bubbleValue, true, victoryPoints);
    }
}
