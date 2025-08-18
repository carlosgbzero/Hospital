package com.example.demo.Hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Hospital.repository.HospitalRepository;
import com.example.demo.Hospital.repository.ReporteHospitalDTO;

public class ReporteHospitalService {
     @Autowired
    private HospitalRepository hospitalRepository;

    public List<ReporteHospitalDTO> obtenerHospitalesParaReporte() {
        return hospitalRepository.findAllWithCount();
    }

     public List<ReporteHospitalDTO> obtenerHospitalesTopParaReporte() {
        return hospitalRepository.findWithMostPatients();
    }
}
