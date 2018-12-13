package Networks;

import Agent.*;
import Utility.RandomManager;

import java.util.*;

public class BANetwork extends Network{

    private int n; // agent count
    private int m; // agent count initial

    public BANetwork(int n, int m) {
        this.setNetworkType("Barabási–Albert");
        this.n = n;
        this.m = m;
    }

    public void initializeNetwork() {
        int agentId = 0;
        // creates initial table with m = this.agentCountInitial Agents
        while (this.agentCount() < this.m) {
            // Create New Agent
            Agent newbie = new Agent(agentId++);
            this.addAgent(newbie);
            // Link for initial network state
            ListIterator<Agent> iterator = getAgentIterator();
            while (iterator.hasNext()) {
                Agent connector = iterator.next();
                if (newbie.isLinkable(connector)) {
                    this.addLink(new Link(connector, newbie));
                }
            }
        }
        // System.out.print("Finished First Process\n");
        // adds agents until there are n = this.agentCount agents
        while (this.agentCount() < this.n) {
            Agent newbie = new Agent(agentId++);
            this.addAgent(newbie);
            int connected = 0;
            while (connected < this.m) {
                Agent connector = this.getAgentFromIndex((int) (RandomManager.nextDouble() * this.agentCount()));
                if (newbie.isLinkable(connector)) {
                    if (RandomManager.nextDouble() < (double) connector.neighborCount() / this.linkCount()) {
                        this.addLink(new Link(connector, newbie));
                        connected++;
                    }
                }
            }
        }
        this.sortAgents();
    }
}
