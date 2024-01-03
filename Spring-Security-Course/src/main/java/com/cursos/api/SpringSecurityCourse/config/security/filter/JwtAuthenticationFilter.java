package com.cursos.api.SpringSecurityCourse.config.security.filter;

import com.cursos.api.SpringSecurityCourse.exception.ObjectNotFoundException;
import com.cursos.api.SpringSecurityCourse.persistence.entity.User;
import com.cursos.api.SpringSecurityCourse.service.UserService;
import com.cursos.api.SpringSecurityCourse.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entro en el filtro JwtAuthenticationFilter");
        // 1. Obtener encabezado Http llamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        // 2. Obtener Token JWT desde el encabezado
        String jwt = authorizationHeader.split(" ")[1];
        // 3. Obtener el subject/username desde el token
        String username = jwtService.extractUsername(jwt);
        // esta accion a su vez valida el formato del token, firma y fecha de expiracion
        // 4. Setear objeto authentication dentro de Security Context Holder
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, user.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 5. Ejecutar el resto de filtros
        filterChain.doFilter(request, response);
    }
}
