package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Conexion {
    private static Connection connection = null;

    @Value("${bd.username}")
    private String user;

    @Value("${bd.password}")
    private String password;

    @Value("${bd.url}")
    private String url;

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Registrar el driver de PostgreSQL
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectarse con la Base de Datos");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("No se encontró el driver de PostgreSQL");
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada con éxito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
