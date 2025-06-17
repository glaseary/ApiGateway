package com.Perfulandia.ApiGateway.jwt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}