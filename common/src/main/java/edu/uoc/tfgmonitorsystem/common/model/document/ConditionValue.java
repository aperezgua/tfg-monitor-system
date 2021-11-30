package edu.uoc.tfgmonitorsystem.common.model.document;

public class ConditionValue extends BaseDocument {

    private Double accumulatedValue;

    private Boolean fullFilled;

    public ConditionValue() {
        super();
    }

    public void addAvgValue(Double value) {
        if (this.accumulatedValue == null) {
            accumulatedValue = value;
        } else {
            accumulatedValue = (accumulatedValue + value) / 2;
        }
    }

    public Double getAccumulatedValue() {
        return accumulatedValue;
    }

    public Boolean getFullFilled() {
        return fullFilled;
    }

    public boolean isFullFilled() {
        return fullFilled != null && fullFilled;
    }

    public void setAccumulatedValue(Double accumulatedValue) {
        this.accumulatedValue = accumulatedValue;
    }

    public void setFullFilled(Boolean fullFilled) {
        this.fullFilled = fullFilled;
    }

}
