package com.example.quacksbag.ai; // Angenommenes Paket

import com.example.quacksbag.ai.strategy.draw.DrawStrategy;
import com.example.quacksbag.ai.strategy.explosion.ExplosionStrategy;
import com.example.quacksbag.ai.strategy.flask.FlaskStrategy;
import com.example.quacksbag.ai.strategy.rubybuyables.RubyBuyableStrategy;
import com.example.quacksbag.ai.strategy.shopping.ShoppingStrategy;
import com.example.quacksbag.baserules.GameManager;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DrawChoice;
import com.example.quacksbag.player.ExplosionChoice;
import com.example.quacksbag.player.RubyBuyables;
import com.example.quacksbag.ruleset.ChipPrice;
import com.example.quacksbag.ruleset.implementations.ChoosenChip;
import com.example.quacksbag.ruleset.implementations.Rulset1DecisionMaker;

import java.util.List;


public class AiDecisionMakerRuleSet1 extends Rulset1DecisionMaker {
    private GameManager gameManager;

    private final DrawStrategy drawStrategy;
    private final ExplosionStrategy explosionStrategy;
    private final ShoppingStrategy shoppingStrategy;
    private final FlaskStrategy flaskStrategy;
    private final RubyBuyableStrategy rubyBuyableStrategy;

    public AiDecisionMakerRuleSet1(DrawStrategy drawStrategy, ExplosionStrategy explosionStrategy, ShoppingStrategy shoppingStrategy, FlaskStrategy flaskStrategy, RubyBuyableStrategy rubyBuyableStrategy) {
        this.drawStrategy = drawStrategy;
        this.explosionStrategy = explosionStrategy;
        this.shoppingStrategy = shoppingStrategy;
        this.flaskStrategy = flaskStrategy;
        this.rubyBuyableStrategy = rubyBuyableStrategy;
    }

    @Override
    public ExplosionChoice makeChoiceOnExplosion() {
        return explosionStrategy.decideExplosion(gameManager);
    }

    @Override
    public List<Chip> doShoppingChoice(int bubbleValue, List<ChipPrice> buyableChips) {
        return shoppingStrategy.decideShopping(gameManager, bubbleValue, buyableChips);
    }

    @Override
    public boolean wantToUseFlask(Chip chip) {
        return flaskStrategy.decideUseFlask(gameManager, chip);
    }

    @Override
    public DrawChoice doAnotherDraw() {
        return drawStrategy.decideDraw(gameManager);
    }

    @Override
    public RubyBuyables buyDropOrFlask() {
        return rubyBuyableStrategy.decideBuyDropOrFlask(gameManager);
    }

    @Override
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public ChoosenChip chooseChipForBlueEffect(List<Chip> chips) {
        ChoosenChip choosenChip = null;
        for (Chip chip : chips) {
            if (!ChipColor.WHITE.equals(chip.getColor())) {
                if (choosenChip == null) {
                    choosenChip = new ChoosenChip(chip);
                } else {
                    if (choosenChip.getChip().getValue() < chip.getValue()) {
                        choosenChip = new ChoosenChip(chip);
                    }
                }
            }
        }


        return choosenChip;
    }
}
