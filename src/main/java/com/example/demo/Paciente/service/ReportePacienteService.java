package com.example.demo.Paciente.service;

import com.example.demo.Paciente.repository.PacienteRepository;
import com.example.demo.Paciente.repository.Paciente;
import com.example.demo.Paciente.repository.PacienteReporteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportePacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteReporteDTO> obtenerPacientesParaReporte(Integer hospitalcod, Integer departamentocod, Integer unidadcod) {
        return pacienteRepository.findByFiltro(hospitalcod, departamentocod, unidadcod);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
}
