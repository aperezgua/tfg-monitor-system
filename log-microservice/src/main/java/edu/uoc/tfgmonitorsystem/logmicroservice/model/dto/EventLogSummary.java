package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.document.Severity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EventLogSummary {

    private Severity severity;

    private Integer number;

    private Integer periodInSeconds;

    private Integer increment;

    public EventLogSummary() {
        super();
    }

    public Integer getIncrement() {
        return increment;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getPeriodInSeconds() {
        return periodInSeconds;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setPeriodInSeconds(Integer periodInSeconds) {
        this.periodInSeconds = periodInSeconds;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
