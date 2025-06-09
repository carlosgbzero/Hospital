package com.example.demo.Turno.repository;

import lombok.Data;
import java.util.Date;

@Data
public class Turno {
    private int turnonum;
    private int medicocod;
    private int hospitalcod;
    private int departamentocod;
    private int unidadcod;
    private Date fechahorainicio;
    private Date fechahorafin;
    private int pacientesasignados;
}
