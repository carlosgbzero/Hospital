package com.example.demo.Paciente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Paciente.repository.Paciente;
import com.example.demo.Paciente.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public void createPaciente(@RequestBody Paciente paciente) {
        pacienteService.createPaciente(paciente);
    }

    @GetMapping
    public List<Paciente> getAllPacientes() {
        return pacienteService.getAllPacientes();
    }

    @GetMapping("/{id}")
    public Paciente getPacienteById(@PathVariable int id) {
        return pacienteService.getPacienteById(id);
    }

    @PutMapping("/{id}")
    public void updatePaciente(@RequestBody Paciente paciente, @PathVariable int id) {
        pacienteService.updatePaciente(paciente, id);
    }

    @DeleteMapping("/{id}")
    public void deletePaciente(@PathVariable int id) {
        pacienteService.deletePaciente(id);
    }
}
