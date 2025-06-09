package com.example.demo.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.config.Conexion;

@Component
public class DatabaseConnectionChecker implements CommandLineRunner {
    
    @Autowired
    private Conexion conexion;

    @Override
    public void run(String... args) {
        try {
            System.out.println("Comprobando conexión a la base de datos...");
            if (conexion.getConnection() != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("No se pudo establecer conexión a la base de datos.");
            }
        } catch (Exception e) {
            System.out.println("Error al intentar conectar a la base de datos:");
            e.printStackTrace();
        } finally {
            conexion.closeConnection();
        }
    }
}
