package com.example.quacksbag;

public class PlayerClaudron {
    private int dropPosition; // Defaults to 0

    public void shiftDropOneForward(){
        dropPosition++;
    }

    // Package-private getter for testing purposes
    int getDropPositionForTesting() {
        return dropPosition;
    }
}
