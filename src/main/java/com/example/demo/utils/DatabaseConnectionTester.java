package com.example.demo.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import com.example.demo.DemoApplication;
import com.example.demo.config.Conexion;

public class DatabaseConnectionTester {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        Conexion conexion = context.getBean(Conexion.class);
        
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