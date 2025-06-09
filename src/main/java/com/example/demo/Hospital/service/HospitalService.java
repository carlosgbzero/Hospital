package com.example.demo.Hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Hospital.repository.Hospital;
import com.example.demo.Hospital.repository.HospitalRepository;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService() {
        this.hospitalRepository = new HospitalRepository();
    }

    public void createHospital(Hospital hospital) {
        hospitalRepository.create(hospital);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(int id) {
        return hospitalRepository.find(id);
    }

    public void updateHospital(Hospital hospital, int id) {
        hospitalRepository.update(hospital, id);
    }

    public void deleteHospital(int id) {
        hospitalRepository.delete(id);
    }
}
