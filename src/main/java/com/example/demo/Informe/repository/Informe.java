package com.example.demo.Informe.repository;

import lombok.Data;
import java.util.Date;

@Data
public class Informe {
    private int informenum;
    private int turnonum;
    private Date fechahora;
    private int pacientesatendidos;
    private int pacientesaltas;
    private int pacientesadmitidos;
    private int totalactual;
}
