package Action;

import Constants.RewardScheme;

public class Action {

    public static Action getRandomAction() {
        if (Math.random() < 0.5) {
            return new Action(ActionType.COOPERATE);
        } else {
            return new Action(ActionType.DEFECT);
        }
    }

    public static void setResults(Action a1, Action a2, RewardScheme rewardScheme) {
        if (a1.type == ActionType.COOPERATE) {
            if (a2.type == ActionType.COOPERATE) {
                a1.result = rewardScheme.getR();
                a2.result = rewardScheme.getR();
            } else {
                a1.result = rewardScheme.getS();
                a2.result = rewardScheme.getT();
            }
        } else {
            if (a2.type == ActionType.COOPERATE) {
                a1.result = rewardScheme.getT();
                a2.result = rewardScheme.getS();
            } else {
                a1.result = rewardScheme.getP();
                a2.result = rewardScheme.getP();
            }
        }
    }

    private ActionType type;
    private int result;

    Action(ActionType type) {
        this.type = type;
    }

    int getResult() {
        return result;
    }

    public ActionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("(%s %d)", type, result);
    }
}