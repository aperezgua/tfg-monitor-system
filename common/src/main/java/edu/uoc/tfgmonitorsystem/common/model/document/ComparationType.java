package edu.uoc.tfgmonitorsystem.common.model.document;

/**
 * Tipo de comparación de la regla.
 */
public enum ComparationType {
    CONTAINS, GREATER_THAN, LESS_THAN, AVG_GREATER_THAN, AVG_LESS_THAN;

    public boolean isAvgComparation() {
        return AVG_GREATER_THAN.equals(this) || AVG_LESS_THAN.equals(this);
    }

    public boolean isDoubleComparation() {
        return AVG_GREATER_THAN.equals(this) || AVG_LESS_THAN.equals(this) || LESS_THAN.equals(this)
                || GREATER_THAN.equals(this);
    }
}
