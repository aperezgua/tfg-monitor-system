package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;

@Document
public class EventLog extends BaseDocument {

    /**
     * String con el identificador de la secuencia.
     */
    @Transient
    public static final String SEQUENCE_NAME = "event_log_sequence";

    /**
     * Id autonumérico del evento producido.
     */
    @Id
    private Integer id;

    /**
     * Fecha en la que se crea el evento.
     */
    private Date date;

    /**
     * Valor que produce la alarma.
     */
    private String value;

    /**
     * Valor calculado para la alarma.
     */
    private Double ruleDoubleValue;

    /**
     * Agente sobre el cual se produce el evento.
     */
    @DBRef()
    private Agent agent;

    /**
     * Regla que usa para el evento.
     */
    private String ruleName;

    /**
     * Nivel de criticidad del evento generado.
     */
    private Severity severity;

    /**
     * Fecha de inicio.
     */
    private Date initDate;

    /**
     * Conjunto de valores de las condiciones de la regla.
     */
    private List<ConditionValue> conditionsValues;

    private boolean fullFilled;

    public EventLog() {
        super();
    }

    public EventLog(Agent agent, String ruleName) {
        this.agent = agent;
        this.ruleName = ruleName;
        this.conditionsValues = initConditionsValues();
        this.severity = getRule().getSeverity();
    }

    /**
     * Comprueba que una fecha se encuentra fuera del rango para considerear el tiempo mínimo para que un evento se
     * cumpla.
     *
     * @param timeInSeconds
     * @param dateToCheck
     * @return
     */
    public boolean checkMinTime(Integer timeInSeconds, Date dateToCheck) {

        return dateToCheck.getTime() > initDate.getTime() + timeInSeconds * 1000L;

    }

    /**
     * Calcula si el evento ha sido satisfecho dependiendo de la configuración de la regla y si deben coincidir todas
     * las condiciones o sólo una.
     *
     * @return True si se cumplen las condiciones del evento.
     */
    public boolean computeFullFilled() {
        Rule rule = agent.findRuleByName(ruleName);
        if (rule != null) {
            if (MatchType.ANY.equals(rule.getMatchType())) {
                fullFilled = false;
                for (ConditionValue conditionValue : conditionsValues) {
                    if (conditionValue.isFullFilled()) {
                        fullFilled = true;
                        break;
                    }
                }
            } else {
                fullFilled = true;
                for (int i = 0; i < conditionsValues.size() && fullFilled; i++) {
                    ConditionValue conditionValue = conditionsValues.get(i);
                    fullFilled = conditionValue.isFullFilled();
                }
            }
        } else {
            fullFilled = false;
        }
        return fullFilled;
    }

    public int conditionsValuesSize() {
        return this.conditionsValues.size();
    }

    public Condition findConditionByIndex(int index) {
        Rule rule = agent.findRuleByName(ruleName);
        return rule != null ? rule.getConditions().get(index) : null;
    }

    public ConditionValue findConditionValueByIndex(int index) {
        return conditionsValues.get(index);
    }

    public Agent getAgent() {
        return agent;
    }

    public List<ConditionValue> getConditionsValues() {
        return conditionsValues;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public Date getInitDate() {
        return initDate;
    }

    public Rule getRule() {
        return agent.findRuleByName(ruleName);
    }

    public Double getRuleDoubleValue() {
        return ruleDoubleValue;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getValue() {
        return value;
    }

    /**
     * Método que nos dice si el evento es de valor directo o no.
     *
     * @return True si el evento es de valor directo.
     */
    public boolean isDirectValue() {
        Rule rule = agent.findRuleByName(ruleName);
        return rule != null && CalculationType.DIRECT_VALUE.equals(rule.getCalculationType());
    }

    public boolean isFullFilled() {
        return fullFilled;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setConditionsValues(List<ConditionValue> conditionsValues) {
        this.conditionsValues = conditionsValues;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFullFilled(boolean fullFilled) {
        this.fullFilled = fullFilled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public void setRuleDoubleValue(Double ruleDoubleValue) {
        this.ruleDoubleValue = ruleDoubleValue;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Inicializa los valores de las reglas para ser usados por el algoritmo de cálculo de eventos
     *
     * @param rule Regla que va a inicializar los valores
     * @return List con el listado inicializado.
     */
    private final List<ConditionValue> initConditionsValues() {
        Rule rule = agent.findRuleByName(ruleName);
        List<ConditionValue> values = new ArrayList<>();
        if (rule != null && !CollectionUtils.isEmpty(rule.getConditions())) {
            for (int i = 0; i < rule.getConditions().size(); i++) {
                values.add(new ConditionValue());
            }
        }
        return values;
    }
}
