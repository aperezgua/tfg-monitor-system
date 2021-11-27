package edu.uoc.tfgmonitorsystem.common.controller.security;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase de utilidades de JWT encargada de gestionar los token.
 */
@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenUtil.class);

    private static final String NAME_CLAIM = "name";
    private static final String ROL_CLAIM = "rol";

    /**
     * Clave usada para firmar el Token del usuario generador por el sistema.
     */
    @Value("${jwt.secret}")
    private String secret;

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token) || ignoreTokenExpiration(token);
    }

    /**
     * Genera un token para un agente.
     *
     * @param agent Agent con el objeto agente.
     * @return String con el token JWT.
     */
    public String generateToken(Agent agent) {
        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, agent.getToken());
    }

    /**
     * Genera un token para el usuario.
     *
     * @param user User con el usuario a generar el token.
     * @return String con el token generado.
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(NAME_CLAIM, user.getName());
        claims.put(ROL_CLAIM, user.getRol());

        return doGenerateToken(claims, user.getEmail());
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public User getUserFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);

        User user = new User();
        user.setEmail(claims.getSubject());
        user.setName(claims.get(NAME_CLAIM, String.class));

        user.setRol(Rol.valueOf(claims.get(ROL_CLAIM, String.class)));

        return user;
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Genera un token con los diferentes datos con claims y un asunto.
     *
     * @param claims  Objectos a ser agregados al token.
     * @param subject Asunto del token.
     * @return String con el token generado.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        Date createDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + JwtConstants.TOKEN_VALID_TIME_MILLIS);

        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, getSecreEncode()).compact();

        LOGGER.debug("doGenerateToken [ claims=" + claims + ", subject=" + subject + ", createDate=" + createDate
                + ", expirationDate=" + expirationDate + "]" + " secret=" + secret + ", token=" + token);

        return token;
    }

    /**
     * Obtiene los datos a partir del token que ha sido firmado por la clave secreta.
     *
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(getSecreEncode()).parseClaimsJws(token).getBody();
    }

    /**
     * Obtiene los bytes en UTF8 de la clave.
     *
     * @return
     */
    private byte[] getSecreEncode() {
        return secret.getBytes(StandardCharsets.UTF_8);
    }

    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
