package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con las reservas.
 * Proporciona endpoints para obtener, crear, actualizar y eliminar reservas.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Obtiene todas las reservas registradas en el sistema.
     *
     * @return Lista de todas las reservas.
     */
    @GetMapping
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaService.obtenerTodasLasReservas();
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva.
     * @return Respuesta con el estado 200 (OK) y la reserva si existe, o 404 (No Encontrado) si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerReservaPorId(id)
                .map(reserva -> ResponseEntity.ok(reserva))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva reserva en el sistema.
     *
     * @param reserva Reserva a crear.
     * @return Respuesta con el estado 201 (Creado) y la reserva creada.
     */
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.crearReserva(reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    /**
     * Actualiza una reserva existente.
     *
     * @param id               ID de la reserva a actualizar.
     * @param reservaActualizada Datos actualizados de la reserva.
     * @return Respuesta con el estado 200 (OK) y la reserva actualizada si la reserva existe,
     *         o 404 (No Encontrado) si no se encuentra la reserva.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) {
        try {
            Reserva reserva = reservaService.actualizarReserva(id, reservaActualizada);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param id ID de la reserva a eliminar.
     * @return Respuesta con el estado 204 (Sin Contenido) si la eliminacion fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}