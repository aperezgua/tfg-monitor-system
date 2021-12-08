package edu.uoc.tfgmonitorsystem.log4j.appender;

public class AgentLineLog {

    private final String authenticationUrl;

    private final String putLogUrl;

    private final String agentTokenId;

    private final String logLine;

    public AgentLineLog(String authenticationUrl, String putLogUrl, String agentTokenId, String logLine) {
        this.authenticationUrl = authenticationUrl;
        this.putLogUrl = putLogUrl;
        this.agentTokenId = agentTokenId;
        this.logLine = logLine;
    }

    public String getAgentTokenId() {
        return agentTokenId;
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    public String getLogLine() {
        return logLine;
    }

    public String getPutLogUrl() {
        return putLogUrl;
    }

    @Override
    public String toString() {
        return "agentTokenId=" + agentTokenId + ", logLine=" + logLine;
    }
}
