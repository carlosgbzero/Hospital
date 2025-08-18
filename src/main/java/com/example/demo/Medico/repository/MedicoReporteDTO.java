package com.example.demo.Medico.repository;

import lombok.Data;

@Data
public class MedicoReporteDTO{
    private String nombreCompleto;
    private String especialidad;
    private String numLicencia;
    private String telefono;
    private int anosExperiencia;
    private String contacto;
    private String nombreHospital;
    private String nombreDepartamento;
    private String nombreUnidad;
}