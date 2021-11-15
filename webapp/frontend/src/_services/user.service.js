import config from 'config';
import { authHeader, handleResponse } from '_helpers';

export const userService = {
    getAll,
    find
};

function getAll() {
    const requestOptions = { method: 'GET', headers: authHeader() };
    return fetch(`${config.apiUserUrl}/rest/users/all`, requestOptions).then(handleResponse);
}



function find(userFilter) {
    console.log("LALALA");
    
    const requestOptions = {
        method: 'POST',
        headers: authHeader(),
        body: JSON.stringify(userFilter)
    };
    return fetch(`${config.apiUserUrl}/rest/users/find`, requestOptions).then(handleResponse);
}