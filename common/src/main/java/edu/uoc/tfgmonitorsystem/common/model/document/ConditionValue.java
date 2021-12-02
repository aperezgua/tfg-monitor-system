package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConditionValue extends BaseDocument {

    /**
     * Valores acumulados en la condición
     */
    private List<Double> values;

    /**
     * Timestamps de los tiempos para saber que valores eliminar cada vez que se procesa el log.
     */
    private List<Long> times;

    /**
     * Si la condición ha sido satisfecha.
     */
    private boolean fullFilled;

    public ConditionValue() {
        super();
        values = new ArrayList<>();
        times = new ArrayList<>();
    }

    public double getAvgValue() {
        return getTotalValue() / values.size();
    }

    public List<Long> getTimes() {
        return times;
    }

    public double getTotalValue() {
        double sum = 0;
        for (Double value : values) {
            sum += value;
        }
        return sum;
    }

    public List<Double> getValues() {
        return values;
    }

    public boolean isFullFilled() {
        return fullFilled;
    }

    public void setFullFilled(boolean fullFilled) {
        this.fullFilled = fullFilled;
    }

    /**
     * Actualiza el valor de la condición de conteo según una fecha de log y el tiempo de la condición que la
     * representa.
     *
     * @param logDate       Fecha del log
     * @param timeInSeconds Tiempo máximo a tener en cuenta para los cálculos.
     */
    public void updateCountValue(Date logDate, Integer timeInSeconds) {
        this.updateValue(logDate, timeInSeconds, 1D);
    }

    /**
     * Actualiza el valor de la condición según una fecha de log y el tiempo de la condición que la representa.
     *
     * @param logDate       Fecha del log.
     * @param timeInSeconds Tiempo máximo a tener en cuenta para los cálculos.
     * @param value         Valor a introducir.
     */
    public void updateValue(Date logDate, Integer timeInSeconds, Double value) {
        clearOldValuesAndTimes(logDate, timeInSeconds);
        values.add(value);
        times.add(logDate.getTime());
    }

    /**
     * Elimina los datos de los tiempos/valores que son inferiores al log que se está agregando.
     *
     * @param logDate       fecha del log
     * @param timeInSeconds tiempo limite en segundos
     */
    private void clearOldValuesAndTimes(Date logDate, Integer timeInSeconds) {

        long cutTime = logDate.getTime() - timeInSeconds * 1000;

        List<Integer> indexToRemove = new ArrayList<>();
        int index = 0;
        for (Long time : times) {
            if (time <= cutTime) {
                indexToRemove.add(index++);
            }
        }
        for (int indexR : indexToRemove) {
            values.remove(indexR);
            times.remove(indexR);
        }
    }

}
