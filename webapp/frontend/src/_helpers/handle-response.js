import { authenticationService } from '_services';

/** 
 * Se maneja la respuesta del servidor. El microservicio devuelve 401 si no está autenticado con un mensaje de error 
 * encapsulado en error. 
*/
export function handleResponse(response) {
    return response.text().then(text => {

        const data = text && JSON.parse(text);

        if (!response.ok) {
            if ([401, 403].indexOf(response.status) !== -1) {
                authenticationService.logout();
            }

            const error = (data && data.error) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}