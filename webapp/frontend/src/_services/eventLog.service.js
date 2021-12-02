import config from 'config';
import { requestUrl } from '_helpers';

export const eventLogService = {
    findLastLogEvents
};


function findLastLogEvents(filter) {
    return requestUrl(`${config.apiLogUrl}/rest/eventLog/findLastLogEvents`, 'POST',  JSON.stringify(filter));
}

