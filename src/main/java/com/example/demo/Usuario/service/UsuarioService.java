package com.example.demo.Usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Usuario.repository.Usuario;
import com.example.demo.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void createUsuario(Usuario usuario) {
        usuarioRepository.create(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepository.find(id);
    }

    public void updateUsuario(Usuario usuario, int id) {
        usuarioRepository.update(usuario, id);
    }

    public void deleteUsuario(int id) {
        usuarioRepository.delete(id);
    }
}