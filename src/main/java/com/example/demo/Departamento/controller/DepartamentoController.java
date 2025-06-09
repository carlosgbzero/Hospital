package com.example.demo.Departamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Departamento.repository.Departamento;
import com.example.demo.Departamento.service.DepartamentoService;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    public void createDepartamento(@RequestBody Departamento departamento) {
        departamentoService.createDepartamento(departamento);
    }

    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoService.getAllDepartamentos();
    }

    @GetMapping("/{id}")
    public Departamento getDepartamentoById(@PathVariable int id) {
        return departamentoService.getDepartamentoById(id);
    }

    @PutMapping("/{id}")
    public void updateDepartamento(@RequestBody Departamento departamento, @PathVariable int id) {
        departamentoService.updateDepartamento(departamento, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartamento(@PathVariable int id) {
        departamentoService.deleteDepartamento(id);
    }
}
