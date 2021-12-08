package edu.uoc.tfgmonitorsystem.log4j.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class AgentAppender extends AppenderSkeleton {

    private String authenticationUrl;
    private String putLogUrl;
    private String agentToken;

    public AgentAppender() {
        super();
    }

    @Override
    public void close() {

    }

    public String getAgentToken() {
        return agentToken;
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    public String getPutLogUrl() {
        return putLogUrl;
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public void setAgentToken(String agentToken) {
        this.agentToken = agentToken;
    }

    public void setAuthenticationUrl(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }

    public void setPutLogUrl(String putLogUrl) {
        this.putLogUrl = putLogUrl;
    }

    @Override
    protected void append(LoggingEvent event) {

        Log4jWorker.getInstance().addLogToSend(authenticationUrl, putLogUrl, agentToken, event.getRenderedMessage());
    }

}
