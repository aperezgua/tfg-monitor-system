import { authenticationService } from '_services';
import { authHeader } from '_helpers';
/** 
 * Se maneja la respuesta del servidor. El microservicio devuelve 401 si no está autenticado con un mensaje de error 
 * encapsulado en error. 
*/
export function handleResponse(response) {
    
    if(!response) {
        return Promise.reject("Cannot retrieve data from server.");
    }
    
    return response.text().then(text => {
        //console.log("response: " +text);
        
        const data = text && JSON.parse(text);

        if (!response.ok) {
            if ([401, 403].indexOf(response.status) !== -1) {
                authenticationService.logout();
            }

            const error = (data && data.error) || (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}


export async function requestUrl(url, methodDescription, bodyData) {
    let requestOptions = null;
    
    if(bodyData) {
      requestOptions = { method: methodDescription, headers: authHeader(), body : bodyData };
    } else {
      requestOptions = { method: methodDescription, headers: authHeader() };
    }
    console.log("requestUrl "+url);
    let response;
    try {
        response = await fetch(url, requestOptions);
    } catch (error) {
        console.log(error);
        const response = undefined;
    }
    return handleResponse(response);
    
}