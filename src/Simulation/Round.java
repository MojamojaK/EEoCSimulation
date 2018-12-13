package Simulation;

import Action.*;
import Agent.Agent;
import Networks.*;
import Utility.*;

import java.util.ListIterator;

class Round {

    private int id;
    private int CC;
    private int CD;
    private int DD;
    private double CCRate;

    public int getID() {
        return id;
    }

    public int getCC() {
        return CC;
    }

    public int getCD() {
        return CD;
    }

    public int getDD() {
        return DD;
    }

    public double getCCRate() {
        return CCRate;
    }

    Round(int roundNum, RewardScheme rewardScheme, Parameters params, Network network) {
        this.id = roundNum;
//        System.out.printf("Round: %d\n", this.getID());
        CC = 0;
        CD = 0;
        DD = 0;

        // Set Action for each Agent
        ListIterator<Agent> agentListIterator = network.getAgentIterator();
        while (agentListIterator.hasNext()) {
            Agent agent = agentListIterator.next();
            agent.updateNextAction(params.getEpsilon());
        }

        // Shuffle Link Order for iteration
        network.shuffleLinkOrder();

        // Update Learner per Link based on action determined earlier
        ListIterator<Link> linkListIterator = network.getLinkIterator();
        while(linkListIterator.hasNext()) {
            Link link = linkListIterator.next();
            Agent bravo = link.getRightAgent();
            Agent charlie = link.getLeftAgent();
            if (bravo.getAction() == Action.COOPERATE) {
                if (charlie.getAction() == Action.COOPERATE) {
                    CC++;
                    bravo.updateLearner(charlie, rewardScheme.getR(), params.getAlpha());
                    charlie.updateLearner(bravo, rewardScheme.getR(), params.getAlpha());
                } else if (charlie.getAction() == Action.DEFECT) {
                    CD++;
                    bravo.updateLearner(charlie, rewardScheme.getT(), params.getAlpha());
                    charlie.updateLearner(bravo, rewardScheme.getS(), params.getAlpha());
                } else {
                    throw new IllegalStateException();
                }
            } else if (bravo.getAction() == Action.DEFECT) {
                if (charlie.getAction() == Action.COOPERATE) {
                    CD++;
                    bravo.updateLearner(charlie, rewardScheme.getS(), params.getAlpha());
                    charlie.updateLearner(bravo, rewardScheme.getT(), params.getAlpha());
                } else if (charlie.getAction() == Action.DEFECT) {
                    DD++;
                    bravo.updateLearner(charlie, rewardScheme.getP(), params.getAlpha());
                    charlie.updateLearner(bravo, rewardScheme.getP(), params.getAlpha());
                } else {
                    throw new IllegalStateException();
                }
            } else {
                throw new IllegalStateException();
            }
//            bravo.log(String.format("Opponent(%3d):%s, QC:%f, QD:%f", charlie.getId(), charlie.getAction(), bravo.neighbors.get(charlie).getCooperateScore(), bravo.neighbors.get(charlie).getDefectScore()));
//            charlie.log(String.format("Opponent(%3d):%s, QC:%f, QD:%f", bravo.getId(), bravo.getAction(), charlie.neighbors.get(bravo).getCooperateScore(), charlie.neighbors.get(bravo).getDefectScore()));
        }
        CCRate = (double)CC / network.linkCount();
    }
}
