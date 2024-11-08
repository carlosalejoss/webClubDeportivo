package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.model.Socio;
import org.example.si_gestor_club_deportivo.service.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    @Autowired
    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    // Endpoint para obtener todos los socios
    @GetMapping
    public List<Socio> obtenerTodosLosSocios() {
        return socioService.obtenerTodosLosSocios();
    }

    // Endpoint para obtener un socio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Socio> obtenerSocioPorId(@PathVariable Long id) {
        return socioService.obtenerSocioPorId(id)
                .map(socio -> ResponseEntity.ok(socio))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo socio
    @PostMapping
    public ResponseEntity<Socio> crearSocio(@RequestBody Socio socio) {
        Socio nuevoSocio = socioService.crearSocio(socio);
        return new ResponseEntity<>(nuevoSocio, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un socio existente
    @PutMapping("/{id}")
    public ResponseEntity<Socio> actualizarSocio(@PathVariable Long id, @RequestBody Socio socioActualizado) {
        try {
            Socio socio = socioService.actualizarSocio(id, socioActualizado);
            return ResponseEntity.ok(socio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un socio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        socioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    }
}