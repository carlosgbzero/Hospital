package com.example.demo.Paciente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Paciente.repository.Paciente;
import com.example.demo.Paciente.repository.PacienteRepository;

@Service
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;

    public void createPaciente(Paciente paciente) {
        pacienteRepository.create(paciente);
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente getPacienteById(int id) {
        return pacienteRepository.find(id);
    }

    public void updatePaciente(Paciente paciente, int id) {
        pacienteRepository.update(paciente, id);
    }

    public void deletePaciente(int id) {
        pacienteRepository.delete(id);
    }
}
