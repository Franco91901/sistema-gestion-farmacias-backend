package com.proyecto.auth.application.service;

import com.proyecto.auth.application.dto.request.LoginRequestDTO;
import com.proyecto.auth.application.dto.request.RegisterRequestDTO;
import com.proyecto.auth.application.dto.response.AuthResponseDTO;
import com.proyecto.auth.application.dto.response.UsuarioResponseDTO;
import com.proyecto.auth.domain.model.Rol;
import com.proyecto.auth.domain.model.Usuario;
import com.proyecto.auth.domain.repository.RolRepository;
import com.proyecto.auth.domain.repository.UsuarioRepository;
import com.proyecto.auth.infrastructure.security.JwtService;
import com.proyecto.shared.exception.EntityNotFoundException;
import com.proyecto.shared.exception.ExceptionConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationConfiguration authenticationConfiguration;

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {
        try {
            AuthenticationManager am = authenticationConfiguration.getAuthenticationManager();
            Authentication auth = am.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
            );
            String token = jwtService.generateToken(auth);
            Usuario usuario = (Usuario) auth.getPrincipal();
            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(
                    usuario.getIdUsuario(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getTelefono(),
                    usuario.getDni(),
                    usuario.getActivo(),
                    usuario.getRol().getNombre(),
                    usuario.getIdSede(),
                    null
            );
            return new AuthResponseDTO(token, usuarioDTO);
        } catch (DisabledException e) {
            throw e;
        } catch (BadCredentialsException e) {
            log.error("Credenciales incorrectas para: {}", dto.email());
            throw new BadCredentialsException("Credenciales inválidas");
        } catch (Exception e) {
            log.error("Error en login: {}", e.getMessage());
            throw new BadCredentialsException("Error en autenticación");
        }
    }

    @Override
    public void register(RegisterRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        Rol rol = (dto.rolId() != null)
                ? rolRepository.findById(Math.toIntExact(dto.rolId()))
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"))
                : rolRepository.findByNombre("ROLE_FARMACEUTICO")
                        .orElseThrow(() -> new EntityNotFoundException("Rol FARMACEUTICO no encontrado en la BD"));

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setApellido(dto.apellido());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        usuario.setTelefono(dto.telefono());
        usuario.setDni(dto.dni());
        usuario.setRol(rol);
        usuario.setIdSede(dto.sedeId());
        usuario.setActivo(false);

        usuarioRepository.save(usuario);
        log.info("Usuario registrado: {}", dto.email());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }
}
