package com.proyecto.auth.infrastructure.controller;

import com.proyecto.auth.application.dto.request.LoginRequestDTO;
import com.proyecto.auth.application.dto.request.RegisterRequestDTO;
import com.proyecto.auth.application.dto.response.AuthResponseDTO;
import com.proyecto.auth.application.service.AuthService;
import com.proyecto.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(dto)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok(ApiResponse.ok("Registro exitoso. Esperando aprobación.", null));
    }
}
