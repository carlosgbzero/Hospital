package com.example.demo.Hospital.repository;

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
public class HospitalRepository implements CRUD<Hospital> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void create(Hospital entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO hospitales (hospitalcod, nombre) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHospitalcod());
            stmt.setString(2, entity.getNombre());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hospital> findAll() {
        List<Hospital> hospitals = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM hospitales";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hospital hospital = new Hospital();
                hospital.setHospitalcod(rs.getInt("hospitalcod"));
                hospital.setNombre(rs.getString("nombre"));
                hospitals.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    @Override
    public Hospital find(int id) {
        Hospital hospital = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM hospitales WHERE hospitalcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                hospital = new Hospital();
                hospital.setHospitalcod(rs.getInt("hospitalcod"));
                hospital.setNombre(rs.getString("nombre"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospital;
    }

    @Override
    public void update(Hospital entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE hospitales SET nombre = ? WHERE hospitalcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombre());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM hospitales WHERE hospitalcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
