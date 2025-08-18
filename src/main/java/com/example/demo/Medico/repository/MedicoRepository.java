package com.example.demo.Medico.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Paciente.repository.PacienteReporteDTO;
import com.example.demo.config.Conexion;
import com.example.demo.interfaces.CRUD;

@Repository
public class MedicoRepository implements CRUD<Medico> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    @Transactional
    public void create(Medico medico) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Verificar que la unidad existe
                String checkUnidad = "SELECT 1 FROM unidades WHERE hospitalcod = ? AND departamentocod = ? AND unidadcod = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkUnidad);
                checkStmt.setInt(1, medico.getHospitalCod());
                checkStmt.setInt(2, medico.getDepartamentoCod());
                checkStmt.setInt(3, medico.getUnidadCod());
                if (!checkStmt.executeQuery().next()) {
                    throw new RuntimeException("La unidad especificada no existe");
                }

                // Insertar médico
                String query = "INSERT INTO medicos (nombre, apellidos, especialidad, licenciamedica, telefono, anosexperiencia, contacto, hospitalcod, departamentocod, unidadcod) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, medico.getNombre());
                stmt.setString(2, medico.getApellidos());
                stmt.setString(3, medico.getEspecialidad());
                stmt.setString(4, medico.getLicenciaMedica());
                stmt.setString(5, medico.getTelefono());
                stmt.setInt(6, medico.getAnosExperiencia());
                stmt.setString(7, medico.getContacto());
                stmt.setInt(8, medico.getHospitalCod());
                stmt.setInt(9, medico.getDepartamentoCod());
                stmt.setInt(10, medico.getUnidadCod());
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
                medico.setMedicoCod(rs.getInt("medicocod"));
                medico.setNombre(rs.getString("nombre"));
                medico.setApellidos(rs.getString("apellidos"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setLicenciaMedica(rs.getString("licenciamedica"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setAnosExperiencia(rs.getInt("anosexperiencia"));
                medico.setContacto(rs.getString("contacto"));
                medico.setHospitalCod(rs.getInt("hospitalcod"));
                medico.setDepartamentoCod(rs.getInt("departamentocod"));
                medico.setUnidadCod(rs.getInt("unidadcod"));
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
                medico.setMedicoCod(rs.getInt("medicocod"));
                medico.setNombre(rs.getString("nombre"));
                medico.setApellidos(rs.getString("apellidos"));
                medico.setEspecialidad(rs.getString("especialidad"));
                medico.setLicenciaMedica(rs.getString("licenciamedica"));
                medico.setTelefono(rs.getString("telefono"));
                medico.setAnosExperiencia(rs.getInt("anosexperiencia"));
                medico.setContacto(rs.getString("contacto"));
                medico.setHospitalCod(rs.getInt("hospitalcod"));
                medico.setDepartamentoCod(rs.getInt("departamentocod"));
                medico.setUnidadCod(rs.getInt("unidadcod"));
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
            stmt.setString(4, entity.getLicenciaMedica());
            stmt.setString(5, entity.getTelefono());
            stmt.setInt(6, entity.getAnosExperiencia());
            stmt.setString(7, entity.getContacto());
            stmt.setInt(8, entity.getHospitalCod());
            stmt.setInt(9, entity.getDepartamentoCod());
            stmt.setInt(10, entity.getUnidadCod());
            stmt.setInt(11, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<MedicoReporteDTO> findByFiltro(Integer hospitalcod, Integer departamentocod, Integer unidadcod) {
        List<MedicoReporteDTO> medicos = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT m.nombre || ' ' || m.apellidos AS nombreCompleto, " +
            "m.especialidad" + "m.licenciamedica, " + "m.telefono, " +
            "m.anosexperiencia, " + "m.contacto, " +
            "m.hospitalcod, m.departamentocod, m.unidadcod, " +
            "h.nombre AS nombreHospital, d.nombre AS nombreDepartamento, u.nombre AS nombreUnidad " +
            "FROM medicos m " +
            "JOIN hospitales h ON m.hospitalcod = h.hospitalcod " +
            "JOIN departamentos d ON m.departamentocod = d.departamentocod AND m.hospitalcod = d.hospitalcod " +
            "JOIN unidades u ON m.unidadcod = u.unidadcod AND m.departamentocod = u.departamentocod AND m.hospitalcod = u.hospitalcod " +
            "WHERE 1=1"
        );
        if (hospitalcod != null) {
            query.append(" AND m.hospitalcod = ?");
            if (departamentocod != null){
                query.append(" AND m.departamentocod = ?");
                if (unidadcod != null)
                    query.append(" AND m.unidadcod = ?");
            }
        }
        query.append(" ORDER BY m.hospitalcod, m.departamentocod, m.unidadcod, m.apellidos, m.nombre");

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query.toString());
            int idx = 1;
            if (hospitalcod != null) stmt.setInt(idx++, hospitalcod);
            if (departamentocod != null) stmt.setInt(idx++, departamentocod);
            if (unidadcod != null) stmt.setInt(idx++, unidadcod);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicoReporteDTO dto = new MedicoReporteDTO();
                dto.setNombreCompleto(rs.getString("nombreCompleto"));
                dto.setEspecialidad(rs.getString("especialidad"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setAnosExperiencia(rs.getInt("anosesperiencia"));
                dto.setContacto(rs.getString("contacto"));
                dto.setNumLicencia(rs.getString("licenciamedica"));
                dto.setNombreHospital(rs.getString("nombreHospital"));
                dto.setNombreDepartamento(rs.getString("nombreDepartamento"));
                dto.setNombreUnidad(rs.getString("nombreUnidad"));
                medicos.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicos;
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
