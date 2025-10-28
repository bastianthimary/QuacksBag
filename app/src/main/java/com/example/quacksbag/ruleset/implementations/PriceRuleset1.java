package com.example.quacksbag.ruleset.implementations;

import com.example.quacksbag.ruleset.PriceRuleset;

public class PriceRuleset1 extends PriceRuleset {

    protected int determineGreenByValue(int chipsInClaudron) {
        return switch (chipsInClaudron) {
            case 1 -> 4;
            case 2 -> 8;
            case 4 -> 14;
            default -> 0;
        };
    }

    protected int determineRedByValue(int chipsInClaudron) {
        return switch (chipsInClaudron) {
            case 1 -> 6;
            case 2 -> 10;
            case 4 -> 16;
            default -> 0;
        };
    }

    protected int determineBlueByValue(int chipsInClaudron) {
        return switch (chipsInClaudron) {
            case 1 -> 5;
            case 2 -> 10;
            case 4 -> 19;
            default -> 0;
        };

    }

    protected int determineYellowByValue(int chipsInClaudron) {
        return switch (chipsInClaudron) {
            case 1 -> 8;
            case 2 -> 12;
            case 4 -> 18;
            default -> 0;
        };
    }

    @Override
    protected int determinePurpleByValue(int chipValue) {
        return 9;
    }

    @Override
    protected int determineBlackByValue(int chipValue) {
        return 10;
    }


}
