package com.example.demo.Departamento.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Departamento {
    private int hospitalCod;
    private int departamentocod;
    private String nombre;
}
