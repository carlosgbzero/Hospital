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

    public List<ReporteHospitalDTO> findAllWithCount() {
        List<ReporteHospitalDTO> hospitals = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT h.hospitalcod, h.nombre," + 
            "COUNT(DISTINCT d.departamentocod) AS cantidadDepartamentos," + 
            "COUNT(DISTINCT u.unidadcod) AS cantidadUnidades" +
            "COUNT(DISTINCT p.historiaclinicanum) AS cantidadPacientes FROM hospitales h" + 
            "LEFT JOIN departamentos d ON h.hospitalcod = d.hospitalcod" + 
            "LEFT JOIN unidades u ON h.hospitalcod = u.hospitalcod" + 
            "LEFT JOIN pacientes p ON h.hospitalcod = p.hospitalcod" +
            "GROUP BY h.hospitalcod, h.nombre";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReporteHospitalDTO hospital = new ReporteHospitalDTO();
                hospital.setId(rs.getInt("hospitalcod"));
                hospital.setNombre(rs.getString("nombre"));
                hospital.setCantidadDepartamentos(rs.getInt("cantidadDepartamentos"));
                hospital.setCantidadUnidades(rs.getInt("cantidadUnidades"));
                hospital.setCantidadPacientes(rs.getInt("cantidadPacientes"));
                hospitals.add(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hospitals;
    }

    public List<ReporteHospitalDTO> findWithMostPatients() {
        List<ReporteHospitalDTO> hospitals = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT h.hospitalcod, h.nombre," + 
            "COUNT(DISTINCT p.historiaclinicanum) AS cantidadPacientes FROM hospitales h" + 
            "LEFT JOIN pacientes p ON h.hospitalcod = p.hospitalcod" +
            "GROUP BY h.hospitalcod, h.nombre" + 
            "ORDER BY cantidadPacientes DESC" + "LIMIT 5";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReporteHospitalDTO hospital = new ReporteHospitalDTO();
                hospital.setId(rs.getInt("hospitalcod"));
                hospital.setNombre(rs.getString("nombre"));
                hospital.setCantidadDepartamentos(rs.getInt("cantidadDepartamentos"));
                hospital.setCantidadUnidades(rs.getInt("cantidadUnidades"));
                hospital.setCantidadPacientes(rs.getInt("cantidadPacientes"));
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
