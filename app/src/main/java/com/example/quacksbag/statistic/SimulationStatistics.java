package com.example.quacksbag.statistic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SimulationStatistics {
    private static final SimulationStatistics INSTANCE = new SimulationStatistics();
    
    private final AtomicInteger totalRuns = new AtomicInteger(0);
    private final AtomicLong totalScore = new AtomicLong(0);
    private final AtomicInteger totalExplodes = new AtomicInteger(0);
    private final AtomicInteger totalDropBonus = new AtomicInteger(0);
    private int maxScore = Integer.MIN_VALUE;
    private int minScore = Integer.MAX_VALUE;
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
            
            double avgScore = totalScore.get() / (double) runs;
            double explodeRate = totalExplodes.get() / (double) runs * 100;
            
            System.out.println("\n=== Simulation Statistics ===");
            System.out.println("Total runs: " + runs);
            System.out.println("Average score: " + String.format("%.2f", avgScore));
            System.out.println("Maximum score: " + maxScore);
            System.out.println("Minimum score: " + minScore);
            System.out.println("Explosion rate: " + String.format("%.2f", explodeRate) + "%");
            System.out.println("Drop bonus rate: " +  totalDropBonus.get());
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
}
