package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.List;

public class Rule extends BaseDocument {

    private Boolean active;

    private CalculationType calculationType;

    private MatchType matchType;

    private String name;

    private String regularExpression;

    private Severity severity;

    private List<Condition> conditions;

    public Rule() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public CalculationType getCalculationType() {
        return calculationType;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public String getName() {
        return name;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCalculationType(CalculationType calculationType) {
        this.calculationType = calculationType;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
