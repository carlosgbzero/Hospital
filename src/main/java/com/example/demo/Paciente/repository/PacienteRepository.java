package com.example.demo.Paciente.repository;

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
public class PacienteRepository implements CRUD<Paciente> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void create(Paciente entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO pacientes (nombre, apellidos, fechanacimiento, direccion, hospitalcod, departamentocod, unidadcod) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getApellidos());
            stmt.setDate(3, new java.sql.Date(entity.getFechanacimiento().getTime()));
            stmt.setString(4, entity.getDireccion());
            stmt.setInt(5, entity.getHospitalcod());
            stmt.setInt(6, entity.getDepartamentocod());
            stmt.setInt(7, entity.getUnidadcod());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM pacientes";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setHistoriaclinicanum(rs.getInt("historiaclinicanum"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setFechanacimiento(rs.getDate("fechanacimiento"));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setHospitalcod(rs.getInt("hospitalcod"));
                paciente.setDepartamentocod(rs.getInt("departamentocod"));
                paciente.setUnidadcod(rs.getInt("unidadcod"));
                pacientes.add(paciente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    @Override
    public Paciente find(int id) {
        Paciente paciente = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM pacientes WHERE historiaclinicanum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setHistoriaclinicanum(rs.getInt("historiaclinicanum"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setFechanacimiento(rs.getDate("fechanacimiento"));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setHospitalcod(rs.getInt("hospitalcod"));
                paciente.setDepartamentocod(rs.getInt("departamentocod"));
                paciente.setUnidadcod(rs.getInt("unidadcod"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paciente;
    }

    @Override
    public void update(Paciente entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE pacientes SET nombre = ?, apellidos = ?, fechanacimiento = ?, direccion = ?, hospitalcod = ?, departamentocod = ?, unidadcod = ? WHERE historiaclinicanum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getApellidos());
            stmt.setDate(3, new java.sql.Date(entity.getFechanacimiento().getTime()));
            stmt.setString(4, entity.getDireccion());
            stmt.setInt(5, entity.getHospitalcod());
            stmt.setInt(6, entity.getDepartamentocod());
            stmt.setInt(7, entity.getUnidadcod());
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM pacientes WHERE historiaclinicanum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
