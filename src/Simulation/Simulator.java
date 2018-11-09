package Simulation;

import Networks.*;
import Constants.*;

public class Simulator {

    private int roundCount;
    private RewardScheme rewardScheme;
    private Parameters params;
    private Network network;

    private Simulator(int roundCount, RewardScheme rewardScheme, Parameters params, Network network) {
        this.roundCount = roundCount;
        this.rewardScheme = rewardScheme;
        this.params = params;
        this.network = network;
        this.network.initializeQ(params.getQ0());
    }

    private void run() {
        for (int i = 0; i < this.roundCount; i++) {
            new Round(i, rewardScheme, params, network);
        }
    }

    public static void main(String[] args) {
        try {
            RewardScheme rewardScheme = new RewardScheme(5, 3, 1, 0);
            Parameters params = new Parameters(6, 0.1, 0.05);

            Network baNetwork = new BANetwork(1000, 5);
            baNetwork.printStats();

//            Network wsNetwork = new WSNetwork(1000, 10, 0.05);
//            wsNetwork.printStats();
//
//            Network erNetwork = new ERNetwork(1000, 0.01001);
//            erNetwork.printStats();

            Simulator simulation = new Simulator(100, rewardScheme, params, baNetwork);
            simulation.run();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
