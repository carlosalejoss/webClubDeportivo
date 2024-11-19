package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.example.si_gestor_club_deportivo.repository.ReservaClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaClaseService {

    @Autowired
    private ReservaClaseRepository reservaClaseRepository;

    /**
     * Devuelve todas las reservas de clases.
     *
     * @return Lista de todas las reservas.
     */
    public List<ReservaClase> obtenerTodasLasReservas() {
        return reservaClaseRepository.findAll();
    }

    /**
     * Busca una reserva específica por su ID.
     *
     * @param id ID de la reserva.
     * @return Reserva encontrada o vacía si no existe.
     */
    public Optional<ReservaClase> obtenerReservaPorId(Long id) {
        return reservaClaseRepository.findById(id);
    }


    /**
     * Actualiza una reserva existente.
     *
     * @param reserva Reserva con datos actualizados.
     * @return Reserva actualizada.
     */
    public ReservaClase actualizarReserva(ReservaClase reserva) {
        if (reserva.getId() == null) {
            throw new IllegalArgumentException("El ID de la reserva no puede ser nulo.");
        }
        return reservaClaseRepository.save(reserva);
    }


    public List<ReservaClase> obtenerReservasClasesUsuarioFechas(LocalDate inicio, LocalDate fin, Long userId) {
        return reservaClaseRepository.findReservasClasesByUsuarioAndFechaRange(userId, inicio, fin);
    }

    public List<ReservaClase> obtenerReservasUsuarioExcluyendoRangoFechas(LocalDate inicio, LocalDate fin, Long userId){
        return reservaClaseRepository.findReservasClasesByUsuarioExcludingFechaRange(userId, inicio, fin);
    }

    // Método para eliminar una clase  por su ID
    public void eliminarClase(Long id) {
        reservaClaseRepository.deleteById(id);
    }

}

