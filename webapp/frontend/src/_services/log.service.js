import config from 'config';
import { requestUrl } from '_helpers';

export const logService = {
    findByRegexp,
    updateAgentEvents
};


function findByRegexp(agentTokenId, regexp) {
    return requestUrl(`${config.apiLogUrl}/rest/log/findByRegexp`, 'POST',  JSON.stringify({agentTokenId : agentTokenId, regexp : regexp, limitResults : 10}));
}

function updateAgentEvents(agentTokenId) {
    return requestUrl(`${config.apiLogUrl}/rest/log/updateAgentEvents`, 'POST',  JSON.stringify({agentTokenId : agentTokenId}));
}

