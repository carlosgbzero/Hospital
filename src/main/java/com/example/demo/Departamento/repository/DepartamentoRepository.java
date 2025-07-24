package com.example.demo.Departamento.repository;

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
import com.example.demo.Departamento.repository.Departamento; 

@Repository
public class DepartamentoRepository implements CRUD<Departamento> {
    
    @Autowired
    private Conexion conexion;

    @Autowired
    private DataSource dataSource;
    
    public void create(Departamento entity) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "INSERT INTO departamentos (hospitalcod, nombre) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHospitalCod());
            stmt.setString(2, entity.getNombre());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public List<Departamento> findAll() {
        List<Departamento> departamentos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM departamentos";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Departamento departamento = Departamento.builder()
                    .hospitalCod(rs.getInt("hospitalcod"))
                    .departamentocod(rs.getInt("departamentocod"))
                    .nombre(rs.getString("nombre"))
                    .build();
                departamentos.add(departamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departamentos;
    }

    @Override
    public Departamento find(int id) {
        Departamento departamento = null;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM departamentos WHERE departamentocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                departamento = Departamento.builder()
                    .hospitalCod(rs.getInt("hospitalcod"))
                    .departamentocod(rs.getInt("departamentocod"))
                    .nombre(rs.getString("nombre"))
                    .build();}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departamento;
    }

    @Override
    public void update(Departamento entity, int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE departamentos SET hospitalcod = ?, nombre = ? WHERE departamentocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, entity.getHospitalCod());
            stmt.setString(2, entity.getNombre());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String query = "DELETE FROM departamentos WHERE departamentocod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los departamentos de un hospital
    public List<Departamento> findByHospitalId(int hospitalId) {
        List<Departamento> departamentos = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * FROM departamentos WHERE hospitalcod = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, hospitalId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Departamento departamento = Departamento.builder()
                    .hospitalCod(rs.getInt("hospitalcod"))
                    .departamentocod(rs.getInt("departamentocod"))
                    .nombre(rs.getString("nombre"))
                    .build();
                departamentos.add(departamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departamentos;
    }

    // Método corregido para contar departamentos por hospital
    public int countByHospitalId(int hospitalId) {
        int cant = 0;
        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT COUNT(*) FROM departamentos WHERE hospitalcod = ?";
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
}
