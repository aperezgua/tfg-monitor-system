package edu.uoc.tfgmonitorsystem.common.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa a una entidad país en el aplicativo.
 */
@Document
public class Country extends BaseDocument {

    /**
     * Id del país.
     */
    @Id
    private Integer id;
    /**
     * Nombre del país.
     */
    private String name;

    public Country() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
