package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @DBRef()
    private Rule rule;

    /**
     * Fecha de inicio.
     */
    private Date initDate;

    private List<ConditionValue> conditionsValues;

    public EventLog() {
        super();
    }

    public EventLog(Agent agent, Rule rule) {
        this.agent = agent;
        this.rule = rule;
        this.conditionsValues = initConditionsValues(rule);
    }

    public int conditionsValuesSize() {
        return this.conditionsValues.size();
    }

    public Condition findConditionByIndex(int index) {
        return this.getRule().getConditions().get(index);
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
        return rule;
    }

    public Double getValue() {
        return value;
    }

    public boolean isDirectValue() {
        return CalculationType.DIRECT_VALUE.equals(rule.getCalculationType());
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
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
    private final List<ConditionValue> initConditionsValues(Rule rule) {
        List<ConditionValue> values = new ArrayList<>(rule.getConditions().size());
        for (int i = 0; i < rule.getConditions().size(); i++) {
            values.add(new ConditionValue());
        }
        return values;
    }

}
