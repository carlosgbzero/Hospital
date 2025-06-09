package com.example.demo.auth.controller;
//Faltan mas parametros
public record RegisterRequest(
        String name,
        String email,
        String password,
        int rol
) {
}
