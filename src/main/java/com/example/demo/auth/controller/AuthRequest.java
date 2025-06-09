package com.example.demo.auth.controller;

public record AuthRequest(
        String email,
        String password
) {
}
