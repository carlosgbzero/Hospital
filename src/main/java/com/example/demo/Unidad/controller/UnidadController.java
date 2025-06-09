package com.example.demo.Unidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Unidad.repository.Unidad;
import com.example.demo.Unidad.service.UnidadService;

@RestController
@RequestMapping("/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    @PostMapping
    public void createUnidad(@RequestBody Unidad unidad) {
        unidadService.createUnidad(unidad);
    }

    @GetMapping
    public List<Unidad> getAllUnidades() {
        return unidadService.getAllUnidades();
    }

    @GetMapping("/{id}")
    public Unidad getUnidadById(@PathVariable int id) {
        return unidadService.getUnidadById(id);
    }

    @PutMapping("/{id}")
    public void updateUnidad(@RequestBody Unidad unidad, @PathVariable int id) {
        unidadService.updateUnidad(unidad, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUnidad(@PathVariable int id) {
        unidadService.deleteUnidad(id);
    }
}
