package com.example.demo.Rol.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Rol.repository.Rol;
import com.example.demo.Rol.repository.RolRepository;

@Service
public class RolService {
    
    @Autowired
    private RolRepository rolRepository;

    public void createRol(Rol rol) {
        rolRepository.create(rol);
    }

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Rol getRolById(int id) {
        return rolRepository.find(id);
    }

    public void updateRol(Rol rol, int id) {
        rolRepository.update(rol, id);
    }

    public void deleteRol(int id) {
        rolRepository.delete(id);
    }
}