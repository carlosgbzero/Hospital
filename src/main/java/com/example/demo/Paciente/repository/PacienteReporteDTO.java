package com.example.demo.Paciente.repository;

import lombok.Data;

@Data
public class PacienteReporteDTO {
        private int historiaclinicanum;
        private String nombreCompleto;
        private String fechaNacimiento;
        private String direccion;
        private int hospitalcod;
        private int departamentocod;
        private int unidadcod;
        private String nombreHospital;
        private String nombreDepartamento;
        private String nombreUnidad;
    }