package Networks;

import Action.ActionType;
import Action.Decider;
import Agent.Agent;

import java.util.*;

public abstract class Network {

    private String networkType = "";
    private List<Agent> agents = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    abstract void initializeNetwork();

    void setNetworkType(String str) {
        networkType = str;
    }

    void addAgent(Agent agent) {
        agents.add(agent);
    }

    void addLink(Link link) {
        links.add(link);
    }

    void sortAgents() {
        agents.sort(Comparator.comparingInt(Agent::neighborCount));
        Collections.reverse(agents);
    }

    int agentCount() {
        return agents.size();
    }

    int linkCount() {
        return links.size();
    }

    ListIterator<Agent> getAgentIterator() {
        return agents.listIterator();
    }

    public ListIterator<Link> getLinkIterator() {
        return links.listIterator();
    }

    Agent getAgentFromIndex(int index) {
        return agents.get(index);
    }

    Link getLink(Agent bravo, Agent charlie) throws NoSuchElementException{
        for (Link link: links) {
            if ((link.node[0] == bravo && link.node[1] == charlie)
                    || (link.node[0] == charlie && link.node[1] == bravo)) {
                return link;
            }
        }
        throw new NoSuchElementException(
                String.format("No Link between %d and %d found", bravo.getId(), charlie.getId()));
    }

    public void initializeQ(int Q0) {
        for (Agent agent: agents) {
            for (Decider decider: agent.getAllDeciders()) {
                decider.setScore(ActionType.COOPERATE, Q0);
                decider.setScore(ActionType.DEFECT, Q0);
            }
        }
    }

    // makes array lists read-only
    void protect() {
        agents = Collections.unmodifiableList(agents);
        links = Collections.unmodifiableList(links);
    }

    // prints statistics of created network
    public void printStats() {
        System.out.println("Model Type: " + networkType);
        System.out.println(String.format("Agents: %d, Links: %d", agents.size(), links.size()));
        int neighbors = -1;
        int count = 1;
        for (Agent agent: agents) {
            if (agent.neighborCount() != neighbors) {
                if (neighbors != -1) {
                    System.out.println(String.format("%4d agents have %3d neighbors.", count, neighbors));
                }
                neighbors = agent.neighborCount();
                count = 1;
            } else {
                count++;
            }
        }
        System.out.println(String.format("%4d agents have %3d neighbors.", count, neighbors));
        System.out.println();
    }
}
