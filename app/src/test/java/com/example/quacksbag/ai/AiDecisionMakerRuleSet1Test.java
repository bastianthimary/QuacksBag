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
import com.example.quacksbag.ai.strategy.factory.shopping.ShoppingStrategyOptionWrapper;
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

        int numberOfSimulations = 100000;
        Logger.info("Running " + numberOfSimulations + " simulations...");

        // Initialize the decision maker once
        AiDecisionMakerRuleSet1 decisionMakerRuleSet1 = initStrategies();

        // Run simulations
        for (int i = 0; i < numberOfSimulations; i++) {

            GameManager gameManager = new GameManager(new Ruleset1(), "test_" + i, decisionMakerRuleSet1);
            var result = gameManager.playGame();

            SimulationStatistics.getInstance().recordResult(result);
            SimulationStatistics.getInstance().recordGameStatistic(getGameStatistic());
            RoundStatistic.reset();
            GameStatistic.getGameStatistic().clear();
        }

        // Print final statistics
        SimulationStatistics.getInstance().printStatistics();
        Logger.resultBase(getGameStatistic().toString());
    }

    @Test
    public void testSingleSimulation() {
        Logger.setLevel(Logger.Level.RESULTDEEP);
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
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//7,79 Avg Score /19/1
        strategySettings.put("Setup2", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.34),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//10,41/22/2
        strategySettings.put("Setup3", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.5),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));//7,83 /19/1
        strategySettings.put("Setup4", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.41),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 9,63/20/1
        strategySettings.put("Setup5", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.37),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,29/21/2
        strategySettings.put("Setup6", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.35),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,40/21/3
        strategySettings.put("Setup7", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,75/20/2
        strategySettings.put("Setup8", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.32),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 8)
        ));// 10,73/19/2
        strategySettings.put("Setup9", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));// 10,94/22/2
        strategySettings.put("Setup10", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.4),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));// 10,95/22/1

        strategySettings.put("Setup11", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.3),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,01/22/2
        strategySettings.put("Setup12", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.25),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,08/22/2
        strategySettings.put("Setup13", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,09/22/2
        strategySettings.put("Setup14", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.1),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//11,08/22/2
        strategySettings.put("Setup15", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 7)
        ));//11,76/22/2
        strategySettings.put("Setup16", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 7)
        ));//15,50/30/4
        strategySettings.put("Setup17", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 8)
        ));//15,47/31/5
        strategySettings.put("Setup18", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 6)
        ));//15,51/29/3
        strategySettings.put("Setup19", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 5)
        ));//15,52 /29/5
        strategySettings.put("Setup20", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.YELLOW), new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//15,51/29/4
        strategySettings.put("Setup21", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//15,52/31/5
        strategySettings.put("Setup22", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(4, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_FLASK_TILL_ROUND, 4)
        ));//16,55/35/4
        strategySettings.put("Setup23", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(4, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//16,58/37/4
        strategySettings.put("Setup24", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(5, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,03/40/4
        strategySettings.put("Setup25", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(6, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,25/43/3
        strategySettings.put("Setup26", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(7, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,38/43/2
        strategySettings.put("Setup27", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(8, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,36/42/2
        strategySettings.put("Setup28", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(9, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,38/42/3
        strategySettings.put("Setup29", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(10, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,38/44/3
        strategySettings.put("Setup30", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(11, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,38/42/2
        strategySettings.put("Setup31", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(12, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,40/46/2

        strategySettings.put("Setup32", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.MAX_CHIPNUMBER, List.of(new WishedChip(ChipColor.RED), new WishedChip(13, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,41/44/2
        strategySettings.put("Setup33", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.RED), new WishedChip(13, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,83/42/2

        strategySettings.put("Setup34", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.RED), new WishedChip(15, ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//17,84/42/3
        strategySettings.put("Setup35", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.ORANGE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//13,43/25/2

        strategySettings.put("Setup36", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.BLUE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 4)
        ));//14,22/26/2/2,19/0,88/3,08
        strategySettings.put("Setup37", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.GREEN))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//14,86/34/3/3,09/2,69/7/
        strategySettings.put("Setup38", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.BLACK))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//13,58/25/3/2,85/5,68/3,06
        strategySettings.put("Setup39", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.PURPLE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//18,97/34/5/2,75/3,18/4,35
        strategySettings.put("Setup40", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.PURPLE), new WishedChip(ChipColor.BLUE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//16,09/32/4/2,22/1,58/3,5/ B1: 9,85 / B2:1,18 /P1:1,28
        strategySettings.put("Setup41", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(ComboResultWeight.ALLWAYS2CHIPS_MAXSCORE, List.of(new WishedChip(ChipColor.PURPLE), new WishedChip(5, ChipColor.BLUE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//17,41/31/5/2,15/2,23/4,15/ B1:5,45/ B2:0,03/P:3,73

        strategySettings.put("Setup42", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(List.of(new WishedChip(ChipColor.PURPLE)), List.of(new WishedChip(ChipColor.BLUE))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//18,12/34/6/2,09/2,95/4,29/B1:4.08/P:4,82

        strategySettings.put("Setup43", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(new WishedChip(ChipColor.PURPLE), new WishedChip(ChipColor.BLUE), new WishedChip(ChipColor.GREEN)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//18,25/32/5/2,14/2,96/4,36/B1:4,16/P:4,82/G1:0,52
        strategySettings.put("Setup44", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(List.of(new WishedChip(ChipColor.PURPLE)), List.of(new WishedChip(ChipColor.ORANGE), new WishedChip(ChipColor.RED))),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//20,43/38/4/3,19/3,39/4,27/O1:6,61/R1:1,78/R2:0,06/P1:5,86
        strategySettings.put("Setup45", AiDesisionMakerRuleSet1Factory.createAiDesisionMakerRuleSet1(
                new DrawStrategyOptionWrapper(DrawStrategyOption.CUSTOM_DRAW_BY_PROBABILITY, 0.33),
                new ExplosionStrategyOptionWrapper(ExplosionStrategyOption.PURE_SHOPPING),
                new ShoppingStrategyOptionWrapper(new WishedChip(ChipColor.PURPLE), new WishedChip(2, ChipColor.RED), new WishedChip(1, ChipColor.ORANGE)),
                new FlaskStrategyOptionWrapper(FlaskStrategyOption.USE_FLASK_BY_PROBABILITY, 0.2),
                new RubyBuyableStrategyOptionWrapper(RubyBuyableStrategyOption.BUY_DROP_TILL_ROUND, 7)
        ));//19,89/39/5/2,93/3,23/4,26/O1:5,31/R1:1,98/R2:0,06/P1:5,86/R3:0,02


        return strategySettings.get("Setup45");
    }
}