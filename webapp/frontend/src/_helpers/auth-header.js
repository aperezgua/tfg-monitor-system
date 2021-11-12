import { authenticationService } from '_services';

export function authHeader() {
    // return authorization header with jwt token
    const currentToken = authenticationService.currentTokenValue;
    
    
    if (currentToken && currentToken.token) {
        return { Authorization: `${currentToken.token}` };
    } else {
        return {};
    }
}