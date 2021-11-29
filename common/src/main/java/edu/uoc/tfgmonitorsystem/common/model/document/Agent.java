package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Agent extends BaseDocument implements Credential {

    private Boolean active;
    /**
     * Fecha de creación del usuario.
     */
    private Date createdDate;

    /**
     * Nombre del sistema.
     */
    @NotBlank(message = "agent.name.mandatory")
    private String name;

    @DBRef()
    private Systems systems;

    /**
     * Id del sistema.
     */
    @Id
    private String token;

    private List<Rule> rules;

    public Agent() {
        super();
    }

    public Agent(Agent agent) {
        this.active = agent.active;
        this.token = agent.token;
        this.systems = agent.systems;
        this.name = agent.name;
        this.createdDate = agent.createdDate;
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

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public String getSubject() {
        return token;
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

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void setSystems(Systems systems) {
        this.systems = systems;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
