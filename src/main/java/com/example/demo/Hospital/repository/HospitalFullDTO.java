package com.example.demo.Hospital.repository;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class HospitalFullDTO {
    private int hospitalCod;
    private String nombre;
    private List<DepartmentDTO> departments;
    private int patientCount;

    @Data
    @Builder
    public static class DepartmentDTO {
        private int departamentoCod;
        private String nombre;
        private List<UnitDTO> units;
    }

    @Data
    @Builder
    public static class UnitDTO {
        private int unidadCod;
        private String nombre;
    }
}
