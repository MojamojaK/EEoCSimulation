package Networks;

import Agent.Agent;

public class Link {

    Agent[] node = new Agent[2];

    Link(Agent bravo, Agent charlie) throws InstantiationError {
        if (bravo == charlie) {
            throw new InstantiationError("Self-linking agents (" + bravo + ", " + charlie + ") not allowed");
        } else if (bravo.isNeighbor(charlie)) {
            throw new InstantiationError("Double-linking (" + bravo + ", " + charlie + ") not allowed");
        } else {
            // System.out.print("Connected: " + bravo + " and " + charlie + "\n");
            this.node[0] = bravo;
            this.node[1] = charlie;
            bravo.addNeighbor(charlie);
            charlie.addNeighbor(bravo);
        }
    }

    public Agent getRightAgent() {
        return node[0];
    }

    public Agent getLeftAgent() {
        return node[1];
    }
}
