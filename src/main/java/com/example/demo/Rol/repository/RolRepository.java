package com.example.demo.Rol.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class RolRepository implements CRUD<Rol> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void create(Rol entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO roles (nombrerol) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombrerol());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rol> findAll() {
        List<Rol> roles = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM roles";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setRolcod(rs.getInt("rolcod"));
                rol.setNombrerol(rs.getString("nombrerol"));
                roles.add(rol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Rol find(int id) {
        Rol rol = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM roles WHERE rolcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rol = new Rol();
                rol.setRolcod(rs.getInt("rolcod"));
                rol.setNombrerol(rs.getString("nombrerol"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rol;
    }

    @Override
    public void update(Rol entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE roles SET nombrerol = ? WHERE rolcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombrerol());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM roles WHERE rolcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}