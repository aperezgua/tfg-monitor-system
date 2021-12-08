import config from 'config';
import { requestUrl } from '_helpers';



export const eventLogService = {
    findLastLogEvents,
    findLogBySeverity,
    getEventFilter,
    saveEventFilter,
    findEventSummary,
    findEventsByRule
};


function findLastLogEvents(limitResults) {
    
    let eventFilter = getEventFilterToServer();
    
    eventFilter["limitResults"] = limitResults;
    
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(eventFilter));
}

function findEventSummary(severity) {
    
    let eventFilter = getEventFilterToServer();
    
    eventFilter["severity"] = severity;
    
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findEventSummary`, 'POST',  JSON.stringify(eventFilter));
}

function findLogBySeverity(severity) {
    
    let eventFilter = getEventFilterToServer();
    
    eventFilter["severity"] = severity;
    
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(eventFilter));
}

function findEventsByRule(agentTokenId, ruleName) {
    let eventFilter = getEventFilterToServer();
    
    eventFilter["agentTokenId"] = agentTokenId;
    eventFilter["ruleName"] = ruleName;
    
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(eventFilter));
}

/** Construye un objeto filter para enviar al servidor con el conjunto de ids de sistemas correcto */
function getEventFilterToServer() {
     let eventFilter = getEventFilter() ;
    let ids = [];
    if(eventFilter.systems && eventFilter.systems.length  > 0) {
        for(var i=0; i < eventFilter.systems.length; i++) {
            ids.push(eventFilter.systems[i].value);
        }
    }
    
    let filterToServer = {
        lastTimeInSeconds : eventFilter.lastTimeInSeconds,
        systemIds : ids,
    }
    return filterToServer;
}


/** obtiene un filtro de la memoria */
function getEventFilter() {
    //localStorage.removeItem('eventFilter')
    let eventFilter = JSON.parse(localStorage.getItem('eventFilter'));
    
    if(!eventFilter) {
        eventFilter = {
            systems : [],
            lastTimeInSeconds : 3600
        } 
    }
    
    
    return eventFilter;
}

/** Guarda un filtro en la memoria */
function saveEventFilter(filter) {
    localStorage.setItem('eventFilter', JSON.stringify(filter))
}
