package com.example.demo.Medico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Medico.repository.Medico;
import com.example.demo.Medico.repository.MedicoRepository;

@Service
public class MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;

    public void createMedico(Medico medico) {
        medicoRepository.create(medico);
    }

    public List<Medico> getAllMedicos() {
        return medicoRepository.findAll();
    }

    public Medico getMedicoById(int id) {
        return medicoRepository.find(id);
    }

    public void updateMedico(Medico medico, int id) {
        medicoRepository.update(medico, id);
    }

    public void deleteMedico(int id) {
        medicoRepository.delete(id);
    }
}
