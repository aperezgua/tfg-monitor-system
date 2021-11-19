package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Agent extends BaseDocument {

    /**
     * Id del sistema.
     */
    @Id
    private String token;
    /**
     * Nombre del sistema.
     */
    @NotBlank(message = "agent.name.mandatory")
    private String name;

    @DBRef()
    private Systems systems;

    /**
     * Fecha de creación del usuario.
     */
    private Date createdDate;

    private Boolean active;

    public Agent() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    public Systems getSystems() {
        return systems;
    }

    public String getToken() {
        return token;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSystems(Systems systems) {
        this.systems = systems;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
