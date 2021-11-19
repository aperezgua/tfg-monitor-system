package edu.uoc.tfgmonitorsystem.common.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa a un usuario de la aplicación.
 */
@Document
public class User extends BaseDocument {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    /**
     * Id del usuario.
     */
    @Id
    private Integer id;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Email del usuario.
     */
    @NotBlank(message = "user.email.mandatory")
    @Indexed(unique = true)
    private String email;

    /**
     * Password encriptado.
     */
    @NotBlank(message = "user.password.mandatory")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    /**
     * Fecha de creación del usuario.
     */
    private Date createdDate;
    /**
     * Si el usuario se encuentra activo.
     */
    private Boolean active;

    /**
     * Rol de este usuario.
     */
    private Rol rol;

    public User() {
        super();
    }

    public Boolean getActive() {
        return active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
