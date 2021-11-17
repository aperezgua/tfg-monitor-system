package edu.uoc.tfgmonitorsystem.systemmicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SystemFilter implements Serializable {

    private static final long serialVersionUID = -985615854173568541L;

    private String name;

    private Integer countryId;

    private ActiveTypeFilter activeTypeFilter;

    public ActiveTypeFilter getActiveTypeFilter() {
        return activeTypeFilter;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public void setActiveTypeFilter(ActiveTypeFilter activeTypeFilter) {
        this.activeTypeFilter = activeTypeFilter;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
