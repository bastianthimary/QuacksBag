package com.example.quacksbag.gamematerial;




import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Chip {
    private final String id; // unique id for a chip instance
    private final ChipColor color; // e.g. "White", "Blue"
    private final int value; // numeric value (1,2,3,4...)

    public Chip(ChipColor color, int value) {
        this.id = UUID.randomUUID().toString();
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
}