package com.example.quacksbag.ruleset;

import com.example.quacksbag.baserules.PlayerScore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.baserules.RoundClaudron;
import com.example.quacksbag.gamematerial.Chip;
import com.example.quacksbag.gamematerial.ChipColor;
import com.example.quacksbag.player.DecisionMaker;

public interface Ruleset {

     int determineValueExecuteInsantEffectAndPutInClaudron(Chip chip, RoundBagManager chipsInClaudron, DecisionMaker decisionMaker, RoundClaudron roundClaudron);

        void executePurpleRoundEndEffect( RoundBagManager chipsInClaudron, PlayerScore playerScore);
      void executeGreenRoundEndEffect( RoundBagManager chipsInClaudron, PlayerScore playerScore);
      void executeBlackRoundEndEffect( RoundBagManager chipsInClaudron, PlayerScore playerScore);

      PriceRuleset getPriceRuleset();
}
