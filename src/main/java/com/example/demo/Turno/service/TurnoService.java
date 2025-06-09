package com.example.demo.Turno.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Turno.repository.Turno;
import com.example.demo.Turno.repository.TurnoRepository;

@Service
public class TurnoService {
    
    @Autowired
    private TurnoRepository turnoRepository;

    public void createTurno(Turno turno) {
        turnoRepository.create(turno);
    }

    public List<Turno> getAllTurnos() {
        return turnoRepository.findAll();
    }

    public Turno getTurnoById(int id) {
        return turnoRepository.find(id);
    }

    public void updateTurno(Turno turno, int id) {
        turnoRepository.update(turno, id);
    }

    public void deleteTurno(int id) {
        turnoRepository.delete(id);
    }
}
