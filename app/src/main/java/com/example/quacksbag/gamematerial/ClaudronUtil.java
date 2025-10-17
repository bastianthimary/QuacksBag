package com.example.quacksbag.gamematerial;

public class ClaudronUtil {
    private static final Claudron claudron = new Claudron();

    public static boolean isPositionARubyBubble(int position) {
        return claudron.getBubbleForPosition(position).isRuby();
    }

    public static int calcDistanceToNextRuby(int position) {
        int distance = 0;
        while (!isPositionARubyBubble(position + distance)) {
            distance++;
        }
        return distance;
    }
}
