package com.example.demo.Medico.repository;

import lombok.Data;

@Data
public class Medico {
    private int medicoCod;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String licenciaMedica;
    private String telefono;
    private int anosExperiencia;
    private String contacto;
    private int hospitalCod;
    private int departamentoCod;
    private int unidadCod;
}
