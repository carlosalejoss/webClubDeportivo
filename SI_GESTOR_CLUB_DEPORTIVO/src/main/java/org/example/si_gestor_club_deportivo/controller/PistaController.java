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

/**
 * Controlador para gestionar las operaciones relacionadas con las pistas.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar pistas.
 */
@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    private final PistaService pistaService;

    @Autowired
    public PistaController(PistaService pistaService) {
        this.pistaService = pistaService;
    }

    /**
     * Obtiene todas las pistas registradas en el sistema.
     *
     * @return Lista de todas las pistas.
     */
    @GetMapping
    public List<Pista> obtenerTodasLasPistas() {
        return pistaService.obtenerTodasLasPistas();
    }

    /**
     * Obtiene una pista por su nombre.
     *
     * @param nombre Nombre de la pista.
     * @return Respuesta con el estado 200 (OK) y la pista si existe, o 404 (No Encontrado) si no existe.
     */
    @GetMapping("/{nombre}")
    public ResponseEntity<Pista> obtenerPistaPorNombre(@PathVariable String nombre) {
        return pistaService.obtenerPistaPorNombre(nombre)
                .map(pista -> ResponseEntity.ok(pista))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva pista en el sistema.
     *
     * @param pista Pista a crear.
     * @return Respuesta con el estado 201 (Creado) y la pista creada.
     */
    @PostMapping
    public ResponseEntity<Pista> crearPista(@RequestBody Pista pista) {
        Pista nuevaPista = pistaService.crearPista(pista);
        return new ResponseEntity<>(nuevaPista, HttpStatus.CREATED);
    }

    /**
     * Actualiza una pista existente en el sistema.
     *
     * @param nombre          Nombre de la pista a actualizar.
     * @param pistaActualizada Datos actualizados de la pista.
     * @return Respuesta con el estado 200 (OK) y la pista actualizada si la pista existe,
     *         o 404 (No Encontrado) si no se encuentra la pista.
     */
    @PutMapping("/{nombre}")
    public ResponseEntity<Pista> actualizarPista(@PathVariable String nombre, @RequestBody Pista pistaActualizada) {
        try {
            Pista pista = pistaService.actualizarPista(nombre, pistaActualizada);
            return ResponseEntity.ok(pista);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una pista por su nombre.
     *
     * @param nombre Nombre de la pista a eliminar.
     * @return Respuesta con el estado 204 (Sin Contenido) si la eliminacion fue exitosa.
     */
    @DeleteMapping("/{nombre}")
    public ResponseEntity<Void> eliminarPista(@PathVariable String nombre) {
        pistaService.eliminarPista(nombre);
        return ResponseEntity.noContent().build();
    }
}