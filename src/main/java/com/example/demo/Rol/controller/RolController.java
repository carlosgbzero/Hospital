package com.example.demo.Rol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Rol.repository.Rol;
import com.example.demo.Rol.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping
    public void createRol(@RequestBody Rol rol) {
        rolService.createRol(rol);
    }

    @GetMapping
    public List<Rol> getAllRoles() {
        return rolService.getAllRoles();
    }

    @GetMapping("/{id}")
    public Rol getRolById(@PathVariable int id) {
        return rolService.getRolById(id);
    }

    @PutMapping("/{id}")
    public void updateRol(@RequestBody Rol rol, @PathVariable int id) {
        rolService.updateRol(rol, id);
    }

    @DeleteMapping("/{id}")
    public void deleteRol(@PathVariable int id) {
        rolService.deleteRol(id);
    }
}