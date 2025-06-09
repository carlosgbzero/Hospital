package com.example.demo.Medico.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class MedicoRepository implements CRUD<Medico> {
    
    @Autowired
    private Conexion conexion;
    
    @Override
    @Transactional
    public void create(Medico medico) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar que la unidad existe
                String checkUnidad = "SELECT 1 FROM unidades WHERE hospitalcod = ? AND departamentocod = ? AND unidadcod = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkUnidad);
                checkStmt.setInt(1, medico.getHospitalcod());
                checkStmt.setInt(2, medico.getDepartamentocod());
                checkStmt.setInt(3, medico.getUnidadcod());
                if (!checkStmt.executeQuery().next()) {
                    throw new RuntimeException("La unidad especificada no existe");
                }

                // Insertar médico
                String query = "INSERT INTO medicos (nombre, apellidos, especialidad, licenciamedica, telefono, anosexperiencia, contacto, hospitalcod, departamentocod, unidadcod) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, medico.getNombre());
                stmt.setString(2, medico.getApellidos());
                stmt.setString(3, medico.getEspecialidad());
                stmt.setString(4, medico.getLicenciamedica());
                stmt.setString(5, medico.getTelefono());
                stmt.setInt(6, medico.getAnosexperiencia());
                stmt.setString(7, medico.getContacto());
                stmt.setInt(8, medico.getHospitalcod());
                stmt.setInt(9, medico.getDepartamentocod());
                stmt.setInt(10, medico.getUnidadcod());
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
    public List<Medico> findAll() {
        List<Medico> medicos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM medicos";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medico medico = new Medico();
                medico.setMedicocod(rs.getInt("medicocod"));
                medico.setNombre(rs.getString("nombre"));
                medico.setApellidos(rs.getString("apellidos"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setLicenciamedica(rs.getString("licenciamedica"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setAnosexperiencia(rs.getInt("anosexperiencia"));
                medico.setContacto(rs.getString("contacto"));
                medico.setHospitalcod(rs.getInt("hospitalcod"));
                medico.setDepartamentocod(rs.getInt("departamentocod"));
                medico.setUnidadcod(rs.getInt("unidadcod"));
                medicos.add(medico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicos;
    }

    @Override
    public Medico find(int id) {
        Medico medico = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM medicos WHERE medicocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                medico = new Medico();
                medico.setMedicocod(rs.getInt("medicocod"));
                medico.setNombre(rs.getString("nombre"));
                medico.setApellidos(rs.getString("apellidos"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setLicenciamedica(rs.getString("licenciamedica"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setAnosexperiencia(rs.getInt("anosexperiencia"));
                medico.setContacto(rs.getString("contacto"));
                medico.setHospitalcod(rs.getInt("hospitalcod"));
                medico.setDepartamentocod(rs.getInt("departamentocod"));
                medico.setUnidadcod(rs.getInt("unidadcod"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medico;
    }

    @Override
    public void update(Medico entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE medicos SET nombre = ?, apellidos = ?, especialidad = ?, licenciamedica = ?, telefono = ?, anosexperiencia = ?, contacto = ?, hospitalcod = ?, departamentocod = ?, unidadcod = ? WHERE medicocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getApellidos());
            stmt.setString(3, entity.getEspecialidad());
            stmt.setString(4, entity.getLicenciamedica());
            stmt.setString(5, entity.getTelefono());
            stmt.setInt(6, entity.getAnosexperiencia());
            stmt.setString(7, entity.getContacto());
            stmt.setInt(8, entity.getHospitalcod());
            stmt.setInt(9, entity.getDepartamentocod());
            stmt.setInt(10, entity.getUnidadcod());
            stmt.setInt(11, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM medicos WHERE medicocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
