package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Filtro para buscar elementos de log.
 */
public class AgentLogFilter {

    @NotBlank(message = "agentLogFilter.agentTokenId.mandatory")
    private String agentTokenId;

    private String regexp;

    private Integer limitResults;

    public AgentLogFilter() {
        super();
    }

    public AgentLogFilter(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public String getAgentTokenId() {
        return agentTokenId;
    }

    public Integer getLimitResults() {
        return limitResults;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setAgentTokenId(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public void setLimitResults(Integer limitResults) {
        this.limitResults = limitResults;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
