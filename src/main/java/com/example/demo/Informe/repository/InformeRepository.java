package com.example.demo.Informe.repository;

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
public class InformeRepository implements CRUD<Informe> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    @Transactional
    public void create(Informe informe) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar que el turno existe
                String checkTurno = "SELECT 1 FROM turnos WHERE turnonum = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkTurno);
                checkStmt.setInt(1, informe.getTurnonum());
                if (!checkStmt.executeQuery().next()) {
                    throw new RuntimeException("El turno especificado no existe");
                }

                // Validar que la suma de pacientes sea coherente
                if (informe.getPacientesatendidos() < 0 || 
                    informe.getPacientesaltas() < 0 || 
                    informe.getPacientesadmitidos() < 0 || 
                    informe.getTotalactual() < 0) {
                    throw new RuntimeException("Los números de pacientes no pueden ser negativos");
                }

                // Insertar informe
                String query = "INSERT INTO informes (turnonum, fechahora, pacientesatendidos, pacientesaltas, pacientesadmitidos, totalactual) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, informe.getTurnonum());
                stmt.setTimestamp(2, new Timestamp(informe.getFechahora().getTime()));
                stmt.setInt(3, informe.getPacientesatendidos());
                stmt.setInt(4, informe.getPacientesaltas());
                stmt.setInt(5, informe.getPacientesadmitidos());
                stmt.setInt(6, informe.getTotalactual());
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
    public List<Informe> findAll() {
        List<Informe> informes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM informes";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Informe informe = new Informe();
                informe.setInformenum(rs.getInt("informenum"));
                informe.setTurnonum(rs.getInt("turnonum"));
                informe.setFechahora(rs.getTimestamp("fechahora"));
                informe.setPacientesatendidos(rs.getInt("pacientesatendidos"));
                informe.setPacientesaltas(rs.getInt("pacientesaltas"));
                informe.setPacientesadmitidos(rs.getInt("pacientesadmitidos"));
                informe.setTotalactual(rs.getInt("totalactual"));
                informes.add(informe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informes;
    }

    @Override
    public Informe find(int id) {
        Informe informe = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM informes WHERE informenum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                informe = new Informe();
                informe.setInformenum(rs.getInt("informenum"));
                informe.setTurnonum(rs.getInt("turnonum"));
                informe.setFechahora(rs.getTimestamp("fechahora"));
                informe.setPacientesatendidos(rs.getInt("pacientesatendidos"));
                informe.setPacientesaltas(rs.getInt("pacientesaltas"));
                informe.setPacientesadmitidos(rs.getInt("pacientesadmitidos"));
                informe.setTotalactual(rs.getInt("totalactual"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return informe;
    }

    @Override
    public void update(Informe entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE informes SET turnonum = ?, fechahora = ?, pacientesatendidos = ?, pacientesaltas = ?, pacientesadmitidos = ?, totalactual = ? WHERE informenum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getTurnonum());
            stmt.setTimestamp(2, new Timestamp(entity.getFechahora().getTime()));
            stmt.setInt(3, entity.getPacientesatendidos());
            stmt.setInt(4, entity.getPacientesaltas());
            stmt.setInt(5, entity.getPacientesadmitidos());
            stmt.setInt(6, entity.getTotalactual());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM informes WHERE informenum = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
