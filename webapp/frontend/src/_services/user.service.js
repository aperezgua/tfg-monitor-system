import config from 'config';
import { requestUrl } from '_helpers';

export const userService = {
    find,
    get,
    put
};


function find(userFilter) {
    return requestUrl(`${config.apiUserUrl}/rest/users/find`, 'POST',  JSON.stringify(userFilter));
}

function put(user) {
    return requestUrl(`${config.apiUserUrl}/rest/users/put`, 'POST', JSON.stringify(user));
}

function get(id) {
    return requestUrl(`${config.apiUserUrl}/rest/users/get/${id}`, 'GET');
}