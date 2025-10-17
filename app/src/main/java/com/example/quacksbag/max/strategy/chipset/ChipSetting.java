package com.example.quacksbag.max.strategy.chipset;

import com.example.quacksbag.gamematerial.ChipColor;

public class ChipSetting implements Comparable<ChipSetting> {

    private ChipColor chipColor;
    private ChipSettingWeights chipSettingWeight;
    private int maxAmount = -1;
    private boolean priorizeFirst = false;

    ChipSetting(ChipColor chipColor, ChipSettingWeights chipSettingWeight) {
        this.chipSettingWeight = chipSettingWeight;

    }

    public ChipSetting(ChipColor chipColor, ChipSettingWeights chipSettingWeight, int maxAmount, boolean priorizeFirst) {
        this.chipColor = chipColor;
        this.chipSettingWeight = chipSettingWeight;
        this.maxAmount = maxAmount;
        this.priorizeFirst = priorizeFirst;
    }

    public ChipColor getChipColor() {
        return chipColor;
    }

    public ChipSettingWeights getChipSettingWeight() {
        return chipSettingWeight;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean isPriorizeFirst() {
        return priorizeFirst;
    }

    @Override
    public int compareTo(ChipSetting other) {
        if (this.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT && this.priorizeFirst) {
            if (other.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT && other.priorizeFirst) {
                return 0; // Both are FIX_MAX_COUNT and priorizeFirst
            }
            return -1; // This one comes first
        }
        if (other.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT && other.priorizeFirst) {
            return 1; // Other one comes first
        }

        if (this.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT) {
            if (other.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT) {
                return 0; // Both are FIX_MAX_COUNT (and not priorizeFirst)
            }
            return -1; // This one comes first
        }
        if (other.chipSettingWeight == ChipSettingWeights.FIX_MAX_COUNT) {
            return 1; // Other one comes first
        }

        if (this.chipSettingWeight == ChipSettingWeights.REST_TILL_END) {
            if (other.chipSettingWeight == ChipSettingWeights.REST_TILL_END) {
                return 0;
            }
            return -1; // This one comes first
        }

        if (other.chipSettingWeight == ChipSettingWeights.REST_TILL_END) {
            return 1; // Other one comes first
        }

        // AFTERREACH_END is the last one by default if none of the above matched for this.
        // If other is also AFTERREACH_END, they are equal in this context.
        if (this.chipSettingWeight == ChipSettingWeights.AFTERREACH_END && other.chipSettingWeight == ChipSettingWeights.AFTERREACH_END) {
            return 0;
        }

        // If 'this' is AFTERREACH_END, it should come after others not yet handled.
        // However, all other specific cases for 'other' have been handled above, implying 'other' must be AFTERREACH_END if 'this' is not.
        // This logic simplifies as the last remaining unprioritized items are compared.
        // Given the logic above, if we reach here, 'this' is AFTERREACH_END or other has a higher enum ordinal.
        // Standard enum comparison can be used as a fallback if the specific rules don't differentiate.
        return this.chipSettingWeight.compareTo(other.chipSettingWeight);
    }
}
