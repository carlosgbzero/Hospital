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
            stmt.setDate(3, new java.sql.Date(entity.getFechaNacimiento().getTime()));
            stmt.setString(4, entity.getDireccion());
            stmt.setInt(5, entity.getHospitalCod());
            stmt.setInt(6, entity.getDepartamentoCod());
            stmt.setInt(7, entity.getUnidadCod());
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
                paciente.setHistoriaClinicaNum(rs.getInt("historiaclinicanum"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setFechaNacimiento(rs.getDate("fechanacimiento"));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setHospitalCod(rs.getInt("hospitalcod"));
                paciente.setDepartamentoCod(rs.getInt("departamentocod"));
                paciente.setUnidadCod(rs.getInt("unidadcod"));
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
                paciente.setHistoriaClinicaNum(rs.getInt("historiaclinicanum"));
                paciente.setNombre(rs.getString("nombre"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setFechaNacimiento(rs.getDate("fechanacimiento"));
                paciente.setDireccion(rs.getString("direccion"));
                paciente.setHospitalCod(rs.getInt("hospitalcod"));
                paciente.setDepartamentoCod(rs.getInt("departamentocod"));
                paciente.setUnidadCod(rs.getInt("unidadcod"));
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
            stmt.setDate(3, new java.sql.Date(entity.getFechaNacimiento().getTime()));
            stmt.setString(4, entity.getDireccion());
            stmt.setInt(5, entity.getHospitalCod());
            stmt.setInt(6, entity.getDepartamentoCod());
            stmt.setInt(7, entity.getUnidadCod());
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

    public List<PacienteReporteDTO> findByFiltro(Integer hospitalcod, Integer departamentocod, Integer unidadcod) {
        List<PacienteReporteDTO> pacientes = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT p.historiaclinicanum, " +
            "p.nombre || ' ' || p.apellidos AS nombreCompleto, " +
            "TO_CHAR(p.fechanacimiento, 'YYYY-MM-DD') AS fechaNacimiento, " +
            "p.direccion, " +
            "p.hospitalcod, p.departamentocod, p.unidadcod, " +
            "h.nombre AS nombreHospital, d.nombre AS nombreDepartamento, u.nombre AS nombreUnidad " +
            "FROM pacientes p " +
            "JOIN hospitales h ON p.hospitalcod = h.hospitalcod " +
            "JOIN departamentos d ON p.departamentocod = d.departamentocod AND p.hospitalcod = d.hospitalcod " +
            "JOIN unidades u ON p.unidadcod = u.unidadcod AND p.departamentocod = u.departamentocod AND p.hospitalcod = u.hospitalcod " +
            "WHERE 1=1"
        );
        if (hospitalcod != null) {
            query.append(" AND p.hospitalcod = ?");
        if (departamentocod != null){
            query.append(" AND p.departamentocod = ?");
            if (unidadcod != null) query.append(" AND p.unidadcod = ?");
            }
        }
        query.append(" ORDER BY p.hospitalcod, p.departamentocod, p.unidadcod, p.apellidos, p.nombre");

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query.toString());
            int idx = 1;
            if (hospitalcod != null) stmt.setInt(idx++, hospitalcod);
            if (departamentocod != null) stmt.setInt(idx++, departamentocod);
            if (unidadcod != null) stmt.setInt(idx++, unidadcod);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PacienteReporteDTO dto = new PacienteReporteDTO();
                dto.setHistoriaclinicanum(rs.getInt("historiaclinicanum"));
                dto.setNombreCompleto(rs.getString("nombreCompleto"));
                dto.setFechaNacimiento(rs.getString("fechaNacimiento"));
                dto.setDireccion(rs.getString("direccion"));
                dto.setHospitalcod(rs.getInt("hospitalcod"));
                dto.setDepartamentocod(rs.getInt("departamentocod"));
                dto.setUnidadcod(rs.getInt("unidadcod"));
                dto.setNombreHospital(rs.getString("nombreHospital"));
                dto.setNombreDepartamento(rs.getString("nombreDepartamento"));
                dto.setNombreUnidad(rs.getString("nombreUnidad"));
                pacientes.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pacientes;
    }

    public int countByHospitalId(int hospitalId) {
        int cant = 0;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM pacientes WHERE hospitalcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, hospitalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cant = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cant;     
    }

    public int countByDepartamentoId(Long departamentoID) {
        int cant = 0;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM pacientes WHERE departamentocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, departamentoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cant = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cant;     
    }

    public int countByUnidadId(Long unidadID) {
        int cant = 0;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM pacientes WHERE unidadcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, unidadID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cant = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cant;     
    }
}
