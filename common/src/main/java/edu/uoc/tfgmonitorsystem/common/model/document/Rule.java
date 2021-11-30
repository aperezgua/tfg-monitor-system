package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Regla usada por el documento Agent.
 */
@Document
public class Rule extends BaseDocument {

    /**
     * Si la regla está activa o no.
     */
    private Boolean active;

    /**
     * Tipo de cálculo usado.
     */
    private CalculationType calculationType;

    /**
     * Tipo de coincidencia para la regla.
     */
    private MatchType matchType;

    /**
     * Nombre de la regla.
     */
    private String name;

    /**
     * Expresión regular.
     */
    private String regularExpression;

    /**
     * Nivel de la regla.
     */
    private Severity severity;

    /**
     * Conjunto de condiciones que forman la regla.
     */
    private List<Condition> conditions;

    public Rule() {
        super();
    }

    public Condition findConditionByIndex(int index) {
        return conditions.get(index);
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
