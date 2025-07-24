package com.example.demo.Departamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Departamento.repository.Departamento;
import com.example.demo.Departamento.repository.DepartamentoRepository;
import com.example.demo.Departamento.repository.DepartmentWithUnitsDTO;
import com.example.demo.Unidad.repository.UnidadRepository;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private UnidadRepository unidadRepository;

    public void createDepartamento(Departamento departamento) {
        departamentoRepository.create(departamento);
    }

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento getDepartamentoById(int id) {
        return departamentoRepository.find(id);
    }

    public void updateDepartamento(Departamento departamento, int id) {
        departamentoRepository.update(departamento, id);
    }

    public void deleteDepartamento(int id) {
        departamentoRepository.delete(id);
    }

    public List<DepartmentWithUnitsDTO> getDepartmentsWithUnits() {
        return departamentoRepository.findAll().stream().map(dep -> {
            var unidades = unidadRepository.findByDepartamentoId(dep.getDepartamentocod());
            var unitsDTO = unidades.stream().map(uni ->
                DepartmentWithUnitsDTO.UnitDTO.builder()
                    .unidadCod(uni.getUnidadCod())
                    .nombre(uni.getNombre())
                    .ubicacion(uni.getUbicacion())
                    .build()
            ).toList();

            return DepartmentWithUnitsDTO.builder()
                .departamentoCod(dep.getDepartamentocod())
                .nombre(dep.getNombre())
                .hospitalCod(dep.getHospitalCod())
                .units(unitsDTO)
                .build();
        }).toList();
    }
}
