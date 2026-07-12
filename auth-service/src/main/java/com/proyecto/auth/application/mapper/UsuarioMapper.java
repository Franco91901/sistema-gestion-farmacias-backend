package com.proyecto.auth.application.mapper;

import com.proyecto.auth.application.dto.request.RegisterRequestDTO;
import com.proyecto.auth.application.dto.response.LoginResponseDTO;
import com.proyecto.auth.application.dto.response.UsuarioResponseDTO;
import com.proyecto.auth.domain.model.Rol;
import com.proyecto.auth.domain.model.Usuario;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioResponseDTO toResponseDTO(Usuario u) {
        if (u == null) return null;
        return new UsuarioResponseDTO(
            u.getIdUsuario(),
            u.getNombre(),
            u.getApellido(),
            u.getEmail(),
            u.getTelefono(),
            u.getDni(),
            u.getActivo(),
            u.getRol() != null ? u.getRol().getNombre() : null,
            u.getIdSede(),
            null
        );
    }

    public static Usuario fromRegistroDTO(RegisterRequestDTO dto, Rol rol, Long idSede) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());
        usuario.setTelefono(dto.telefono());
        usuario.setDni(dto.dni());
        usuario.setRol(rol);
        usuario.setIdSede(idSede);
        return usuario;
    }

    public static LoginResponseDTO toLoginResponseDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new LoginResponseDTO(
            usuario.getEmail(),
            usuario.getRol() != null ? usuario.getRol().getNombre() : null
        );
    }
}
