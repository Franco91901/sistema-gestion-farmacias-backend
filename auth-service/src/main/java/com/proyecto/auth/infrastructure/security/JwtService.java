package com.proyecto.auth.infrastructure.security;

import com.proyecto.auth.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${application.security.jwt.expiration}")
    private int jwtExpiration;

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Usuario usuario = (Usuario) authentication.getPrincipal();
        Objects.requireNonNull(usuario);

        String nombre = usuario.getNombre();
        if (usuario.getApellido() != null && !usuario.getApellido().isBlank()) {
            nombre += " " + usuario.getApellido();
        }

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(usuario.getEmail())
                .issuedAt(now)
                .expiresAt(now.plus(jwtExpiration, ChronoUnit.MINUTES))
                .claim("roles", roles)
                .claim("idSede", usuario.getIdSede())
                .claim("idUsuario", usuario.getIdUsuario())
                .claim("nombre", nombre)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)
        ).getTokenValue();
    }
}
