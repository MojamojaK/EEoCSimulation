package Utility;

import java.util.Random;

public class RandomManager {

    private static int seed = 10000;

    private static Random random = new Random(seed);

    public static int getSeed() {
        return seed;
    }

    public static void nextSeed() {
        seed += 1000;
        random = new Random(seed);
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static Random getRandomInstance() {
        return random;
    }

}
