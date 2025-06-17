package com.Perfulandia.ApiGateway.jwt.service;

import com.Perfulandia.ApiGateway.jwt.dto.AuthResponse;
import com.Perfulandia.ApiGateway.jwt.dto.LoginRequest;
import com.Perfulandia.ApiGateway.jwt.model.Usuario;
import com.Perfulandia.ApiGateway.jwt.repository.UsuarioRepository;
import com.Perfulandia.ApiGateway.jwt.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getPassword()));

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        if (!usuario.getActivo()) {
            throw new BadCredentialsException("Usuario inactivo");
        }

        String token = jwtUtil.generateToken(usuario.getNombreUsuario());
        return new AuthResponse(token, usuario.getNombreUsuario(), usuario.getActivo());
    }
}