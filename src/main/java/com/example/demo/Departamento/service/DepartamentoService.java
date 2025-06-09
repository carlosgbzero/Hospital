package com.example.demo.Departamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Departamento.repository.Departamento;
import com.example.demo.Departamento.repository.DepartamentoRepository;

@Service
public class DepartamentoService {
    
    @Autowired
    private DepartamentoRepository departamentoRepository;

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
}
