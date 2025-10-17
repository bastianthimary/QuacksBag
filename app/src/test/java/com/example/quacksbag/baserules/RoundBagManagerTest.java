package com.example.quacksbag.baserules;

import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundBagManagerTest {

    private RoundBagManager roundBagManager;
    private List<Chip> purchasedChips;

    @BeforeEach
    void setUp() {
        purchasedChips = new ArrayList<>();
        // Beispiel: Füge einige gekaufte Chips für Tests hinzu
        purchasedChips.add(new Chip(ChipColor.BLUE,  1));
        purchasedChips.add(new Chip(ChipColor.RED,  2));
        roundBagManager = new RoundBagManager(purchasedChips);
    }

    @Test
    void constructor_initializesBagWithDefaultAndPurchasedChips() {
        // Die Standardchips sind 4x weiß1, 2x weiß2, 1x weiß3, 1x orange1, 1x grün1
        // (4 + 2 + 1 + 1 + 1) = 9 Standardchips
    //+ 2 gekaufte =11

        assertEquals(11, roundBagManager.currentNumberOfUndrawnChips());
        assertEquals(0, roundBagManager.currentNumberOfDrawnChips());
        assertFalse(roundBagManager.isBagEmpty());
    }

    @Test
    void drawChipFromBag_reducesUndrawnAndIncreasesDrawn() {

        assertFalse(roundBagManager.isBagEmpty(), "Bag should not be empty before drawing");
        Chip drawnChip = roundBagManager.drawChipFromBag(0); // Zeichne den ersten Chip

        assertNotNull(drawnChip);
        assertEquals(10, roundBagManager.currentNumberOfUndrawnChips());

    }


    
    @Test
    void drawChipFromBag_throwsExceptionWhenBagIsEmpty() {
        // Ziehe alle Chips
        while(!roundBagManager.isBagEmpty()){
            roundBagManager.drawChipFromBag(0);
        }
        assertTrue(roundBagManager.isBagEmpty());
        // Der Versuch, aus einem leeren Beutel zu ziehen, sollte fehlschlagen (IndexOutOfBoundsException)
        assertThrows(IndexOutOfBoundsException.class, () -> {
            roundBagManager.drawChipFromBag(0);
        });
    }

    //@Test
    void throwChipBackInBag_movesChipFromDrawnToUndrawn() {
        // Zuerst einen Chip ziehen
        Chip drawnChip = roundBagManager.drawChipFromBag(0);
        int undrawnAfterDraw = roundBagManager.currentNumberOfUndrawnChips();
        int drawnAfterDraw = roundBagManager.currentNumberOfDrawnChips();


        // Chip zurückwerfen
        boolean thrownBack = roundBagManager.throwChipBackInBag(drawnChip);

        assertTrue(thrownBack);
        assertEquals(undrawnAfterDraw + 1, roundBagManager.currentNumberOfUndrawnChips());
        assertEquals(drawnAfterDraw - 1, roundBagManager.currentNumberOfDrawnChips());
        assertFalse(roundBagManager.getDrawnChips().contains(drawnChip));
        // Überprüfen, ob der Chip wieder im undrawn-Stapel ist (schwierig ohne die interne Liste zu sehen,
        // aber die Anzahl sollte stimmen und er sollte nicht mehr bei drawn sein)
    }

    @Test
    void throwChipBackInBag_returnsFalseForNonDrawnChip() {
        Chip nonDrawnChip = new Chip(ChipColor.BLACK,  1); // Ein Chip, der nicht gezogen wurde

        boolean thrownBack = roundBagManager.throwChipBackInBag(nonDrawnChip);

        assertFalse(thrownBack);
        assertEquals(11, roundBagManager.currentNumberOfUndrawnChips());
        assertEquals(0, roundBagManager.currentNumberOfDrawnChips());
    }

    @Test
    void isBagEmpty_returnsTrueWhenUndrawnIsEmpty() {
        // Alle Chips ziehen
        while (!roundBagManager.isBagEmpty()) {
            roundBagManager.drawChipFromBag(0);
        }
        assertTrue(roundBagManager.isBagEmpty());
    }

    @Test
    void isBagEmpty_returnsFalseWhenUndrawnIsNotEmpty() {
        assertFalse(roundBagManager.isBagEmpty());
    }

    @Test
    void currentNumberOfUndrawnChips_returnsCorrectCount() {
        int expected = BagUtil.defaultStartingChips().size() + purchasedChips.size();
        assertEquals(expected, roundBagManager.currentNumberOfUndrawnChips());
        roundBagManager.drawChipFromBag(0);
        assertEquals(expected - 1, roundBagManager.currentNumberOfUndrawnChips());
    }

}