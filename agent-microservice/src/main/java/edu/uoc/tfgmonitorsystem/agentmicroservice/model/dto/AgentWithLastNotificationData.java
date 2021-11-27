package edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import java.util.Date;

public class AgentWithLastNotificationData extends Agent implements Comparable<AgentWithLastNotificationData> {

    private Long size;

    private Date lastNotification;

    public AgentWithLastNotificationData(Agent agent) {
        super(agent);
    }

    @Override
    public int compareTo(AgentWithLastNotificationData o) {
        long d2 = o.lastNotification != null ? o.lastNotification.getTime() : 0L;
        long d1 = this.lastNotification != null ? this.lastNotification.getTime() : 0L;

        int compare = Long.compare(d2, d1);

        if (compare == 0) {
            compare = Integer.compare(this.hashCode(), o.hashCode());
        }

        return compare;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Date getLastNotification() {
        return lastNotification;
    }

    public Long getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setLastNotification(Date lastNotification) {
        this.lastNotification = lastNotification;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
