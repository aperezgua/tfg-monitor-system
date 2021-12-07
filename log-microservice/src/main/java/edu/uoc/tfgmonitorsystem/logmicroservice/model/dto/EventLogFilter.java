package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.document.Severity;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Filtro para buscar eventos de log.
 */
public class EventLogFilter {

    /**
     * Id del token de agente que va a ser usado para buscar eventos.
     */
    private String agentTokenId;

    /**
     * Nº de registros a consultar como máximo.
     */
    private Integer limitResults;

    /**
     * Conjunto de ids de los sistemas a filtrar eventos de log.
     */
    private List<Integer> systemIds;

    /**
     * N segundos sobre los que consultar los eventos de log.
     */
    private Integer lastTimeInSeconds;

    /**
     * Nivel de criticidad del evento.
     */
    private Severity severity;

    /**
     * Nombre de la regla que se va a filtrar.
     */
    private String ruleName;

    public EventLogFilter() {
        super();
    }

    public EventLogFilter(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public String getAgentTokenId() {
        return agentTokenId;
    }

    public Integer getLastTimeInSeconds() {
        return lastTimeInSeconds;
    }

    public Integer getLimitResults() {
        return limitResults;
    }

    public String getRuleName() {
        return ruleName;
    }

    public Severity getSeverity() {
        return severity;
    }

    public List<Integer> getSystemIds() {
        return systemIds;
    }

    public void setAgentTokenId(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public void setLastTimeInSeconds(Integer lastTimeInSeconds) {
        this.lastTimeInSeconds = lastTimeInSeconds;
    }

    public void setLimitResults(Integer limitResults) {
        this.limitResults = limitResults;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setSystemIds(List<Integer> systemIds) {
        this.systemIds = systemIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
