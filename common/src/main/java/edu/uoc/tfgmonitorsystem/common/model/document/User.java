package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa a un usuario de la aplicación.
 */
@Document
public class User {

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
    private String email;
    /**
     * Password encriptado.
     */
    private String encriptedPassword;
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

    public User(Integer id, String name, String email, String encriptedPassword, Date createdDate, Boolean active,
            Rol rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.encriptedPassword = encriptedPassword;
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

    public String getEncriptedPassword() {
        return encriptedPassword;
    }

    public void setEncriptedPassword(String encriptedPassword) {
        this.encriptedPassword = encriptedPassword;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
