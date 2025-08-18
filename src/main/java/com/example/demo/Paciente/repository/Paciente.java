package com.example.demo.Paciente.repository;

import java.util.Date;
import lombok.Data;

@Data
public class Paciente {
    private int historiaClinicaNum;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String direccion;
    private int hospitalCod;
    private int departamentoCod;
    private int unidadCod;
}
