package com.example.demo.Medico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Medico.repository.MedicoReporteDTO;
import com.example.demo.Medico.repository.MedicoRepository;

public class ReporteMedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public List<MedicoReporteDTO> obtenerMedicosParaReporte(Integer hospitalcod, Integer departamentocod, Integer unidadcod) {
        return medicoRepository.findByFiltro(hospitalcod, departamentocod, unidadcod); 
    }

}
