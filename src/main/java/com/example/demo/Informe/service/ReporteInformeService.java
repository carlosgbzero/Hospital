package com.example.demo.Informe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Informe.repository.InformeRepository;
import com.example.demo.Informe.repository.ReporteInformeDTO;

public class ReporteInformeService {
    @Autowired
    private InformeRepository informeRepository;

    public List<ReporteInformeDTO> obtenerHospitalesParaReporte( int hospital, int depart, int unit) {
        return informeRepository.obtenerInformeFiltrado(hospital, depart, unit);
    }
}
