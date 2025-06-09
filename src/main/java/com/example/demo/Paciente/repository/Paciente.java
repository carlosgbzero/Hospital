package com.example.demo.Paciente.repository;

import java.util.Date;
import lombok.Data;

@Data
public class Paciente {
    private int historiaclinicanum;
    private String nombre;
    private String apellidos;
    private Date fechanacimiento;
    private String direccion;
    private int hospitalcod;
    private int departamentocod;
    private int unidadcod;
}
