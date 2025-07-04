package com.Perfulandia.ApiGateway.jwt.repository;

import com.Perfulandia.ApiGateway.jwt.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}