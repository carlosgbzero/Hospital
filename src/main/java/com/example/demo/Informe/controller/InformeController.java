package com.example.demo.Informe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Informe.repository.Informe;
import com.example.demo.Informe.service.InformeService;

@RestController
@RequestMapping("/informes")
public class InformeController {

    @Autowired
    private InformeService informeService;

    @PostMapping
    public void createInforme(@RequestBody Informe informe) {
        informeService.createInforme(informe);
    }

    @GetMapping
    public List<Informe> getAllInformes() {
        return informeService.getAllInformes();
    }

    @GetMapping("/{id}")
    public Informe getInformeById(@PathVariable int id) {
        return informeService.getInformeById(id);
    }

    @PutMapping("/{id}")
    public void updateInforme(@RequestBody Informe informe, @PathVariable int id) {
        informeService.updateInforme(informe, id);
    }

    @DeleteMapping("/{id}")
    public void deleteInforme(@PathVariable int id) {
        informeService.deleteInforme(id);
    }
}
