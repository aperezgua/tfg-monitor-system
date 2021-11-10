package edu.uoc.tfgmonitorsystem.common.controller.security;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro de spring que se encarga de la petición HTTP y comprobar que dispone del token de autenticación.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = Logger.getLogger(JwtRequestFilter.class);
    /**
     * Servicio UserDetailsService que será implementado por cada módulo concreto y ofrecerá un soporte común para la
     * obtención de un usuario aprovechando el sistema de autenticación de Spring.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Utilidade que permite validar los token.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Ejecución de la cadena de filtros.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String username = null;

        printLog(request);

        if (!request.getMethod().equals("OPTIONS")) {
            final String requestTokenHeader = request.getHeader(JwtConstants.AUTHORIZATION_HEADER);
            if (requestTokenHeader != null) {
                try {
                    username = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
                } catch (IllegalArgumentException e) {
                    logger.error("Cannot get token: " + requestTokenHeader, e);
                } catch (ExpiredJwtException e) {
                    logger.warn("Token was expired: " + requestTokenHeader);
                }
            }

            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // if token is valid configure Spring Security to manually set authentication
                if (jwtTokenUtil.validateToken(requestTokenHeader, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the Spring Security
                    // Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private void printLog(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();

        builder.append("request: ").append(request.getRequestURI());

        builder.append("\n## HEADERS:\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            builder.append("  ").append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        builder.append("\n## PARAMETERS:\n");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            builder.append("  ").append(parameterName).append(": ").append(request.getParameter(parameterName))
                    .append("\n");
        }

        LOGGER.debug(builder.toString());
    }

}
