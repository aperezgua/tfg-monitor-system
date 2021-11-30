package edu.uoc.tfgmonitorsystem.common.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que gestiona la colección de secuencias de la base de datos.
 */
@Document()
public class DbSequence {

    /**
     * Contado de secuencia para el documento de MongoDB.
     */
    private long sequence;

    /**
     * Nombre de la colección.
     */
    @Id
    private String sequenceName;

    public long getSequence() {
        return sequence;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

}
