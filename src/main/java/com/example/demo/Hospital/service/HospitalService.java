package com.example.demo.Hospital.service;

import com.example.demo.Hospital.repository.Hospital;
import com.example.demo.Hospital.repository.HospitalRepository;
import com.example.demo.Hospital.repository.HospitalResumenDTO;
import com.example.demo.Hospital.repository.HospitalFullDTO;
import com.example.demo.Departamento.repository.DepartamentoRepository;
import com.example.demo.Unidad.repository.UnidadRepository;
import com.example.demo.Paciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private UnidadRepository unidadRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

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

    public List<HospitalResumenDTO> obtenerResumenHospitales() {
        return hospitalRepository.findAll().stream().map(hospital -> {
            int cantidadDepartamentos = departamentoRepository.countByHospitalId(hospital.getHospitalcod());
            int cantidadUnidades = unidadRepository.countByHospitalId(hospital.getHospitalcod());
            int cantidadPacientes = pacienteRepository.countByHospitalId(hospital.getHospitalcod());
            return HospitalResumenDTO.builder()
                .id(hospital.getHospitalcod())
                .nombre(hospital.getNombre())
                .cantidadDepartamentos(cantidadDepartamentos)
                .cantidadUnidades(cantidadUnidades)
                .cantidadPacientes(cantidadPacientes)
                .build();
        }).collect(Collectors.toList());
    }

    public List<HospitalFullDTO> getHospitalsFull() {
    return hospitalRepository.findAll().stream().map(hospital -> {
        var departamentos = departamentoRepository.findByHospitalId(hospital.getHospitalcod());
        List<HospitalFullDTO.DepartmentDTO> departmentDTOs = departamentos.stream().map(dep -> {
            var unidades = unidadRepository.findByHospitalAndDepartamentoId(hospital.getHospitalcod(), dep.getDepartamentocod());
            List<HospitalFullDTO.UnitDTO> unitDTOs = unidades.stream().map(uni ->
                HospitalFullDTO.UnitDTO.builder()
                    .unidadCod(uni.getUnidadCod())
                    .nombre(uni.getNombre())
                    .build()
            ).toList();
            return HospitalFullDTO.DepartmentDTO.builder()
                .departamentoCod(dep.getDepartamentocod())
                .nombre(dep.getNombre())
                .units(unitDTOs)
                .build();
            }).toList();

        int patientCount = pacienteRepository.countByHospitalId(hospital.getHospitalcod()); 

        return HospitalFullDTO.builder()
            .hospitalCod(hospital.getHospitalcod())
            .nombre(hospital.getNombre())
            .departments(departmentDTOs)
            .patientCount(patientCount)
            .build();
        }).toList();
    }
}
