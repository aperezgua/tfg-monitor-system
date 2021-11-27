package edu.uoc.tfgmonitorsystem.common.model.document;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Log extends BaseDocument {
    @Transient
    public static final String SEQUENCE_NAME = "log_sequence";

    @Id
    private Long id;

    @DBRef()
    private Agent agent;

    private Date date;

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
