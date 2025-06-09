package com.example.demo.Usuario.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int usuariocod;
    private String nombreusuario;
    private String contrasenahash;
    private String email;
    private String nombre;
    private String apellidos;
    private boolean activo;
    private Date fechacreacion;
}