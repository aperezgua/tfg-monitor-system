package edu.uoc.tfgmonitorsystem.common.model.document;

import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MonitorizableSystem {

    @Transient
    public static final String SEQUENCE_NAME = "monitorizablesystem_sequence";

    /**
     * Id del sistema.
     */
    @Id
    private Integer id;
    /**
     * Nombre del sistema.
     */
    @NotBlank(message = "Email is mandatory")
    private String name;

    @NotBlank(message = "Country is mandatory")
    private Country country;

    private Boolean active;

    public MonitorizableSystem() {
        super();
    }

    public MonitorizableSystem(Integer id, String name, Country country, Boolean active) {
        super();
        this.id = id;
        this.name = name;
        this.country = country;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public Country getCountry() {
        return country;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
