package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EventLogFilter {
    private String agentTokenId;

    private Integer limitResults;

    private List<Integer> systemIds;

    private Integer lastTimeInSeconds;

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

    public void setSystemIds(List<Integer> systemIds) {
        this.systemIds = systemIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
