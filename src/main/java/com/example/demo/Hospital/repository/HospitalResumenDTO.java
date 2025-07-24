package com.example.demo.Hospital.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalResumenDTO {
    private int id;
    private String nombre;
    private int cantidadDepartamentos;
    private int cantidadUnidades;
    private int cantidadPacientes;
}