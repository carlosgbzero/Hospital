package com.example.demo.Medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Medico.repository.Medico;
import com.example.demo.Medico.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public void createMedico(@RequestBody Medico medico) {
        medicoService.createMedico(medico);
    }

    @GetMapping
    public List<Medico> getAllMedicos() {
        return medicoService.getAllMedicos();
    }

    @GetMapping("/{id}")
    public Medico getMedicoById(@PathVariable int id) {
        return medicoService.getMedicoById(id);
    }

    @PutMapping("/{id}")
    public void updateMedico(@RequestBody Medico medico, @PathVariable int id) {
        medicoService.updateMedico(medico, id);
    }

    @DeleteMapping("/{id}")
    public void deleteMedico(@PathVariable int id) {
        medicoService.deleteMedico(id);
    }
}
