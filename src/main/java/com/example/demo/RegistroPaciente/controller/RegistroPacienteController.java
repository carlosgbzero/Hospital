package com.example.demo.RegistroPaciente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.RegistroPaciente.repository.RegistroPaciente;
import com.example.demo.RegistroPaciente.service.RegistroPacienteService;

@RestController
@RequestMapping("/registros-paciente")
public class RegistroPacienteController {

    @Autowired
    private RegistroPacienteService registroPacienteService;

    @PostMapping
    public void createRegistroPaciente(@RequestBody RegistroPaciente registroPaciente) {
        registroPacienteService.createRegistroPaciente(registroPaciente);
    }

    @GetMapping
    public List<RegistroPaciente> getAllRegistrosPaciente() {
        return registroPacienteService.getAllRegistrosPaciente();
    }

    @GetMapping("/{id}")
    public RegistroPaciente getRegistroPacienteById(@PathVariable int id) {
        return registroPacienteService.getRegistroPacienteById(id);
    }

    @PutMapping("/{id}")
    public void updateRegistroPaciente(@RequestBody RegistroPaciente registroPaciente, @PathVariable int id) {
        registroPacienteService.updateRegistroPaciente(registroPaciente, id);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistroPaciente(@PathVariable int id) {
        registroPacienteService.deleteRegistroPaciente(id);
    }
}
