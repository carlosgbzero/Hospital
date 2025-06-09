package com.example.demo.RegistroPaciente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.RegistroPaciente.repository.RegistroPaciente;
import com.example.demo.RegistroPaciente.repository.RegistroPacienteRepository;

@Service
public class RegistroPacienteService {
    
    @Autowired
    private RegistroPacienteRepository registroPacienteRepository;

    public void createRegistroPaciente(RegistroPaciente registroPaciente) {
        registroPacienteRepository.create(registroPaciente);
    }

    public List<RegistroPaciente> getAllRegistrosPaciente() {
        return registroPacienteRepository.findAll();
    }

    public RegistroPaciente getRegistroPacienteById(int id) {
        return registroPacienteRepository.find(id);
    }

    public void updateRegistroPaciente(RegistroPaciente registroPaciente, int id) {
        registroPacienteRepository.update(registroPaciente, id);
    }

    public void deleteRegistroPaciente(int id) {
        registroPacienteRepository.delete(id);
    }
}
