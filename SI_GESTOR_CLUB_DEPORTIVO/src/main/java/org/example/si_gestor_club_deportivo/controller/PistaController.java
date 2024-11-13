package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.service.PistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    private final PistaService pistaService;

    @Autowired
    public PistaController(PistaService pistaService) {
        this.pistaService = pistaService;
    }

    // Endpoint para obtener todas las pistas
    @GetMapping
    public List<Pista> obtenerTodasLasPistas() {
        return pistaService.obtenerTodasLasPistas();
    }

    // Endpoint para obtener una pista por su nombre
    @GetMapping("/{nombre}")
    public ResponseEntity<Pista> obtenerPistaPorNombre(@PathVariable String nombre) {
        return pistaService.obtenerPistaPorNombre(nombre)
                .map(pista -> ResponseEntity.ok(pista))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para crear una nueva pista
    @PostMapping
    public ResponseEntity<Pista> crearPista(@RequestBody Pista pista) {
        Pista nuevaPista = pistaService.crearPista(pista);
        return new ResponseEntity<>(nuevaPista, HttpStatus.CREATED);
    }

    // Endpoint para actualizar una pista existente
    @PutMapping("/{nombre}")
    public ResponseEntity<Pista> actualizarPista(@PathVariable String nombre, @RequestBody Pista pistaActualizada) {
        try {
            Pista pista = pistaService.actualizarPista(nombre, pistaActualizada);
            return ResponseEntity.ok(pista);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar una pista
    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarPista(@PathVariable String nombre) {
        pistaService.eliminarPista(nombre);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/eleccion")
    public String mostrarPistasPorTipo(@RequestParam("tipo") String tipo, Model model) {
        List<Pista> pistas = pistaService.obtenerPistasPorTipo(tipo);
        model.addAttribute("pistas", pistas);
        model.addAttribute("tipo", tipo); // Pasamos el tipo para usar en el título dinámico
        return "eleccionCampo";
    }
}