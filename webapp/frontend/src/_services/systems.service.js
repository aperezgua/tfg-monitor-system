import config from 'config';
import { requestUrl } from '_helpers';

export const systemsService = {
    find,
    get,
    put,
    getCountries
};


function find(systemFilter) {
    return requestUrl(`${config.apiSystemUrl}/rest/systems/find`, 'POST',  JSON.stringify(systemFilter));
}

function put(system) {
    return requestUrl(`${config.apiSystemUrl}/rest/systems/put`, 'POST', JSON.stringify(system));
}

function get(id) {
    return requestUrl(`${config.apiSystemUrl}/rest/systems/get/${id}`, 'GET');
}

function getCountries() {
    return requestUrl(`${config.apiSystemUrl}/rest/countries/all`, 'GET');
}