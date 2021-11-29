import config from 'config';
import { requestUrl } from '_helpers';

export const agentService = {
    find,
    get,
    put,
    generateToken,
    findLastNotificationData
};


function find(agentFilter) {
    return requestUrl(`${config.apiAgentUrl}/rest/agents/find`, 'POST',  JSON.stringify(agentFilter));
}

function put(agent) {
    return requestUrl(`${config.apiAgentUrl}/rest/agents/put`, 'POST', JSON.stringify(agent));
}

function get(token) {
    return requestUrl(`${config.apiAgentUrl}/rest/agents/get/${token}`, 'GET');
}

function findLastNotificationData() {
    return requestUrl(`${config.apiAgentUrl}/rest/agents/findLastNotificationData`, 'GET');
}


function generateToken() {
    return requestUrl(`${config.apiAgentUrl}/rest/agents/generateToken`, 'GET');
}
