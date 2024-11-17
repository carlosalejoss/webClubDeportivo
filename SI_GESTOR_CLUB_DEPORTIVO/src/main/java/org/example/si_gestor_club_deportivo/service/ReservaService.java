package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Método para obtener todas las reservas
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    // Método para obtener una reserva por su ID
    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Método para crear una nueva reserva
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Método para actualizar una reserva existente
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

    // Método para eliminar una reserva por su ID
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> obtenerReservasEntreFechas(LocalDate inicio, LocalDate fin, String campo) {
        return reservaRepository.findByFechaBetweenAndPistaNombre(inicio, fin, campo);
    }

    public boolean obtenerReservaUsuarioFecha(Long user_id, LocalDate fecha){
        return reservaRepository.existsByUsuarioIdAndFecha(user_id, fecha);
    }

    public List<Reserva> obtenerReservasUsuarioFechas(LocalDate inicio, LocalDate fin, Long userId) {
        return reservaRepository.findReservasByUsuarioAndFechaRange(userId, inicio, fin);
    }

    public List<Reserva> obtenerReservasUsuarioExcluyendoRangoFechas(LocalDate inicio, LocalDate fin, Long userId){
        return reservaRepository.findReservasByUsuarioExcludingFechaRange(userId, inicio, fin);
    }

}