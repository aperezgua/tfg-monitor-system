package edu.uoc.tfgmonitorsystem.common.controller.security;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * Utilidade que permite validar los token.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private void printLog(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();

        builder.append("request: ").append(request.getRequestURI()).append(", method:").append(request.getMethod());

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

    private void setUserInContext(HttpServletRequest request, final String requestTokenHeader) {
        try {
            User user = jwtTokenUtil.getUserFromToken(requestTokenHeader);

            // Si el username es válido, quiere decir que el token es válido y se permite el paso.
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user, null, new ArrayList<>());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error("Cannot get token: " + requestTokenHeader, e);
        } catch (ExpiredJwtException e) {
            LOGGER.warn("Token was expired: " + requestTokenHeader);
        }
    }

    /**
     * Ejecución de la cadena de filtros.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        printLog(request);

        if (!request.getMethod().equals("OPTIONS")) {
            final String requestTokenHeader = request.getHeader(JwtConstants.AUTHORIZATION_HEADER);
            if (requestTokenHeader != null) {

                if (jwtTokenUtil.validateToken(requestTokenHeader)) {

                    setUserInContext(request, requestTokenHeader);

                }
            }

        }

        chain.doFilter(request, response);

    }

}
