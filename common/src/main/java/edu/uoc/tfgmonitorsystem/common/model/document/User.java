package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa a un usuario de la aplicación.
 */
@Document
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

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
    @NotBlank(message = "Email is mandatory")
    private String email;

    /**
     * Password encriptado.
     */
    @NotBlank(message = "Password is mandatory")
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

    public User(Integer id, String name, String email, String password, Date createdDate, Boolean active, Rol rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
        this.active = active;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
