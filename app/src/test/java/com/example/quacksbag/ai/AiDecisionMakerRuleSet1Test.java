package com.example.quacksbag.ai;


import com.example.quacksbag.ai.strategy.factory.AiDesisionMakerRuleSet1Factory;
import com.example.quacksbag.ai.strategy.factory.draw.DrawStrategyOption;
import com.example.quacksbag.ai.strategy.factory.draw.DrawStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.explosion.ExplosionStrategyOption;
import com.example.quacksbag.ai.strategy.factory.explosion.ExplosionStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.flask.FlaskStrategyOption;
import com.example.quacksbag.ai.strategy.factory.flask.FlaskStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.factory.ruby.RubyBuyableStrategyOption;
import com.example.quacksbag.ai.strategy.factory.ruby.RubyBuyableStrategyOptionWrapper;
import com.example.quacksbag.ai.strategy.shopping.WishedChip;
import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.logging.Logger;
import com.example.quacksbag.max.strategy.buy.ComboResultWeight;
import com.example.quacksbag.ruleset.implementations.Ruleset1;
import com.example.quacksbag.util.SimulationStatistics;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Setup1
 * DrawStrategyOption.SAFE_DRAW,
 * new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
 * ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
 * List.of(new WishedChip(ChipColor.RED)),
 * new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
 * new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8):
 * <p>
 * <p>
 * Setup 2:
 * DrawStrategyOption.SAFE_DRAW,
 * new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
 * ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
 * List.of(new WishedChip(ChipColor.RED),new WishedChip(4,ChipColor.ORANGE)),
 * new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
 * new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
 * <p>
 * Setup 3:
 * AiDesisionMakerRuleSet1
 * DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY,0.1),
 * new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
 * ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
 * List.of(new WishedChip(ChipColor.ORANGE),new WishedChip(ChipColor.BLACK)),
 * new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
 * new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
 */
class AiDecisionMakerRuleSet1Test {
   // @Test
    public void testSimulation() {
        Logger.setLevel(Logger.Level.RESULTBASE);
        Logger.info("Starting simulation...");

        // Reset statistics before running tests
        SimulationStatistics.getInstance().reset();

        int numberOfSimulations = 100000;
        Logger.info("Running " + numberOfSimulations + " simulations...");

        // Initialize the decision maker once
        AiDecisionMakerRuleSet1 decisionMakerRuleSet1 = AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY,0.5),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.BLACK)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        );

        // Run simulations
        for (int i = 0; i < numberOfSimulations; i++) {

            GameManager gameManager = new GameManager(new Ruleset1(), "test_" + i, decisionMakerRuleSet1);
            var result = gameManager.playGame();

            SimulationStatistics.getInstance().recordResult(result);
        }

        // Print final statistics
        SimulationStatistics.getInstance().printStatistics();
    }
}