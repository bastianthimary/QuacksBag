package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PureVictroyExplosionStrategyTest {

    @Test
    void testPureVictroyExplosionStrategyCreation() {
        PureVictroyExplosionStrategy strategy = new PureVictroyExplosionStrategy();
        
        assertNotNull(strategy);
    }

    @Test
    void testDecideExplosionAlwaysReturnsVictoryPoints() {
        PureVictroyExplosionStrategy strategy = new PureVictroyExplosionStrategy();
        GameManager gameManager = Mockito.mock(GameManager.class);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.VICTORYPOINTS, result);
    }

    @Test
    void testDecideExplosionMultipleCalls() {
        PureVictroyExplosionStrategy strategy = new PureVictroyExplosionStrategy();
        GameManager gameManager = Mockito.mock(GameManager.class);
        
        ExplosionChoice result1 = strategy.decideExplosion(gameManager);
        ExplosionChoice result2 = strategy.decideExplosion(gameManager);
        ExplosionChoice result3 = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.VICTORYPOINTS, result1);
        assertEquals(ExplosionChoice.VICTORYPOINTS, result2);
        assertEquals(ExplosionChoice.VICTORYPOINTS, result3);
    }
}
