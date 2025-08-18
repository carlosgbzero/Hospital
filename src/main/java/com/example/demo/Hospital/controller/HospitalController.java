package com.example.demo.Hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Hospital.repository.Hospital;
import com.example.demo.Hospital.repository.HospitalFullDTO;
import com.example.demo.Hospital.service.HospitalService;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping
    public void createHospital(@RequestBody Hospital hospital) {
        hospitalService.createHospital(hospital);
    }

    @GetMapping
    public List<HospitalFullDTO> getAllHospitalsFull(
        @RequestParam(required = false, defaultValue = "false") boolean includeDepartments,
        @RequestParam(required = false, defaultValue = "false") boolean includeCounts
    ) {
        if (includeDepartments && includeCounts) {
            return hospitalService.getHospitalsFull();
        }
        // Puedes agregar otras variantes según los parámetros
        return hospitalService.getAllHospitals().stream()
            .map(h -> HospitalFullDTO.builder()
                .hospitalCod(h.getHospitalcod())
                .nombre(h.getNombre())
                .build())
            .toList();
    }

    @GetMapping("/resumen")
    public List<Hospital> getAllHospitalsResumen() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/{id}")
    public Hospital getHospitalById(@PathVariable int id) {
        return hospitalService.getHospitalById(id);
    }

    @PutMapping("/{id}")
    public void updateHospital(@RequestBody Hospital hospital, @PathVariable int id) {
        hospitalService.updateHospital(hospital, id);
    }

    @DeleteMapping("/{id}")
    public void deleteHospital(@PathVariable int id) {
        hospitalService.deleteHospital(id);
    }
}
