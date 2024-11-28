package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.example.si_gestor_club_deportivo.repository.ReservaClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de reservas de clases.
 * Este servicio permite obtener, crear, actualizar y eliminar reservas de clases.
 */
@Service
public class ReservaClaseService {

    @Autowired
    private ReservaClaseRepository reservaClaseRepository;

    /**
     * Devuelve todas las reservas de clases registradas en la base de datos.
     *
     * @return Lista de todas las reservas de clases.
     */
    public List<ReservaClase> obtenerTodasLasReservas() {
        return reservaClaseRepository.findAll();
    }

    /**
     * Busca una reserva específica de clase por su ID.
     *
     * @param id ID de la reserva.
     * @return Un objeto Optional con la reserva encontrada o vacío si no existe.
     */
    public Optional<ReservaClase> obtenerReservaPorId(Long id) {
        return reservaClaseRepository.findById(id);
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     * Si el ID de la reserva es nulo, se lanza una excepción.
     *
     * @param reserva Reserva con datos actualizados.
     * @return La reserva actualizada.
     * @throws IllegalArgumentException Si el ID de la reserva es nulo.
     */
    public ReservaClase actualizarReserva(ReservaClase reserva) {
        if (reserva.getId() == null) {
            throw new IllegalArgumentException("El ID de la reserva no puede ser nulo.");
        }
        return reservaClaseRepository.save(reserva);
    }

    /**
     * Obtiene todas las reservas de clases de un usuario dentro de un rango de fechas específico.
     *
     * @param inicio  Fecha de inicio del rango.
     * @param fin     Fecha de fin del rango.
     * @param userId  ID del usuario.
     * @return Lista de reservas de clases realizadas por el usuario dentro del rango de fechas.
     */
    public List<ReservaClase> obtenerReservasClasesUsuarioFechas(LocalDate inicio, LocalDate fin, Long userId) {
        return reservaClaseRepository.findReservasClasesByUsuarioAndFechaRange(userId, inicio, fin);
    }

    /**
     * Obtiene todas las reservas de clases de un usuario excluyendo un rango de fechas específico.
     *
     * @param inicio  Fecha de inicio del rango.
     * @param fin     Fecha de fin del rango.
     * @param userId  ID del usuario.
     * @return Lista de reservas de clases realizadas por el usuario fuera del rango de fechas.
     */
    public List<ReservaClase> obtenerReservasUsuarioExcluyendoRangoFechas(LocalDate inicio, LocalDate fin, Long userId){
        return reservaClaseRepository.findReservasClasesByUsuarioExcludingFechaRange(userId, inicio, fin);
    }

    /**
     * Elimina una reserva de clase por su ID.
     *
     * @param id ID de la reserva a eliminar.
     */
    public void eliminarClase(Long id) {
        reservaClaseRepository.deleteById(id);
    }

}