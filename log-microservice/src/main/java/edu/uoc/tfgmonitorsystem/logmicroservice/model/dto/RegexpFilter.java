package edu.uoc.tfgmonitorsystem.logmicroservice.model.dto;

import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RegexpFilter {

    @NotBlank(message = "regexpFilter.agentTokenId.mandatory")
    private String agentTokenId;

    @NotBlank(message = "regexpFilter.regexp.mandatory")
    private String regexp;

    public String getAgentTokenId() {
        return agentTokenId;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setAgentTokenId(String agentTokenId) {
        this.agentTokenId = agentTokenId;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
