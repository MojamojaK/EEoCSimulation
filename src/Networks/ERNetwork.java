package Networks;

import Agent.Agent;

import java.util.ListIterator;

public class ERNetwork extends Network{

    private int n; // agent count
    private double p; // edge selection probability

    public ERNetwork (int n, double p) {
        this.setNetworkType("Erdős–Rényi");
        this.n = n;
        this.p = p;
        if (this.p < 0 || 1 < this.p) {
            throw new IllegalArgumentException("p must be 0 <= p <= 1 !");
        }
        initializeNetwork();
        protect();
    }

    public void initializeNetwork() {
        int agentId = 0;
        while (this.agentCount() < this.n) {
            this.addAgent(new Agent(agentId++));
        }
        ListIterator<Agent> iterator_b = getAgentIterator();
        while (iterator_b.hasNext()) {
            Agent bravo = iterator_b.next();
            ListIterator<Agent> iterator_c = getAgentIterator();
            while (iterator_c.hasNext()) {
                Agent charlie = iterator_c.next();
                if (bravo.isLinkable(charlie)) {
                    if (Math.random() < this.p) {
                        this.addLink(new Link(bravo, charlie));
                    }
                }
            }
        }
        this.sortAgents();
    }
}
