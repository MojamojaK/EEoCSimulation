package Networks;

import Agent.Agent;
import Utility.RandomManager;

import java.util.ArrayList;

public class WSNetwork extends Network{

    private int n; // agent count
    private int m; // mean degree
    private double r; // rewiring probability


    public WSNetwork(int n, int m, double r) throws IllegalArgumentException {
        this.setNetworkType("Watts–Strogatz");
        this.n = n;
        this.m = m;
        this.r = r;
        if (this.r < 0 || 1 < this.r) {
            throw new IllegalArgumentException("r must be 0 <= r <= 1 !");
        }
    }

    public void initializeNetwork() {
        int agentId = 0;
        ArrayList<Link> rightLinks = new ArrayList<>();
        // create n agents;
        while (this.agentCount() < this.n) {
            this.addAgent(new Agent(agentId++));
        }
        // link adjacent m agents (floor(m/2) to the left, ceil(m/2) to the right)
        for (int i = 0; i < this.agentCount(); i++) {
            Agent bravo = this.getAgentFromIndex(i);
            int right_lim = i + ((m%2==1)?m/2+1:m/2) + 1;
            for (int jt = i - m/2; jt < right_lim; jt++) {
                int j = (jt + n) % n; // wrap j value
                Agent charlie = this.getAgentFromIndex(j);
                if (bravo.isLinkable(charlie)) {
                    Link link = new Link(bravo, charlie);
                    this.addLink(link);
                    if (i < jt) rightLinks.add(link);
                } else if (i < jt) {
                    if (bravo.isNeighbor(charlie)) {
                        // switch node index for right side determination to work properly
                        // System.out.print("Switching sides for " + bravo + ", " + charlie + "\n");
                        Link link = getLink(bravo, charlie);
                        link.swapAgentIndex();
                        rightLinks.add(link);
                    }
                }
            }
        }
        // rewire right-side-links
        for (Link link: rightLinks) {
            if (RandomManager.nextDouble() < this.r) {
                Agent bravo = link.node[0];
                Agent charlie;
                do {
                    charlie = this.getAgentFromIndex((int)(RandomManager.nextDouble() * this.agentCount()));
                } while (!bravo.isLinkable(charlie));
                Agent old = link.node[1];
                old.removeNeighbor(bravo);
                bravo.removeNeighbor(old);
                link.node[1] = charlie;
                charlie.addNeighbor(bravo);
                bravo.addNeighbor(charlie);
                // System.out.print("Rewired: (" + bravo + ", " + old + ") -> (" + bravo + ", " + charlie + ")\n");
            }
        }
        this.sortAgents();
    }
}
