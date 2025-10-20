package com.example.quacksbag.statistic;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GameStatistic {
    private static GameStatistic INSTANCE = new GameStatistic();
    private final AtomicInteger totalExplosions = new AtomicInteger(0);
    private final AtomicInteger totalDropBonus = new AtomicInteger(0);
    private final AtomicInteger totalFlaskUses = new AtomicInteger(0);
    private final AtomicInteger totalNumberOfDrawnChips = new AtomicInteger(0);
    private final AtomicInteger rubyCount = new AtomicInteger(0);
    private HashMap<Chip, AtomicInteger> chipCount = new HashMap<>();
    private List<RoundStatistic> roundStatistics = new ArrayList<>();
    private final HashMap<ChipColor, AtomicInteger> colorTriggeredTimes = new HashMap<>();


    public static GameStatistic getGameStatistic() {
        if (INSTANCE == null) {
            INSTANCE = new GameStatistic();
        }
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = null;
    }

    public void clear() {
        totalExplosions.set(0);
        totalDropBonus.set(0);
        totalFlaskUses.set(0);
        totalNumberOfDrawnChips.set(0);
        rubyCount.set(0);
        chipCount.clear();
        roundStatistics.clear();
        colorTriggeredTimes.clear();
    }

    public AtomicInteger getTotalDropBonus() {
        return totalDropBonus;
    }

    public void incrementDropBonus() {

        totalDropBonus.incrementAndGet();
    }

    public void addRuby() {
        rubyCount.incrementAndGet();
    }

    public HashMap<Chip, AtomicInteger> getChipCount() {
        return chipCount;
    }

    public void setChipCount(HashMap<Chip, AtomicInteger> chipCount) {
        this.chipCount = chipCount;
    }

    public List<RoundStatistic> getRoundStatistics() {
        return roundStatistics;
    }

    public void setRoundStatistics(List<RoundStatistic> roundStatistics) {
        this.roundStatistics = roundStatistics;
    }

    public void addBuyedChip(Chip chip) {
        chipCount.computeIfAbsent(chip, k -> new AtomicInteger(0)).
                incrementAndGet();
    }

    public AtomicInteger getTotalExplosions() {
        return totalExplosions;
    }

    public void incrementTotalExplosions() {
        totalExplosions.incrementAndGet();
    }

    public void updateFromRoundStatistic(RoundStatistic roundStatistic) {
        if (roundStatistic == null) {
            return;
        }

        if (roundStatistic.isExploded().get()) {
            incrementTotalExplosions();
        }
        if (roundStatistic.isFlaskUsed()) {
            totalFlaskUses.incrementAndGet();
        }
        totalNumberOfDrawnChips.addAndGet(roundStatistic.getDrawnChips().size());

        // Aktualisiere colorTriggeredTimes - addiere die Werte für jede ChipColor
        for (ChipColor color : roundStatistic.getColorTriggeredTimes().keySet()) {
            int roundValue = roundStatistic.getColorTriggeredTimes().get(color).get();
            colorTriggeredTimes.computeIfAbsent(color, k -> new AtomicInteger(0))
                    .addAndGet(roundValue);
        }

        roundStatistics.add(roundStatistic);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Spielstatistik ===\n");

        sb.append("Gesamtexplosionen: ").append(getTotalExplosions()).append("\n");
        sb.append("Bonus-Tropfen: ").append(getTotalDropBonus()).append("\n");
        sb.append("Anzahl Flasche verwendet: ").append(totalFlaskUses.get()).append("\n");
        sb.append("Gesamtgezogene Chips: ").append(totalNumberOfDrawnChips.get()).append("\n");
        sb.append("Anzahl Rubine: ").append(rubyCount.get()).append("\n");
        sb.append(buildStringForAllChips());
        sb.append("Farbeffekte ausgelöst:\n");

        if (colorTriggeredTimes.isEmpty()) {
            sb.append("  Keine\n");
        } else {
            for (ChipColor color : colorTriggeredTimes.keySet()) {
                sb.append("  ").append(color.getColorname())
                        .append(": ")
                        .append(colorTriggeredTimes.get(color).get()).append("x\n");
            }
        }

        sb.append("=======================");
        return sb.toString();
    }

    private StringBuilder buildStringForAllChips() {
        StringBuilder sb = new StringBuilder();
        for (Chip chip : chipCount.keySet()) {  // 'chip' is declared here
            sb.append(chip.getColor().getColorname()).append(": ")
                    .append(chipCount.get(chip).get()).append("\n");
        }
        return sb;
    }
}
