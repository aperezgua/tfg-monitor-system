import config from 'config';
import { requestUrl } from '_helpers';

export const userService = {
    getAll,
    find,
    get,
    put
};

function getAll() {    
    return requestUrl(`${config.apiUserUrl}/rest/users/all`, 'GET');
}

function find(userFilter) {
    return requestUrl(`${config.apiUserUrl}/rest/users/find`, 'POST',  JSON.stringify(userFilter));
}

function put(user) {
    return requestUrl(`${config.apiUserUrl}/rest/users/put`, 'POST', JSON.stringify(user));
}

function get(id) {
    return requestUrl(`${config.apiUserUrl}/rest/users/get/${id}`, 'GET');
}