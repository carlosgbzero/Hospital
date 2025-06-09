package com.example.demo.Turno.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Turno.repository.Turno;
import com.example.demo.Turno.service.TurnoService;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @PostMapping
    public void createTurno(@RequestBody Turno turno) {
        turnoService.createTurno(turno);
    }

    @GetMapping
    public List<Turno> getAllTurnos() {
        return turnoService.getAllTurnos();
    }

    @GetMapping("/{id}")
    public Turno getTurnoById(@PathVariable int id) {
        return turnoService.getTurnoById(id);
    }

    @PutMapping("/{id}")
    public void updateTurno(@RequestBody Turno turno, @PathVariable int id) {
        turnoService.updateTurno(turno, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTurno(@PathVariable int id) {
        turnoService.deleteTurno(id);
    }
}
