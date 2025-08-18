package com.example.demo.Unidad.repository;

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
public class UnidadRepository implements CRUD<Unidad> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    @Override
    public void create(Unidad entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO unidades (hospitalcod, departamentocod, nombre, ubicacion) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHospitalCod());
            stmt.setInt(2, entity.getDepartamentoCod());
            stmt.setString(3, entity.getNombre());
            stmt.setString(4, entity.getUbicacion());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Unidad> findAll() {
        List<Unidad> unidades = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM unidades";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Unidad unidad = new Unidad();
                unidad.setHospitalCod(rs.getInt("hospitalcod"));
                unidad.setDepartamentoCod(rs.getInt("departamentocod"));
                unidad.setUnidadCod(rs.getInt("unidadcod"));
                unidad.setNombre(rs.getString("nombre"));
                unidad.setUbicacion(rs.getString("ubicacion"));
                unidades.add(unidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidades;
    }

    @Override
    public Unidad find(int id) {
        Unidad unidad = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM unidades WHERE unidadcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                unidad = new Unidad();
                unidad.setHospitalCod(rs.getInt("hospitalcod"));
                unidad.setDepartamentoCod(rs.getInt("departamentocod"));
                unidad.setUnidadCod(rs.getInt("unidadcod"));
                unidad.setNombre(rs.getString("nombre"));
                unidad.setUbicacion(rs.getString("ubicacion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidad;
    }

    @Override
    public void update(Unidad entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE unidades SET hospitalcod = ?, departamentocod = ?, nombre = ?, ubicacion = ? WHERE unidadcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHospitalCod());
            stmt.setInt(2, entity.getDepartamentoCod());
            stmt.setString(3, entity.getNombre());
            stmt.setString(4, entity.getUbicacion());
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM unidades WHERE unidadcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countByHospitalId(int hospitalId) {
        int cant = 0;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM unidades WHERE hospitalcod = ?";
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
            String query = "SELECT COUNT(*) FROM unidades WHERE departamentocod = ?";
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

    public List<Unidad> findByDepartamentoId(int departamentoId) {
        List<Unidad> unidades = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM unidades WHERE departamentocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, departamentoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Unidad unidad = new Unidad();
                unidad.setHospitalCod(rs.getInt("hospitalcod"));
                unidad.setDepartamentoCod(rs.getInt("departamentocod"));
                unidad.setUnidadCod(rs.getInt("unidadcod"));
                unidad.setNombre(rs.getString("nombre"));
                unidad.setUbicacion(rs.getString("ubicacion"));
                unidades.add(unidad);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unidades;
    }

    public List<Unidad> findByHospitalAndDepartamentoId(int hospitalId, int departamentoId) {
    List<Unidad> unidades = new ArrayList<>();
    try (Connection conn = dataSource.getConnection()) {
        String query = "SELECT * FROM unidades WHERE hospitalcod = ? AND departamentocod = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, hospitalId);
        stmt.setInt(2, departamentoId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Unidad unidad = new Unidad();
            unidad.setHospitalCod(rs.getInt("hospitalcod"));
            unidad.setDepartamentoCod(rs.getInt("departamentocod"));
            unidad.setUnidadCod(rs.getInt("unidadcod"));
            unidad.setNombre(rs.getString("nombre"));
            unidad.setUbicacion(rs.getString("ubicacion"));
            unidades.add(unidad);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return unidades;
}
}