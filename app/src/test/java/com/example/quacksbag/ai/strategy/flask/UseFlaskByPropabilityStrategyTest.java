package com.example.quacksbag.ai.strategy.flask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UseFlaskByPropabilityStrategyTest {

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithAllWhite() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(10, 10);
        
        assertEquals(1.0, probability, 0.001);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithNoWhite() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(0, 10);
        
        assertEquals(0.0, probability, 0.001);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithHalf() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(5, 10);
        
        assertEquals(0.5, probability, 0.001);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithOneThird() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(1, 3);
        
        assertEquals(0.333, probability, 0.01);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithTwoThirds() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(2, 3);
        
        assertEquals(0.666, probability, 0.01);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithOneFourth() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(1, 4);
        
        assertEquals(0.25, probability, 0.001);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithThreeFourths() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(3, 4);
        
        assertEquals(0.75, probability, 0.001);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithLargeNumbers() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(50, 100);
        
        assertEquals(0.5, probability, 0.001);
    }

    @Test
    void testStrategyCreationWithProbability() {
        UseFlaskByPropabilityStrategy strategy = new UseFlaskByPropabilityStrategy(0.5);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithZeroProbability() {
        UseFlaskByPropabilityStrategy strategy = new UseFlaskByPropabilityStrategy(0.0);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithOneProbability() {
        UseFlaskByPropabilityStrategy strategy = new UseFlaskByPropabilityStrategy(1.0);
        
        assertNotNull(strategy);
    }

    @Test
    void testStrategyCreationWithHighProbability() {
        UseFlaskByPropabilityStrategy strategy = new UseFlaskByPropabilityStrategy(0.9);
        
        assertNotNull(strategy);
    }

    @Test
    void testDeterminePropabilityOfDrawingWhiteWithOneChip() {
        double probability = UseFlaskByPropabilityStrategy.determinePropabilityOfDrawingWhite(1, 1);
        
        assertEquals(1.0, probability, 0.001);
    }
}
