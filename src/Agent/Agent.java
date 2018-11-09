package Agent;

import Action.*;

import java.util.*;

public class Agent {

    private int id;

    public Agent(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    private Hashtable<Agent, Decider> neighbors = new Hashtable<>();

    public void addNeighbor(Agent neighbor) {
        neighbors.put(neighbor, new Decider());
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

    public List<Decider> getAllDeciders() {
        return new ArrayList<>(neighbors.values());
    }

    private Decider getDecider(Agent agent) {
        return neighbors.get(agent);
    }

    // epsilon greedy selection of next action
    public Action getNextAction(Agent agent, double epsilon) {
        if (Math.random() < epsilon) {
            return Action.getRandomAction();
        } else {
            return getNextRationalAction(agent);
        }
    }

    private Action getNextRationalAction(Agent agent) {
        return this.getDecider(agent).getNextRationalAction();
    }

    public void updateDecider(Agent agent, Action action, double alpha) {
        this.getDecider(agent).update(action, alpha);
    }
}
