package com.example.quacksbag.statistic;

import com.example.quacksbag.gamematerial.Chip;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationStatistics {
    private static final SimulationStatistics INSTANCE = new SimulationStatistics();
    
    private final AtomicInteger totalRuns = new AtomicInteger(0);
    private final AtomicInteger totalScore = new AtomicInteger(0);
    private final AtomicInteger totalExplodes = new AtomicInteger(0);
    private final AtomicInteger totalDropBonus = new AtomicInteger(0);
    private final AtomicInteger totalRubyCount = new AtomicInteger(0);
    private int maxScore = Integer.MIN_VALUE;
    private int minScore = Integer.MAX_VALUE;
    private HashMap<Chip, AtomicInteger> chipCount = new HashMap<>();
    private final Object lock = new Object();

    private SimulationStatistics() {
        // Private constructor for singleton
    }

    public static SimulationStatistics getInstance() {
        return INSTANCE;
    }

    public void recordResult(int score) {
        synchronized (lock) {
            totalRuns.incrementAndGet();
            totalScore.addAndGet(score);

            maxScore = Math.max(maxScore, score);
            minScore = Math.min(minScore, score);
        }
    }

    public void incrementExplosion(){
        totalExplodes.incrementAndGet();
    }

    public void printStatistics() {
        synchronized (lock) {
            int runs = totalRuns.get();
            if (runs == 0) {
                System.out.println("No simulation data recorded yet.");
                return;
            }


            double explodeRate = totalExplodes.get() / (double) runs;
            
            System.out.println("\n=== Simulation Statistics ===");
            System.out.println("Total runs: " + runs);
            System.out.println("Average score: " + getPerRoundAvg(totalScore.get()));
            System.out.println("Maximum score: " + maxScore);
            System.out.println("Minimum score: " + minScore);
            System.out.println("Explosion rate: " + String.format("%.2f", explodeRate) + " pro Spiel");
            System.out.println("Drop bonus rate: " + getPerRoundAvg(totalDropBonus.get()));
            System.out.println("Rubine: " + getPerRoundAvg(totalRubyCount.get()));
            System.out.println("Chip count:");
            System.out.println(buildStringForAllChips());
            System.out.println("==========================\n");
        }
    }

    public void reset() {
        synchronized (lock) {
            totalRuns.set(0);
            totalScore.set(0);
            totalExplodes.set(0);
            maxScore = Integer.MIN_VALUE;
            minScore = Integer.MAX_VALUE;
        }
    }

    public void recordGameStatistic(GameStatistic gameStatistic) {
        totalExplodes.addAndGet(gameStatistic.getTotalExplosions().get());
        totalDropBonus.addAndGet(gameStatistic.getTotalDropBonus().get());
        totalRubyCount.addAndGet(gameStatistic.getRubyCount().get());
        addAllChips(gameStatistic.getChipCount());
    }

    private void addAllChips(HashMap<Chip, AtomicInteger> allchips) {
        for (Chip chip : allchips.keySet()) {
            chipCount.computeIfAbsent(chip, k -> new AtomicInteger(0)).
                    addAndGet(allchips.get(chip).get());
        }
    }

    private StringBuilder buildStringForAllChips() {
        StringBuilder sb = new StringBuilder();
        for (Chip chip : chipCount.keySet()) {  // 'chip' is declared here
            sb.append(chip.getColor().getColorname()).append("").append(chip.getValue())
                    .append(": ")
                    .append(getPerRoundAvg(chipCount.get(chip).get())).append("\n");
        }
        return sb;
    }

    private String getPerRoundAvg(int summaryOfAll) {
        return String.format("%.2f", summaryOfAll / (double) totalRuns.get());
    }
}
