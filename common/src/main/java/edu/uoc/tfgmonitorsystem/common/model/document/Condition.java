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

    /**
     * Verifica que un valor procesado por regexp coincide.
     *
     * @param valueToMatch
     * @return
     */
    public boolean matchValue(Double valueToMatch) {
        switch (getComparationType()) {
        case AVG_GREATER_THAN:
        case GREATER_THAN:
            return valueToMatch > Double.valueOf(this.value);
        case AVG_LESS_THAN:
        case LESS_THAN:
            return valueToMatch < Double.valueOf(this.value);
        case CONTAINS:
        default:
            return false;
        }
    }

    /**
     * Verifica si una condición de String coincide con el valor
     *
     * @param valueToMatch
     * @return
     */
    public boolean matchValue(String valueToMatch) {
        switch (getComparationType()) {
        case CONTAINS:
            return valueToMatch.contains(value);
        case AVG_GREATER_THAN:
        case GREATER_THAN:
        case AVG_LESS_THAN:
        case LESS_THAN:
        default:
            return false;
        }
    }

    public boolean needAccumulatedAvgValue() {
        return comparationType.isAvgComparation();
    }

    public boolean needDoubleValueComparation() {
        return comparationType.isDoubleComparation();
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
