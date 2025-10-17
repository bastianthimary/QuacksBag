package com.example.quacksbag.android;

import android.graphics.Color;

import com.example.quacksbag.gamematerial.ChipColor;

public class ColorMapper {
    public static int mapChipColorToHex(ChipColor chipColor) {
        int hexvalue = 0;
        switch (chipColor) {
            case BLACK:
                hexvalue = Color.BLACK;
                break;
            case RED:
                hexvalue = Color.RED;
                break;
            case BLUE:
                hexvalue = Color.BLUE;
                break;
            case GREEN:
                hexvalue = 0xFF2E7D32;
                break;
            case WHITE:
                hexvalue = Color.WHITE;
                break;
            case ORANGE:
                hexvalue = 0xFFFF9800;
                break;
            case PURPLE:
                hexvalue = 0xFF8E24AA;
                break;
            case YELLOW:
                hexvalue = Color.YELLOW;
                break;
            default:
        }
        return hexvalue;
    }
}
