package Action;

import java.util.HashMap;

public class Decider {
    private HashMap<ActionType, Double> qHashMap;

    public Decider() {
        qHashMap = new HashMap<>();
    }

    public void setScore(ActionType action, double q) {
        qHashMap.put(action, q);
    }

    private double getCooperateScore() {
        return qHashMap.get(ActionType.COOPERATE);
    }

    private double getDefectScore() {
        return qHashMap.get(ActionType.DEFECT);
    }

    public Action getNextRationalAction() {
        if (getCooperateScore() > getDefectScore()) {
            return new Action(ActionType.COOPERATE);
        } else if (getCooperateScore() < getDefectScore()){
            return new Action(ActionType.DEFECT);
        } else {
            if (Math.random() < 0.5) {
                return new Action(ActionType.COOPERATE);
            } else {
                return new Action(ActionType.DEFECT);
            }
        }
    }

    public void update(Action action, double alpha) {
        qHashMap.put(action.getType(), (1 - alpha) * qHashMap.get(action.getType()) + alpha * action.getResult());
    }
}
