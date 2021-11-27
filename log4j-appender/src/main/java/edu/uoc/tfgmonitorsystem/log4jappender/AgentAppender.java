package edu.uoc.tfgmonitorsystem.log4jappender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class AgentAppender extends AppenderSkeleton {

    private static final Logger LOGGER = Logger.getLogger(AgentAppender.class);

    private String authenticationUrl;
    private String putLogUrl;
    private String agentToken;

    private HttpClient httpClient;

    public AgentAppender() {
        super();
    }

    @Override
    public void close() {
        httpClient = null;
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
        if (httpClient == null) {

            httpClient = new HttpClient(authenticationUrl, putLogUrl, agentToken);
        }

        httpClient.putLogAndRetry(event.getRenderedMessage());

    }

}
