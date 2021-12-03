import { BehaviorSubject } from 'rxjs';
import config from 'config';
import { handleResponse } from '_helpers';
import jwt from 'jwt-decode';

const currentTokenSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('jwtToken')));
const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('user')));


export const authenticationService = {
    login,
    logout,
    currentToken: currentTokenSubject.asObservable(),
    currentUser: currentUserSubject.asObservable(),
    get currentTokenValue () { return currentTokenSubject.value },
    get currentUserValue () { return currentUserSubject.value }
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
            
            let user = jwt(token.token);
            currentUserSubject.next(user);
            localStorage.setItem('user', JSON.stringify(user));
            
            console.log("Datos usuario: " +currentUserSubject.value.sub);
            return token;
        });
}

/**
 */
function logout() {    
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
    localStorage.removeItem('eventFilter');
    currentTokenSubject.next(null);
    currentUserSubject.next(null);
}