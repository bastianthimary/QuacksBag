package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MixedExplosionStrategyTest {

    @Test
    void testMixedExplosionStrategyCreation() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(5);
        
        assertNotNull(strategy);
    }

    @Test
    void testDecideExplosionBeforeRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(3);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionAtExactRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(5);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionAfterRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(6);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.VICTORYPOINTS, result);
    }

    @Test
    void testDecideExplosionAtRoundOne() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(3);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(1);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionWithHighRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(10);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(8);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionWithZeroRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(0);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(0);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionAfterZeroRoundLimit() {
        MixedExplosionStrategy strategy = new MixedExplosionStrategy(0);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(1);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.VICTORYPOINTS, result);
    }
}
