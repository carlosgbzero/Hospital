package com.example.demo.auth.repository;

import com.example.demo.config.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Repository
public class TokenRepository  {

    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;

    
    public List<Token> findAllValidTokenByUser(int userId) {
        List<Token> tokens = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM tokens WHERE user_id = ? AND (is_expired = false OR is_revoked = false)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Token token = Token.builder()
                        .id(rs.getInt("id"))
                        .token(rs.getString("token"))
                        .tokenType(Token.TokenType.valueOf(rs.getString("token_type")))
                        .isExpired(rs.getBoolean("is_expired"))
                        .isRevoked(rs.getBoolean("is_revoked"))
                        .build();
                tokens.add(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }

    
    public Optional<Token> findByToken(String tokenStr) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM tokens WHERE token = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tokenStr);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Token token = Token.builder()
                        .id(rs.getInt("id"))
                        .token(rs.getString("token"))
                        .tokenType(Token.TokenType.valueOf(rs.getString("token_type")))
                        .isExpired(rs.getBoolean("is_expired"))
                        .isRevoked(rs.getBoolean("is_revoked"))
                        .build();
                return Optional.of(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void create(Token token) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO tokens (token, token_type, is_expired, is_revoked, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getTokenType().name());
            stmt.setBoolean(3, token.getIsExpired());
            stmt.setBoolean(4, token.getIsRevoked());
            stmt.setInt(5, token.getUser().getUsuariocod());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Token> findAll() {
        List<Token> tokens = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM tokens";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Token token = Token.builder()
                        .id(rs.getInt("id"))
                        .token(rs.getString("token"))
                        .tokenType(Token.TokenType.valueOf(rs.getString("token_type")))
                        .isExpired(rs.getBoolean("is_expired"))
                        .isRevoked(rs.getBoolean("is_revoked"))
                        .build();
                tokens.add(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }

    public Token find(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM tokens WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Token.builder()
                        .id(rs.getInt("id"))
                        .token(rs.getString("token"))
                        .tokenType(Token.TokenType.valueOf(rs.getString("token_type")))
                        .isExpired(rs.getBoolean("is_expired"))
                        .isRevoked(rs.getBoolean("is_revoked"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Token token, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE tokens SET token = ?, token_type = ?, is_expired = ?, is_revoked = ?, user_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getTokenType().name());
            stmt.setBoolean(3, token.getIsExpired());
            stmt.setBoolean(4, token.getIsRevoked());
            stmt.setInt(5, token.getUser().getUsuariocod());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM tokens WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
