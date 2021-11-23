package edu.uoc.tfgmonitorsystem.common.model.document;

/**
 * Condición de una regla.
 */
public class Condition {

    /**
     * Tipo de comparación a usar en la condición.
     */
    private ComparationType comparationType;

    /**
     * Valor a usar en la comparación.
     */
    private String value;

    /**
     * Tiempo en segundos a usar en la condición para realizar la comparativa.
     */
    private Integer time;

    public Condition() {
        super();
    }

    public ComparationType getComparationType() {
        return comparationType;
    }

    public Integer getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }

    public void setComparationType(ComparationType comparationType) {
        this.comparationType = comparationType;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
