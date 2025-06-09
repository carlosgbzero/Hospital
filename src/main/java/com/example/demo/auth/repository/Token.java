package com.example.demo.auth.repository;

import com.example.demo.Usuario.repository.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public final class Token {

    private Integer id;
    private String token;
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;
    private Boolean isRevoked;
    private Boolean isExpired;
    private Usuario user;

    public enum TokenType {
        BEARER
    }

}
