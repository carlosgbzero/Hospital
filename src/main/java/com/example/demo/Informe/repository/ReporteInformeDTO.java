package com.example.demo.Informe.repository;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReporteInformeDTO {
    private String hospital;
    private String departamento;
    private String unidad;
    private int numeroTurno;
    private Timestamp horaInforme;
    private int numeroInforme;
    private int pacientesAlInicio;
    private int pacientesAdmitidos;
    private int pacientesDadosDeAlta;
    private int pacientesAtendidosDesdeAnterior;
    private int pacientesAtendidosDia;
}
