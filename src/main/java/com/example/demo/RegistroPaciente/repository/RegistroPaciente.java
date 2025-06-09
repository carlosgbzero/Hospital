package com.example.demo.RegistroPaciente.repository;

import lombok.Data;
import java.util.Date;

@Data
public class RegistroPaciente {
    private int registroid;
    private int historiaclinicanum;
    private Date fechaalta;
    private Date fechabaja;
    private boolean atendido;
    private String causanoatencion;
}
