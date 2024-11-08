package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.model.Administrador;
import org.example.si_gestor_club_deportivo.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    private final AdministradorService adminService;

    @Autowired
    public AdministradorController(AdministradorService adminService) {
        this.adminService = adminService;
    }

    // Endpoint para obtener todos los administradores
    @GetMapping
    public List<Administrador> obtenerTodosLosAdministradores() {
        return adminService.obtenerTodosLosAdministradores();
    }

    // Endpoint para obtener un administrador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obtenerAdministradorPorId(@PathVariable Long id) {
        return adminService.obtenerAdministradorPorId(id)
                .map(admin -> ResponseEntity.ok(admin))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo administrador
    @PostMapping
    public ResponseEntity<Administrador> crearAdministrador(@RequestBody Administrador administrador) {
        Administrador nuevoAdmin = adminService.crearAdministrador(administrador);
        return new ResponseEntity<>(nuevoAdmin, HttpStatus.CREATED);
    }

    // Endpoint para actualizar un administrador existente
    @PutMapping("/{id}")
    public ResponseEntity<Administrador> actualizarAdministrador(@PathVariable Long id, @RequestBody Administrador administradorActualizado) {
        try {
            Administrador administrador = adminService.actualizarAdministrador(id, administradorActualizado);
            return ResponseEntity.ok(administrador);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un administrador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAdministrador(@PathVariable Long id) {
        adminService.eliminarAdministrador(id);
        return ResponseEntity.noContent().build();
    }
}