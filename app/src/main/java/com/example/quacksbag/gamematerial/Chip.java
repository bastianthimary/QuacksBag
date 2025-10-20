package com.example.quacksbag.gamematerial;


import lombok.Getter;
import lombok.NonNull;

@Getter
public class Chip {
    private final ChipColor color; // e.g. "White", "Blue"
    private final int value; // numeric value (1,2,3,4...)

    public Chip(ChipColor color, int value) {
        this.color = color;
        this.value = value;
    }

    // create a copy with a new unique id
    public Chip copy() {
        return new Chip(this.color, this.value);
    }

    public ChipColor getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return color.getColorname() + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chip chip = (Chip) o;
        return value == chip.value && color == chip.color;
    }

    @Override
    public int hashCode() {
        return 31 * color.hashCode() + value;
    }
}