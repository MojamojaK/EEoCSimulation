package Agent;

import Action.*;
import Learner.Learner;
import Utility.RandomManager;

import java.util.*;

public class Agent {

    private int id; // unique ID of agent in network

    private Action action = Action.NULL;
    public Hashtable<Agent, Learner> neighbors = new Hashtable<>(); // ArrayList of neighbors

    public Agent(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public Action getAction() {
        return this.action;
    }

    private HashSet<Learner> getDeciders() {
        return new HashSet<Learner>(neighbors.values());
    }

    public void addNeighbor(Agent neighbor) {
        if (!isNeighbor(neighbor)) {
            neighbors.put(neighbor, new Learner());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int neighborCount() {
        return neighbors.size();
    }

    public boolean isNeighbor(Agent neighbor) {
        return neighbors.containsKey(neighbor);
    }

    public boolean isLinkable(Agent neighbor) {
        return (neighbor != this && !this.isNeighbor(neighbor));
    }

    public void removeNeighbor(Agent agent) {
        if (isNeighbor(agent)) {
            neighbors.remove(agent);
        }
    }

    // epsilon greedy selection of next action
    public void updateNextAction(double epsilon) {
        if (RandomManager.nextDouble() < epsilon) {
            action = Action.getRandom();
//            log(String.format("Action Epsilon: %s", action));
        } else {
            int C = 0, D = 0;
            for (Learner learner : this.getDeciders()) {
                Action candidateAction = learner.nextAction();
                if (candidateAction == Action.COOPERATE) C++;
                else if (candidateAction == Action.DEFECT) D++;
                else System.exit(1);
            }
            if (C > D) action = Action.COOPERATE;
            else if (C < D) action = Action.DEFECT;
            else action = Action.getRandom();
//            log(String.format("Action Rational(%2d:%2d): %s", C, D, action));
        }
    }

    public void log(String str) {
        if (this.getId() == 0) System.out.println(str);
    }

    public void initiateLearner(int Q0) {
        for (Learner learner : neighbors.values()) learner.initiate(Q0);
    }

    public void updateLearner(Agent opponent, int result, double alpha) {
        neighbors.get(opponent).update(action, result, alpha);
    }
}
