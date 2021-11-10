import { BehaviorSubject } from 'rxjs';
import config from 'config';
import { handleResponse } from '_helpers';

const currentTokenSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('jwtToken')));

export const authenticationService = {
    login,
    logout,
    currentToken: currentTokenSubject.asObservable(),
    get currentTokenValue () { return currentTokenSubject.value }
};

/**
 * Se hace login. En caso de producirse un error el handleResponse lo manejará, si se produce un error de conexión se 
 * delega en error.
 */
function login(username, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    };
    
    
    return fetch(`${config.apiAuthUrl}/authenticate`, requestOptions)
        .then(handleResponse)
        .then(token => {
            localStorage.setItem('jwtToken', JSON.stringify(token));
            currentTokenSubject.next(token);
            return token;
        });
}

/**
 */
function logout() {    
    localStorage.removeItem('jwtToken');
    currentTokenSubject.next(null);
}
