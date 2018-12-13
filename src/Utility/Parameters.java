package Utility;

public class Parameters {

    private int Q0;
    private double alpha;
    private double epsilon;

    public Parameters(int Q0, double alpha, double epsilon) {
        this.Q0 = Q0;
        this.alpha = alpha;
        this.epsilon = epsilon;
    }

    public int getQ0() {
        return Q0;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getEpsilon() {
        return epsilon;
    }
}
