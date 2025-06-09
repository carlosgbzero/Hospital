package com.example.demo.auth.service;

import com.example.demo.Usuario.repository.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;
import com.example.demo.auth.controller.AuthRequest;
import com.example.demo.auth.controller.RegisterRequest;
import com.example.demo.auth.controller.TokenResponse;
import com.example.demo.auth.repository.Token;

import jakarta.annotation.Nonnull;
//import com.example.demo.user.User;
//import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final com.example.demo.auth.repository.TokenRepository tokenRepository; // Agregado

    public TokenResponse register(final RegisterRequest request) {
        final Usuario user = Usuario.builder()
                .nombreusuario(request.name())
                .email(request.email())
                .contrasenahash(passwordEncoder.encode(request.password()))
                .build();

        repository.create(user, request.rol());
        final String jwtToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user); // Revoca tokens previos si existen
        saveUserToken(user, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final Usuario user = repository.findByEmail(request.email());
        if(user != null) {
            final String accessToken = jwtService.generateToken(user);
            final String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);

            return new TokenResponse(accessToken, refreshToken);
        }else{
            throw new UserNotFoundException("User not found with email: " + request.email());
        }
        
    }

    private void saveUserToken(Usuario user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.create(token); 
    }

    private void revokeAllUserTokens(Usuario user) {
        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getUsuariocod());
        for (Token token : validTokens) {
            token.setIsExpired(true);
            token.setIsRevoked(true);
            tokenRepository.update(token, token.getId());
        }
    }

    public TokenResponse refreshToken(@Nonnull final String authentication) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final Usuario user = this.repository.findByEmail(userEmail);
        if(user != null) {
            final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
            if (!isTokenValid) {
                return null;
            }

            final String accessToken = jwtService.generateRefreshToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);

            return new TokenResponse(accessToken, refreshToken);
        }else{
             throw new UserNotFoundException("User not found with email: " + userEmail);
        }
        
    }
}
