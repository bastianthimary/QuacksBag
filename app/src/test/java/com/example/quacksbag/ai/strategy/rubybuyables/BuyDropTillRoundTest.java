package com.example.quacksbag.ai.strategy.rubybuyables;

import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.player.RubyBuyables;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BuyDropTillRoundTest {

    @Test
    void testBuyDropTillRoundCreation() {
        BuyDropTillRound strategy = new BuyDropTillRound(5);
        
        assertNotNull(strategy);
    }

    @Test
    void testDecideBuyDropOrFlaskBeforeRound() {
        BuyDropTillRound strategy = new BuyDropTillRound(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(3);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.DROP, result);
    }

    @Test
    void testDecideBuyDropOrFlaskAtExactRound() {
        BuyDropTillRound strategy = new BuyDropTillRound(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(5);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.DROP, result);
    }

    @Test
    void testDecideBuyDropOrFlaskAfterRound() {
        BuyDropTillRound strategy = new BuyDropTillRound(5);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(6);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.NONE, result);
    }

    @Test
    void testDecideBuyDropOrFlaskAtRoundOne() {
        BuyDropTillRound strategy = new BuyDropTillRound(3);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(1);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.DROP, result);
    }

    @Test
    void testDecideBuyDropOrFlaskWithHighRoundLimit() {
        BuyDropTillRound strategy = new BuyDropTillRound(10);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(8);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.DROP, result);
    }

    @Test
    void testDecideBuyDropOrFlaskWithZeroRoundLimit() {
        BuyDropTillRound strategy = new BuyDropTillRound(0);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(0);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.DROP, result);
    }

    @Test
    void testDecideBuyDropOrFlaskAfterZeroRoundLimit() {
        BuyDropTillRound strategy = new BuyDropTillRound(0);
        GameManager gameManager = Mockito.mock(GameManager.class);
        when(gameManager.getCurrentRound()).thenReturn(1);
        
        RubyBuyables result = strategy.decideBuyDropOrFlask(gameManager);
        
        assertEquals(RubyBuyables.NONE, result);
    }
}
