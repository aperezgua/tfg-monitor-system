import config from 'config';
import { requestUrl } from '_helpers';



export const eventLogService = {
    findLastLogEvents,
    getEventFilter,
    saveEventFilter
};


function findLastLogEvents(limitResults) {
    
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
        limitResults : limitResults
    }
    
    eventFilter["limitResults"] = limitResults;
    
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(filterToServer));
}

/** obtiene un filtro de la memoria */
function getEventFilter() {
    //localStorage.removeItem('eventFilter')
    let eventFilter = JSON.parse(localStorage.getItem('eventFilter'));
    
    if(!eventFilter) {
        eventFilter = {
            systems : [],
            lastTimeInSeconds : null
        } 
    }
    
    
    return eventFilter;
}

/** Guarda un filtro en la memoria */
function saveEventFilter(filter) {
    localStorage.setItem('eventFilter', JSON.stringify(filter))
}
