package edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AgentFilter {
    private String name;

    private String systemName;

    private ActiveTypeFilter activeTypeFilter;

    public ActiveTypeFilter getActiveTypeFilter() {
        return activeTypeFilter;
    }

    public String getName() {
        return name;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setActiveTypeFilter(ActiveTypeFilter activeTypeFilter) {
        this.activeTypeFilter = activeTypeFilter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
