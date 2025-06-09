package com.example.demo.Usuario.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class UsuarioRepository implements CRUD<Usuario> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;

    @Override
    public void create(Usuario entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO usuarios (nombreusuario, contrasenahash, email, nombre, apellidos, activo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombreusuario());
            stmt.setString(2, entity.getContrasenahash());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getNombre());
            stmt.setString(5, entity.getApellidos());
            stmt.setBoolean(6, entity.isActivo());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Necesita transacción por la relación con roles
    @Transactional
    public void create(Usuario usuario, int rolCod) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Insertar usuario
                String queryUsuario = "INSERT INTO usuarios (nombreusuario, contrasenahash, email, nombre, apellidos, activo) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, usuario.getNombreusuario());
                stmt.setString(2, usuario.getContrasenahash());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getNombre());
                stmt.setString(5, usuario.getApellidos());
                stmt.setBoolean(6, usuario.isActivo());
                stmt.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int usuariocod = rs.getInt(1);
                    
                    // Insertar roles del usuario
                    String queryRoles = "INSERT INTO usuariorol (usuariocod, rolcod) VALUES (?, ?)";
                    PreparedStatement stmtRoles = conn.prepareStatement(queryRoles);
                    stmtRoles.setInt(1, usuariocod);
                    stmtRoles.setInt(2, rolCod);
                    stmtRoles.executeUpdate();
                    
                }

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM usuarios";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuariocod(rs.getInt("usuariocod"));
                usuario.setNombreusuario(rs.getString("nombreusuario"));
                usuario.setContrasenahash(rs.getString("contrasenahash"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setFechacreacion(rs.getTimestamp("fechacreacion"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario find(int id) {
        Usuario usuario = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE usuariocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuariocod(rs.getInt("usuariocod"));
                usuario.setNombreusuario(rs.getString("nombreusuario"));
                usuario.setContrasenahash(rs.getString("contrasenahash"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setFechacreacion(rs.getTimestamp("fechacreacion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario findByEmail(String email) {
        Usuario usuario = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuariocod(rs.getInt("usuariocod"));
                usuario.setNombreusuario(rs.getString("nombreusuario"));
                usuario.setContrasenahash(rs.getString("contrasenahash"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setFechacreacion(rs.getTimestamp("fechacreacion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void update(Usuario entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE usuarios SET nombreusuario = ?, contrasenahash = ?, email = ?, nombre = ?, apellidos = ?, activo = ? WHERE usuariocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombreusuario());
            stmt.setString(2, entity.getContrasenahash());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getNombre());
            stmt.setString(5, entity.getApellidos());
            stmt.setBoolean(6, entity.isActivo());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM usuarios WHERE usuariocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}