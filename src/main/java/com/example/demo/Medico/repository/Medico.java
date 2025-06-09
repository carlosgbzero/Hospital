package com.example.demo.Medico.repository;

import lombok.Data;

@Data
public class Medico {
    private int medicocod;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String licenciamedica;
    private String telefono;
    private int anosexperiencia;
    private String contacto;
    private int hospitalcod;
    private int departamentocod;
    private int unidadcod;
}
