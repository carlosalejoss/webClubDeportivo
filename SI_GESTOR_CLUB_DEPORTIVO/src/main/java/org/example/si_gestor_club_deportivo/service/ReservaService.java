package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestion de reservas.
 * Este servicio permite obtener, crear, actualizar, eliminar y consultar reservas de pistas.
 */
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Obtiene todas las reservas registradas.
     *
     * @return Lista de todas las reservas.
     */
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Obtiene una reserva por su ID.
     *
     * @param id ID de la reserva.
     * @return Un objeto Optional con la reserva encontrada o vacio si no existe.
     */
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Crea una nueva reserva en la base de datos.
     *
     * @param reserva Reserva a crear.
     * @return La reserva creada.
     */
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     * Si no se encuentra la reserva con el ID proporcionado, lanza una excepcion.
     *
     * @param id ID de la reserva a actualizar.
     * @param reservaActualizada Reserva con los nuevos datos.
     * @return La reserva actualizada.
     * @throws RuntimeException Si la reserva con el ID no existe.
     */
    public Reserva actualizarReserva(Long id, Reserva reservaActualizada) {
        return reservaRepository.findById(id)
                .map(reserva -> {
                    reserva.setFecha(reservaActualizada.getFecha());
                    reserva.setHoraInicio(reservaActualizada.getHoraInicio());
                    reserva.setHoraFin(reservaActualizada.getHoraFin());
                    reserva.setPrecio(reservaActualizada.getPrecio());
                    reserva.setUsuario(reservaActualizada.getUsuario());
                    reserva.setPista(reservaActualizada.getPista());
                    return reservaRepository.save(reserva);
                })
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID " + id));
    }

    /**
     * Elimina una reserva de la base de datos por su ID.
     *
     * @param id ID de la reserva a eliminar.
     */
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    /**
     * Obtiene las reservas de una pista entre dos fechas especificas.
     *
     * @param inicio Fecha de inicio.
     * @param fin Fecha de fin.
     * @param campo Nombre de la pista.
     * @return Lista de reservas dentro del rango de fechas para la pista especificada.
     */
    public List<Reserva> obtenerReservasEntreFechas(LocalDate inicio, LocalDate fin, String campo) {
        return reservaRepository.findByFechaBetweenAndPistaNombre(inicio, fin, campo);
    }

    /**
     * Verifica si un usuario ya tiene una reserva en una fecha especifica.
     *
     * @param user_id ID del usuario.
     * @param fecha Fecha de la reserva.
     * @return true si el usuario tiene una reserva en esa fecha, false en caso contrario.
     */
    public boolean obtenerReservaUsuarioFecha(Long user_id, LocalDate fecha){
        return reservaRepository.existsByUsuarioIdAndFecha(user_id, fecha);
    }

    /**
     * Obtiene todas las reservas de un usuario entre un rango de fechas especifico.
     *
     * @param inicio Fecha de inicio.
     * @param fin Fecha de fin.
     * @param userId ID del usuario.
     * @return Lista de reservas del usuario dentro del rango de fechas.
     */
    public List<Reserva> obtenerReservasUsuarioFechas(LocalDate inicio, LocalDate fin, Long userId) {
        return reservaRepository.findReservasByUsuarioAndFechaRange(userId, inicio, fin);
    }

    /**
     * Obtiene todas las reservas de un usuario excluyendo un rango de fechas especifico.
     *
     * @param inicio Fecha de inicio.
     * @param fin Fecha de fin.
     * @param userId ID del usuario.
     * @return Lista de reservas del usuario fuera del rango de fechas.
     */
    public List<Reserva> obtenerReservasUsuarioExcluyendoRangoFechas(LocalDate inicio, LocalDate fin, Long userId){
        return reservaRepository.findReservasByUsuarioExcludingFechaRange(userId, inicio, fin);
    }
}