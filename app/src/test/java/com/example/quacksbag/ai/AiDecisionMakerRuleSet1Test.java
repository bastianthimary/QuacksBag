package com.example.quacksbag.ai;


import static com.example.quacksbag.statistic.GameStatistic.getGameStatistic;
import static com.example.quacksbag.statistic.RoundStatistic.getRoundStatistic;

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
import com.example.quacksbag.statistic.GameStatistic;
import com.example.quacksbag.statistic.RoundStatistic;
import com.example.quacksbag.statistic.SimulationStatistics;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

class AiDecisionMakerRuleSet1Test {
    HashMap<String, AiDecisionMakerRuleSet1> strategySettings = new HashMap<>();

    @Test
    void testSimulation() {
        Logger.setLevel(Logger.Level.RESULTBASE);
        Logger.info("Starting simulation...");

        // Reset statistics before running tests
        SimulationStatistics.getInstance().reset();

        int numberOfSimulations = 10000;
        Logger.info("Running " + numberOfSimulations + " simulations...");

        // Initialize the decision maker once
        AiDecisionMakerRuleSet1 decisionMakerRuleSet1 = initStrategies();

        // Run simulations
        for (int i = 0; i < numberOfSimulations; i++) {

            GameManager gameManager = new GameManager(new Ruleset1(), "test_" + i, decisionMakerRuleSet1);
            var result = gameManager.playGame();

            SimulationStatistics.getInstance().recordResult(result);
            RoundStatistic.reset();
            GameStatistic.getGameStatistic().clear();
        }

        // Print final statistics
        SimulationStatistics.getInstance().printStatistics();
    }

    @Test
    public void testSingleSimulation() {
        Logger.setLevel(Logger.Level.RESULTBASE);
        Logger.info("Starting simulation...");

        // Reset statistics before running tests
        SimulationStatistics.getInstance().reset();

        int numberOfSimulations = 1;
        Logger.info("Running " + numberOfSimulations + " simulations...");
        initStrategies();
        // Initialize the decision maker once
        AiDecisionMakerRuleSet1 decisionMakerRuleSet1 = strategySettings.get("Setup15");

        // Run simulations
        for (int i = 0; i < numberOfSimulations; i++) {

            GameManager gameManager = new GameManager(new Ruleset1(), "test_" + i, decisionMakerRuleSet1);
            var result = gameManager.playGame();

            SimulationStatistics.getInstance().recordResult(result);
        }
        Logger.resultDeep(getRoundStatistic().toString());
        // Print final statistics
        SimulationStatistics.getInstance().printStatistics();
        Logger.resultDeep(getGameStatistic().toString());
    }

    private AiDecisionMakerRuleSet1 initStrategies() {
        strategySettings.put("Setup1", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.25),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//7,79 Avg Score /19/1
        strategySettings.put("Setup2", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.34),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//10,41/22/2
        strategySettings.put("Setup3", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.5),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//7,83 /19/1
        strategySettings.put("Setup4", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.41),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 9,63/20/1
        strategySettings.put("Setup5", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.37),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,29/21/2
        strategySettings.put("Setup6", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.35),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,40/21/3
        strategySettings.put("Setup7", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,75/20/2
        strategySettings.put("Setup8", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.32),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,73/19/2
        strategySettings.put("Setup9", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));// 10,94/22/2
        strategySettings.put("Setup10", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));// 10,95/22/1

        strategySettings.put("Setup11", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.3),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,01/22/2
        strategySettings.put("Setup12", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.25),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,08/22/2
        strategySettings.put("Setup13", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,09/22/2
        strategySettings.put("Setup14", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.1),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,08/22/2
        strategySettings.put("Setup15", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 7)
        ));//11,76/22/2
        strategySettings.put("Setup16", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 7)
        ));//15,50/30/4
        strategySettings.put("Setup17", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 8)
        ));//15,47/31/5
        strategySettings.put("Setup18", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 6)
        ));//15,51/29/3
        strategySettings.put("Setup19", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 5)
        ));//15,52 /29/5
        strategySettings.put("Setup20", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//15,51/29/4
        strategySettings.put("Setup21", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//15,52/31/5
        strategySettings.put("Setup22", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.RED), new WishedChip(4, ChipColor.ORANGE)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//16,55/35/4
        strategySettings.put("Setup23", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                ComboResultWeight.MAX_CHIPNUMBER,
                List.of(new WishedChip(ChipColor.RED), new WishedChip(4, ChipColor.ORANGE)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//

        return strategySettings.get("Setup23");
    }
}