package Utility;

public class RewardScheme {
    private final int T;
    private final int R;
    private final int P;
    private final int S;

    public RewardScheme(int T, int R, int P, int S) throws IllegalArgumentException{
        this.T = T;
        this.R = R;
        this.P = P;
        this.S = S;

        if (!(this.T > this.R && this.R > this.P && this.P > this.S)) {
            throw new IllegalArgumentException("Must be T > R > P > S!");
        }
        if (!(2 * this.R > this.T + this.S)) {
            throw new IllegalArgumentException("Must be 2R > T + S!");
        }
    }

    public int getT() {
        return T;
    }

    public int getR() {
        return R;
    }

    public int getP() {
        return P;
    }

    public int getS() {
        return S;
    }
}
