package com.example.demo.Informe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Informe.repository.Informe;
import com.example.demo.Informe.repository.InformeRepository;

@Service
public class InformeService {
    
    @Autowired
    private InformeRepository informeRepository;

    public void createInforme(Informe informe) {
        informeRepository.create(informe);
    }

    public List<Informe> getAllInformes() {
        return informeRepository.findAll();
    }

    public Informe getInformeById(int id) {
        return informeRepository.find(id);
    }

    public void updateInforme(Informe informe, int id) {
        informeRepository.update(informe, id);
    }

    public void deleteInforme(int id) {
        informeRepository.delete(id);
    }
}
