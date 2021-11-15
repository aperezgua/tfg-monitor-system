package edu.uoc.tfgmonitorsystem.usermicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserFilter implements Serializable {

    private static final long serialVersionUID = -985615854173568541L;

    private String name;

    private String email;

    private ActiveTypeFilter activeTypeFilter;

    public ActiveTypeFilter getActiveTypeFilter() {
        return activeTypeFilter;
    }

    public void setActiveTypeFilter(ActiveTypeFilter activeTypeFilter) {
        this.activeTypeFilter = activeTypeFilter;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
