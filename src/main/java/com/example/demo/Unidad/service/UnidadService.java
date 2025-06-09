package com.example.demo.Unidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Unidad.repository.Unidad;
import com.example.demo.Unidad.repository.UnidadRepository;

@Service
public class UnidadService {
    
    @Autowired
    private UnidadRepository unidadRepository;

    public void createUnidad(Unidad unidad) {
        unidadRepository.create(unidad);
    }

    public List<Unidad> getAllUnidades() {
        return unidadRepository.findAll();
    }

    public Unidad getUnidadById(int id) {
        return unidadRepository.find(id);
    }

    public void updateUnidad(Unidad unidad, int id) {
        unidadRepository.update(unidad, id);
    }

    public void deleteUnidad(int id) {
        unidadRepository.delete(id);
    }
}
