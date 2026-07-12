package com.proyecto.auth.application.service;

import com.proyecto.auth.application.dto.request.LoginRequestDTO;
import com.proyecto.auth.application.dto.request.RegisterRequestDTO;
import com.proyecto.auth.application.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO dto);
    void register(RegisterRequestDTO dto);
}
