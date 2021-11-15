package edu.uoc.tfgmonitorsystem.common.controller.security;

/**
 * Constantes JWT que serán usadas para filtrar peticiones y requerir seguridad.
 */
public class JwtConstants {

    private JwtConstants() {
        super();
    }

    /**
     * Cabecera usada para el token de autenticación.
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Tiempo usado para la validez de un token.
     */
    public static final long TOKEN_VALID_TIME_MILLIS = 5 * 60 * 60 * 1000;

    /**
     * URL usada para la autenticación en el sistema.
     */
    public static final String AUTHORIZATION_URL = "/authenticate";

}
