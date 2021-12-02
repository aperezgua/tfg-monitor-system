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
     * Valor
     */
    private Double value;

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
                for (ConditionValue conditionValue : conditionsValues) {
                    fullFilled &= conditionValue.isFullFilled();
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

    public String getRuleName() {
        return ruleName;
    }

    public Double getValue() {
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

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setValue(Double value) {
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
