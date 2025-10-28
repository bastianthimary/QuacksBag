package com.example.quacksbag.gamematerial;

public enum ChipColor {
    WHITE("White"),
    ORANGE("Orange"),
    GREEN("Green"),
    BLUE("Blue"),
    RED("Red"),
    YELLOW("Yellow"),
    PURPLE("Purple"),
    BLACK("Black");

    ChipColor(String colorname) {
        this.colorname = colorname;
    }

    public String getColorname() {
        return colorname;
    }

    private final String colorname;

}
