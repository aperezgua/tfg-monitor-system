import config from 'config';
import { requestUrl } from '_helpers';



export const eventLogService = {
    findLastLogEvents,
    getEventFilter,
    saveEventFilter
};


function findLastLogEvents(filter) {
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(filter));
}

/** obtiene un filtro de la memoria */
function getEventFilter() {
    let eventFilter = JSON.parse(localStorage.getItem('eventFilter'));
    if(!eventFilter) {
        eventFilter = {
            systemIds : [],
            lastTimeInSeconds : null
        } 
    }
    return eventFilter;
}

/** Guarda un filtro en la memoria */
function saveEventFilter(filter) {
    localStorage.setItem('eventFilter', JSON.stringify(filter))
}
