import config from 'config';
import { authHeader, handleResponse } from '_helpers';

export const userService = {
    getAll,
    find,
    get,
    put
};

function getAll() {
    const requestOptions = { method: 'GET', headers: authHeader() };
    return fetch(`${config.apiUserUrl}/rest/users/all`, requestOptions).then(handleResponse);
}



function find(userFilter) {
    const requestOptions = {
        method: 'POST',
        headers: authHeader(),
        body: JSON.stringify(userFilter)
    };
    return fetch(`${config.apiUserUrl}/rest/users/find`, requestOptions).then(handleResponse);
}

function put(user) {
    const requestOptions = {
        method: 'POST',
        headers: authHeader(),
        body: JSON.stringify(user)
    };
    return fetch(`${config.apiUserUrl}/rest/users/put`, requestOptions).then(handleResponse);
}

function get(id) {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };
    return fetch(`${config.apiUserUrl}/rest/users/get/${id}`, requestOptions).then(handleResponse);
}