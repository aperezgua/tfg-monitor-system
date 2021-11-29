import config from 'config';
import { requestUrl } from '_helpers';

export const logService = {
    findByRegexp
};


function findByRegexp(agentTokenId, regexp) {
    return requestUrl(`${config.apiLogUrl}/rest/log/findByRegexp`, 'POST',  JSON.stringify({agentTokenId : agentTokenId, regexp : regexp}));
}
