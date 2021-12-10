package edu.uoc.tfgmonitorsystem.common.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa a un usuario de la aplicación.
 */
@Document
public class User extends BaseDocument implements Credential {

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    /**
     * Si el usuario se encuentra activo.
     */
    private Boolean active;
    /**
     * Fecha de creación del usuario.
     */
    private Date createdDate;
    /**
     * Email del usuario.
     */
    @NotBlank(message = "user.email.mandatory")
    @Indexed(unique = true)
    private String email;

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
     * Password encriptado.
     */
    @NotBlank(message = "user.password.mandatory")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    /**
     * Rol de este usuario.
     */
    @NotNull(message = "user.rol.mandatory")
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

    @Override
    public Rol getRol() {
        return rol;
    }

    @Override
    public String getSubject() {
        return email;
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
