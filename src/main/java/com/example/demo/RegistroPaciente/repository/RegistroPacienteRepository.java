package com.example.demo.RegistroPaciente.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class RegistroPacienteRepository implements CRUD<RegistroPaciente> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    @Transactional
    public void create(RegistroPaciente registro) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar que el paciente existe
                String checkPaciente = "SELECT 1 FROM pacientes WHERE historiaclinicanum = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkPaciente);
                checkStmt.setInt(1, registro.getHistoriaclinicanum());
                if (!checkStmt.executeQuery().next()) {
                    throw new RuntimeException("El paciente especificado no existe");
                }

                // Verificar que no haya un registro activo
                String checkActivo = "SELECT 1 FROM registropaciente WHERE historiaclinicanum = ? AND fechabaja IS NULL";
                PreparedStatement activoStmt = conn.prepareStatement(checkActivo);
                activoStmt.setInt(1, registro.getHistoriaclinicanum());
                if (activoStmt.executeQuery().next()) {
                    throw new RuntimeException("El paciente ya tiene un registro activo");
                }

                // Insertar registro
                String query = "INSERT INTO registropaciente (historiaclinicanum, fechaalta, fechabaja, atendido, causanoatencion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, registro.getHistoriaclinicanum());
                stmt.setDate(2, new java.sql.Date(registro.getFechaalta().getTime()));
                stmt.setDate(3, registro.getFechabaja() != null ? new java.sql.Date(registro.getFechabaja().getTime()) : null);
                stmt.setBoolean(4, registro.isAtendido());
                stmt.setString(5, registro.getCausanoatencion());
                stmt.executeUpdate();

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
    public List<RegistroPaciente> findAll() {
        List<RegistroPaciente> registros = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM registropaciente";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegistroPaciente registro = new RegistroPaciente();
                registro.setRegistroid(rs.getInt("registroid"));
                registro.setHistoriaclinicanum(rs.getInt("historiaclinicanum"));
                registro.setFechaalta(rs.getDate("fechaalta"));
                registro.setFechabaja(rs.getDate("fechabaja"));
                registro.setAtendido(rs.getBoolean("atendido"));
                registro.setCausanoatencion(rs.getString("causanoatencion"));
                registros.add(registro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registros;
    }

    @Override
    public RegistroPaciente find(int id) {
        RegistroPaciente registro = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM registropaciente WHERE registroid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                registro = new RegistroPaciente();
                registro.setRegistroid(rs.getInt("registroid"));
                registro.setHistoriaclinicanum(rs.getInt("historiaclinicanum"));
                registro.setFechaalta(rs.getDate("fechaalta"));
                registro.setFechabaja(rs.getDate("fechabaja"));
                registro.setAtendido(rs.getBoolean("atendido"));
                registro.setCausanoatencion(rs.getString("causanoatencion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registro;
    }

    @Override
    public void update(RegistroPaciente entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE registropaciente SET historiaclinicanum = ?, fechaalta = ?, fechabaja = ?, atendido = ?, causanoatencion = ? WHERE registroid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHistoriaclinicanum());
            stmt.setDate(2, new java.sql.Date(entity.getFechaalta().getTime()));
            stmt.setDate(3, entity.getFechabaja() != null ? new java.sql.Date(entity.getFechabaja().getTime()) : null);
            stmt.setBoolean(4, entity.isAtendido());
            stmt.setString(5, entity.getCausanoatencion());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM registropaciente WHERE registroid = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
