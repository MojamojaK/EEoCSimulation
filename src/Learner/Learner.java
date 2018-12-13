package Learner;

import Action.Action;

import java.util.HashMap;

public class Learner {
    private boolean initiated;
    private HashMap<Action, Double> qValue;

    public Learner() {
        initiated = false;
        qValue = new HashMap<>();
    }

    public void initiate(int Q0) {
        try {
            this.setScore(Action.COOPERATE, Q0);
            this.setScore(Action.DEFECT, Q0);
            initiated = true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setScore(Action action, double q) throws IllegalAccessException {
        if ((qValue.containsKey(action) || !initiated)) {
            qValue.put(action, q);
        } else {
            throw new IllegalAccessException();
        }
    }

    public double getCooperateScore() {
        return qValue.get(Action.COOPERATE);
    }

    public double getDefectScore() {
        return qValue.get(Action.DEFECT);
    }

    public Action nextAction() {
        // Do majority voting (if tied chose random)
        if (getCooperateScore() > getDefectScore()) {
            return Action.COOPERATE;
        } else if (getCooperateScore() < getDefectScore()){
            return Action.DEFECT;
        } else {
            return Action.getRandom();
        }
    }

    public void update(Action action, int result, double alpha) {
        double q_new = (1.0 - alpha) * qValue.get(action) + alpha * result;
        qValue.put(action, q_new);
    }
}
