package com.example.demo.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Usuario.repository.Usuario;
import com.example.demo.Usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public void createUsuario(@RequestBody Usuario usuario) {
        usuarioService.createUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable int id) {
        return usuarioService.getUsuarioById(id);
    }

    @PutMapping("/{id}")
    public void updateUsuario(@RequestBody Usuario usuario, @PathVariable int id) {
        usuarioService.updateUsuario(usuario, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable int id) {
        usuarioService.deleteUsuario(id);
    }
}