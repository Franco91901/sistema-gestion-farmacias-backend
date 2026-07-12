package com.proyecto.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    private final ReactiveJwtDecoder jwtDecoder;

    public JwtGatewayFilter(ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.startsWith("/api/auth/") || path.equals("/api/sedes") || path.startsWith("/api/sedes/")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        return jwtDecoder.decode(token)
                .flatMap(jwt -> {
                    String email = jwt.getSubject();
                    Object idSede = jwt.getClaim("idSede");
                    Object idUsuario = jwt.getClaim("idUsuario");
                    Object nombre = jwt.getClaim("nombre");
                    Object roles = jwt.getClaim("roles");

                    ServerWebExchange mutated = exchange.mutate()
                            .request(r -> r
                                    .header("X-User-Email", email != null ? email : "")
                                    .header("X-User-IdSede", idSede != null ? idSede.toString() : "")
                                    .header("X-User-IdUsuario", idUsuario != null ? idUsuario.toString() : "")
                                    .header("X-User-Nombre", nombre != null ? nombre.toString() : "")
                                    .header("X-User-Roles", roles != null ? roles.toString() : "")
                            )
                            .build();

                    return chain.filter(mutated);
                })
                .onErrorResume(ex -> {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
