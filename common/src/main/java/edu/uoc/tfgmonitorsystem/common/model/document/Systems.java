package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Sistemas
 */
@Document
public class Systems extends BaseDocument {

    @Transient
    public static final String SEQUENCE_NAME = "systems_sequence";

    private Boolean active;
    @DBRef()
    private Country country;

    private Date createdDate;

    /**
     * Id del sistema.
     */
    @Id
    private Integer id;

    /**
     * Nombre del sistema.
     */
    @NotBlank(message = "systems.name.mandatory")
    private String name;

    public Systems() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public Country getCountry() {
        return country;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
