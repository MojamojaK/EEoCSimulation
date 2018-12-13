package Simulation;

import Graph.MRateGraph;
import Networks.*;
import Utility.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Simulator  {

    private static int roundCount = 0;
    private static int attemptCount = 0;
    private static ArrayList<Simulator> simulators;

    private static void setRoundCount(int roundCount) {
        Simulator.roundCount = roundCount;
    }

    public static int getRoundCount() {
        return roundCount;
    }

    private static void setAttemptCount(int attemptCount) {
        Simulator.attemptCount = attemptCount;
    }

    public static int getAttemptCount() {
        return attemptCount;
    }

    public static void run(RewardScheme rewardScheme, Parameters params, Network network) {
        simulators = new ArrayList<>();
        for (int i = 0; i < getAttemptCount(); i++) {
            network.reset();
            simulators.add(new Simulator(rewardScheme, params, network));
            RandomManager.nextSeed();
            System.out.printf("%3d%%\n", (int)((float)(i+1) / Simulator.getAttemptCount() * 100));
        }
    }

    private int seed = RandomManager.getSeed();
    private HashMap<Integer, Double> mRate = new HashMap<>();

    public Simulator( RewardScheme rewardScheme, Parameters params, Network network) {
        network.initializeQ(params.getQ0());
        for (int i = 0; i < Simulator.getRoundCount(); i++) {
            Round round = new Round(i, rewardScheme, params, network);
            mRate.put(round.getID(), round.getCCRate());
        }
    }

    public HashMap<Integer, Double> getMRate() {
        return mRate;
    }

    public static void main(String[] args) {
        try {
            RewardScheme rewardScheme = new RewardScheme(5, 3, 1, 0);
            Parameters params = new Parameters(4, 0.3, 0.05);

//            Network baNetwork = new BANetwork(1000, 5);
//            Network wsNetwork = new WSNetwork(1000, 10, 0.05);
            Network erNetwork = new ERNetwork(200, 0.01001);

            Simulator.setAttemptCount(50);
            Simulator.setRoundCount(100);
            Simulator.run(rewardScheme, params, erNetwork);

            MRateGraph mRateGraph = new MRateGraph(Simulator.simulators);

        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
