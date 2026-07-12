package com.proyecto.auth.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GatewayHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String email = request.getHeader("X-User-Email");

        if (email != null && !email.isBlank()) {
            Long idSede    = parseLong(request.getHeader("X-User-IdSede"));
            Long idUsuario = parseLong(request.getHeader("X-User-IdUsuario"));
            String nombre  = request.getHeader("X-User-Nombre");

            Map<String, Object> details = new HashMap<>();
            details.put("idSede", idSede);
            details.put("idUsuario", idUsuario);
            details.put("nombre", nombre);

            String rolesHeader = request.getHeader("X-User-Roles");
            List<SimpleGrantedAuthority> authorities;
            if (rolesHeader != null && !rolesHeader.isBlank()) {
                authorities = Arrays.stream(rolesHeader.split("\\s+"))
                        .filter(r -> !r.isBlank())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            } else {
                authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(email, null, authorities);
            auth.setDetails(details);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private Long parseLong(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
