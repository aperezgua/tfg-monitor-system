package edu.uoc.tfgmonitorsystem.common.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class DbSequence {

    private int sequence;

    @Id
    private String sequenceName;

    public int getSequence() {
        return sequence;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

}
