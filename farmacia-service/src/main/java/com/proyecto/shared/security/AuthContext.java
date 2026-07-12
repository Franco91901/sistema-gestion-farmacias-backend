package com.proyecto.shared.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthContext {

    public String getEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        return auth.getName();
    }

    public Long getIdSede() {
        return getDetail("idSede");
    }

    public Long getIdUsuario() {
        return getDetail("idUsuario");
    }

    public String getNombreUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getDetails() instanceof Map<?, ?> details)) return null;
        Object val = details.get("nombre");
        return val instanceof String s ? s : null;
    }

    @SuppressWarnings("unchecked")
    private Long getDetail(String key) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getDetails() instanceof Map)) return null;
        Object value = ((Map<String, Object>) auth.getDetails()).get(key);
        return value instanceof Number n ? n.longValue() : null;
    }
}
