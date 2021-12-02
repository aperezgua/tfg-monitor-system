package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EventLogFilter {
    private String agentTokenId;

    private Integer limitResults;

    public EventLogFilter() {
        super();
    }

    public EventLogFilter(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public String getAgentTokenId() {
        return agentTokenId;
    }

    public Integer getLimitResults() {
        return limitResults;
    }

    public void setAgentTokenId(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public void setLimitResults(Integer limitResults) {
        this.limitResults = limitResults;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
