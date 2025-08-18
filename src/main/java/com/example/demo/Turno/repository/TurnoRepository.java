package com.example.demo.Turno.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class TurnoRepository implements CRUD<Turno> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    @Transactional
    public void create(Turno turno) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar que el médico existe
                String checkMedico = "SELECT 1 FROM medicos WHERE medicocod = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkMedico);
                checkStmt.setInt(1, turno.getMedicocod());
                if (!checkStmt.executeQuery().next()) {
                    throw new RuntimeException("El médico especificado no existe");
                }

                // Verificar solapamiento de turnos
                String checkSolapamiento = "SELECT 1 FROM turnos WHERE medicocod = ? AND ((fechahorainicio BETWEEN ? AND ?) OR (fechahorafin BETWEEN ? AND ?))";
                PreparedStatement solapamientoStmt = conn.prepareStatement(checkSolapamiento);
                solapamientoStmt.setInt(1, turno.getMedicocod());
                solapamientoStmt.setTimestamp(2, new Timestamp(turno.getFechahorainicio().getTime()));
                solapamientoStmt.setTimestamp(3, new Timestamp(turno.getFechahorafin().getTime()));
                solapamientoStmt.setTimestamp(4, new Timestamp(turno.getFechahorainicio().getTime()));
                solapamientoStmt.setTimestamp(5, new Timestamp(turno.getFechahorafin().getTime()));
                if (solapamientoStmt.executeQuery().next()) {
                    throw new RuntimeException("El turno se solapa con otro existente del mismo médico");
                }

                // Insertar turno
                String query = "INSERT INTO turnos (medicocod, hospitalcod, departamentocod, unidadcod, fechahorainicio, fechahorafin, pacientesasignados) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, turno.getMedicocod());
                stmt.setInt(2, turno.getHospitalcod());
                stmt.setInt(3, turno.getDepartamentocod());
                stmt.setInt(4, turno.getUnidadcod());
                stmt.setTimestamp(5, new Timestamp(turno.getFechahorainicio().getTime()));
                stmt.setTimestamp(6, new Timestamp(turno.getFechahorafin().getTime()));
                stmt.setInt(7, turno.getPacientesasignados());
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
    public List<Turno> findAll() {
        List<Turno> turnos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM turnos";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Turno turno = new Turno();
                turno.setTurnonum(rs.getInt("turnonum"));
                turno.setMedicocod(rs.getInt("medicocod"));
                turno.setHospitalcod(rs.getInt("hospitalcod"));
                turno.setDepartamentocod(rs.getInt("departamentocod"));
                turno.setUnidadcod(rs.getInt("unidadcod"));
                turno.setFechahorainicio(rs.getTimestamp("fechahorainicio"));
                turno.setFechahorafin(rs.getTimestamp("fechahorafin"));
                turno.setPacientesasignados(rs.getInt("pacientesasignados"));
                turnos.add(turno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return turnos;
    }

    @Override
    public Turno find(int id) {
        Turno turno = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM turnos WHERE turnonum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                turno = new Turno();
                turno.setTurnonum(rs.getInt("turnonum"));
                turno.setMedicocod(rs.getInt("medicocod"));
                turno.setHospitalcod(rs.getInt("hospitalcod"));
                turno.setDepartamentocod(rs.getInt("departamentocod"));
                turno.setUnidadcod(rs.getInt("unidadcod"));
                turno.setFechahorainicio(rs.getTimestamp("fechahorainicio"));
                turno.setFechahorafin(rs.getTimestamp("fechahorafin"));
                turno.setPacientesasignados(rs.getInt("pacientesasignados"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return turno;
    }

    @Override
    public void update(Turno entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE turnos SET medicocod = ?, hospitalcod = ?, departamentocod = ?, unidadcod = ?, fechahorainicio = ?, fechahorafin = ?, pacientesasignados = ? WHERE turnonum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getMedicocod());
            stmt.setInt(2, entity.getHospitalcod());
            stmt.setInt(3, entity.getDepartamentocod());
            stmt.setInt(4, entity.getUnidadcod());
            stmt.setTimestamp(5, new Timestamp(entity.getFechahorainicio().getTime()));
            stmt.setTimestamp(6, new Timestamp(entity.getFechahorafin().getTime()));
            stmt.setInt(7, entity.getPacientesasignados());
            stmt.setInt(8, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM turnos WHERE turnonum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
