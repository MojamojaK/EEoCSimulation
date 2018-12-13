package Action;

import Utility.RandomManager;

public enum Action {
    COOPERATE,
    DEFECT,
    NULL;

    @Override
    public String toString() {
        if (this == COOPERATE) {
            return "C";
        } else {
            return "D";
        }
    }

    public static Action getRandom() {
        if (RandomManager.nextDouble() < 0.5) {
            return COOPERATE;
        } else {
            return DEFECT;
        }
    }
}
