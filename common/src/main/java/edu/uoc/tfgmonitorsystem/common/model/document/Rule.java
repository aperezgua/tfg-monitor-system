package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Regla usada por el documento Agent.
 */
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

    @DBRef
    private Agent agent;

    public Rule() {
        super();
    }

    public Condition findConditionByIndex(int index) {
        return conditions.get(index);
    }

    public Boolean getActive() {
        return active;
    }

    public Agent getAgent() {
        return agent;
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

    /**
     * Devuelve si la condición coincide con el regexp de la regla.
     *
     * @param value
     * @return
     */
    public boolean matchRegegularExpression(String value) {
        return Pattern.matches(regularExpression, value);
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
