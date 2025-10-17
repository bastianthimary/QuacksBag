package com.example.quacksbag.ruleset;

import com.example.quacksbag.gamematerial.Chip;

public abstract class PriceRuleset {
    public ChipPrice determinePrice(Chip chip){
        int value;
        switch (chip.getColor()) {
            case ORANGE:
                value = 1;
                break;
            case BLUE:
                value = determineBlueByValue(chip.getValue());
                break;
            case RED:
                value = determineRedByValue(chip.getValue());
                break;
            case YELLOW:
                value = determineYellowByValue(chip.getValue());
                break;
            case BLACK:
                value = 10;
                break;
            case GREEN:
                value = determineGreenByValue(chip.getValue());
                ;
                break;
            case PURPLE:
                value = 9;
                break;
            default:
                value = 0;
                break;
        }
        return new ChipPrice(chip,value);
    }

    protected abstract int determineGreenByValue(int chipValue);

    protected abstract int determineRedByValue(int chipValue);

    protected abstract int determineBlueByValue(int chipValue);

    protected abstract int determineYellowByValue(int chipValue);
}
