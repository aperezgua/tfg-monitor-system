package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa a una entidad log.
 */
@Document
public class Log extends BaseDocument {

    /**
     * Identificador del nombre de secuencia usado.
     */
    @Transient
    public static final String SEQUENCE_NAME = "log_sequence";

    /**
     * Identificador autonumérico de la entidad.
     */
    @Id
    private Long id;

    /**
     * Referencia al agente al que pertenece este log.
     */
    @DBRef()
    private Agent agent;

    /**
     * Fecha de cuándo se produce el log.
     */
    private Date date;

    /**
     * Línea de log a guardar.
     */
    private String logLine;

    public Log() {
        super();
    }

    public Agent getAgent() {
        return agent;
    }

    public Date getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }

    public String getLogLine() {
        return logLine;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogLine(String logLine) {
        this.logLine = logLine;
    }

}
