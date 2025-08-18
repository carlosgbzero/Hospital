package com.example.demo.Departamento.repository;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class DepartmentWithUnitsDTO {
    private int departamentoCod;
    private String nombre;
    private int hospitalCod;
    private List<UnitDTO> units;

    @Builder
    @Getter
    public static class UnitDTO {
        private int unidadCod;
        private String nombre;
        private String ubicacion;
    }
}