package Action;

public enum ActionType {
    COOPERATE,
    DEFECT;

    @Override
    public String toString() {
        if (this == COOPERATE) {
            return "C";
        } else {
            return "D";
        }
    }
}
