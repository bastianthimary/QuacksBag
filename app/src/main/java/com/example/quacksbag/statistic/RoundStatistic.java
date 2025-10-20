package com.example.quacksbag.statistic;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundStatistic {
    private static RoundStatistic INSTANCE;
    private final int roundNumber;
    private int endposition;
    private int shoppingPoints;
    private boolean wasRubyBubble;
    private final AtomicBoolean isExploded = new AtomicBoolean(false);
    private List<Chip> drawnChips = new ArrayList<>();
    private List<Chip> allChipsInBag = new ArrayList<>();
    private final AtomicBoolean flaskUsed = new AtomicBoolean(false);
    private final HashMap<ChipColor, AtomicInteger> colorTriggeredTimes = new HashMap<>();
    private int victoryPoints;

    public RoundStatistic(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public static RoundStatistic getRoundStatistic() {
        if (INSTANCE == null) {
            INSTANCE = new RoundStatistic(0);
        }
        return INSTANCE;
    }

    public void startNewRound(int roundNumber) {
        if (INSTANCE != null && INSTANCE.roundNumber != 0) {
            // Only log if RESULTDEEP level is enabled to avoid memory issues
            if (Logger.isLevelEnabled(Logger.Level.RESULTDEEP)) {
                Logger.resultDeep(toString());
            }
        }
        INSTANCE = new RoundStatistic(roundNumber);
    }

    public static void reset() {
        INSTANCE = null;
    }

    public void setEndposition(int endposition) {
        this.endposition = endposition;
    }

    public void setShoppingPoints(int shoppingPoints) {
        this.shoppingPoints = shoppingPoints;
    }

    public void setWasRubyBubble(boolean wasRubyBubble) {
        this.wasRubyBubble = wasRubyBubble;
    }

    public List<Chip> getDrawnChips() {
        return drawnChips;
    }


    public boolean isFlaskUsed() {
        return flaskUsed.get();
    }

    public void explode() {
        isExploded.set(true);
    }

    public void setDrawnChips(List<Chip> drawnChips) {
        this.drawnChips = drawnChips;
    }

    public void setAllChipsInBag(List<Chip> allChipsInBag) {
        this.allChipsInBag = allChipsInBag;
    }

    public AtomicBoolean isExploded() {
        return isExploded;
    }

    public void incrementColorTriggered(ChipColor color) {
        colorTriggeredTimes.computeIfAbsent(color, k -> new AtomicInteger(0)).incrementAndGet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Rundenstatistik ===\n");
        sb.append("Runde: ").append(roundNumber).append("\n");
        sb.append("Ist Explodiert?: ").append(isExploded.get() ? "Ja" : "Nein").append("\n");
        sb.append("Endposition: ").append(endposition).append("\n");
        sb.append("Shoppingpunkte: ").append(shoppingPoints).append("\n");
        sb.append("Siegpunkte: ").append(victoryPoints).append("\n");
        sb.append("Rubinfeld: ").append(wasRubyBubble ? "Ja" : "Nein").append("\n");
        sb.append("Alle Chips im Bag: ").append(allChipsInBag.size()).append("\n");
        sb.append("Gezogene Chips: ").append(getDrawnChips().size()).append("\n");
        sb.append(buildStringForAllDrawnChips()).append("\n");
        sb.append("Flasche verwendet: ").append(isFlaskUsed() ? "Ja" : "Nein").append("\n");
        sb.append("Farbeffekte ausgelöst:\n");

        if (colorTriggeredTimes.isEmpty()) {
            sb.append("  Keine\n");
        } else {
            for (ChipColor color : colorTriggeredTimes.keySet()) {
                sb.append("  ").append(color.getColorname()).append(": ")
                        .append(colorTriggeredTimes.get(color).get()).append("x\n");
            }
        }

        sb.append("=======================");
        return sb.toString();
    }

    private StringBuilder buildStringForAllDrawnChips() {
        StringBuilder sb = new StringBuilder();
        drawnChips.forEach(chip -> sb.append("Chip: ")
                .append(chip.getColor().getColorname())
                .append(" ")
                .append(chip.getValue())
                .append("\n"));

        return sb;
    }

    public void useFlask() {
        flaskUsed.set(true);
    }

    public Map<ChipColor, AtomicInteger> getColorTriggeredTimes() {
        return colorTriggeredTimes;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
