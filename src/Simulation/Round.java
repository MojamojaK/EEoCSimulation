package Simulation;

import Action.*;
import Agent.Agent;
import Networks.*;
import Constants.*;

import java.util.ListIterator;

class Round {

    private int roundNum;

    Round(int roundNum, RewardScheme rewardScheme, Parameters params, Network network) {
        int CC = 0, CD = 0, DD = 0;
        this.roundNum = roundNum;
        ListIterator<Link> iterator = network.getLinkIterator();
        while (iterator.hasNext()) {
            Link link = iterator.next();
            Agent bravo = link.getRightAgent();
            Agent charlie = link.getLeftAgent();
            // get actions
            Action action1 = bravo.getNextAction(charlie, params.getEpsilon());
            Action action2 = charlie.getNextAction(bravo, params.getEpsilon());
            // get interaction result with C/D T P R S table
            Action.setResults(action1, action2, rewardScheme);
            // update Q
            bravo.updateDecider(charlie, action1, params.getAlpha());
            charlie.updateDecider(bravo, action2, params.getAlpha());
            // print interaction stat;
            // System.out.println(String.format("(%d %s, %d %s)",
            //         bravo.getId(), action1, charlie.getId(), action2));
            if (action1.getType() == ActionType.COOPERATE) {
                if (action2.getType() == ActionType.COOPERATE) CC++;
                else CD++;
            } else {
                if (action2.getType() == ActionType.COOPERATE) CD++;
                else DD++;
            }
        }
        System.out.println(String.format("R:%4d, CC: %5d, CD: %5d, DD: %5d", getNumber(),  CC, CD, DD));
    }

    private int getNumber() {
        return roundNum;
    }
}
