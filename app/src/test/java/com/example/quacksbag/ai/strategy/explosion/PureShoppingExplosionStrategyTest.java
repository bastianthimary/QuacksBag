package com.example.quacksbag.ai.strategy.explosion;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.ExplosionChoice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PureShoppingExplosionStrategyTest {

    @Test
    void testPureShoppingExplosionStrategyCreation() {
        PureShoppingExplosionStrategy strategy = new PureShoppingExplosionStrategy();
        
        assertNotNull(strategy);
    }

    @Test
    void testDecideExplosionAlwaysReturnsShopping() {
        PureShoppingExplosionStrategy strategy = new PureShoppingExplosionStrategy();
        GameManager gameManager = Mockito.mock(GameManager.class);
        
        ExplosionChoice result = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result);
    }

    @Test
    void testDecideExplosionMultipleCalls() {
        PureShoppingExplosionStrategy strategy = new PureShoppingExplosionStrategy();
        GameManager gameManager = Mockito.mock(GameManager.class);
        
        ExplosionChoice result1 = strategy.decideExplosion(gameManager);
        ExplosionChoice result2 = strategy.decideExplosion(gameManager);
        ExplosionChoice result3 = strategy.decideExplosion(gameManager);
        
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result1);
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result2);
        assertEquals(ExplosionChoice.BUBBLESHOPPING, result3);
    }
}
