package com.example.quacksbag.ai.strategy.flask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class UseFlaskByProbabilityStrategyTest {

    @Test
    void testCalcProbabilityOfDrawingWhiteWithAllWhite() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(10, 10);
        
        assertEquals(1.0, probability, 0.001);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithNoWhite() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(0, 10);
        
        assertEquals(0.0, probability, 0.001);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithHalf() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(5, 10);
        
        assertEquals(0.5, probability, 0.001);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithOneThird() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(1, 3);
        
        assertEquals(0.333, probability, 0.01);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithTwoThirds() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(2, 3);
        
        assertEquals(0.666, probability, 0.01);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithOneFourth() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(1, 4);
        
        assertEquals(0.25, probability, 0.001);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithThreeFourths() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(3, 4);
        
        assertEquals(0.75, probability, 0.001);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithLargeNumbers() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(50, 100);
        
        assertEquals(0.5, probability, 0.001);
    }

    @Test
    void testStrategyCreationWithProbability() {
        UseFlaskByProbabilityStrategy strategy = new UseFlaskByProbabilityStrategy(0.5);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithZeroProbability() {
        UseFlaskByProbabilityStrategy strategy = new UseFlaskByProbabilityStrategy(0.0);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithOneProbability() {
        UseFlaskByProbabilityStrategy strategy = new UseFlaskByProbabilityStrategy(1.0);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithHighProbability() {
        UseFlaskByProbabilityStrategy strategy = new UseFlaskByProbabilityStrategy(0.9);
        
        assertNotNull(strategy);
    }

    @Test
    void testCalcProbabilityOfDrawingWhiteWithOneChip() {
        double probability = UseFlaskByProbabilityStrategy.calcProbabilityOfDrawingWhite(1, 1);
        
        assertEquals(1.0, probability, 0.001);
    }
}
