package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaClaseRepository extends JpaRepository<ReservaClase, Long> {
    List<ReservaClase> findByHorarioId(Long horarioId);

    boolean existsByHorarioIdAndUsuarioId(Long horarioId, Long usuarioId);

    List<ReservaClase> findByUsuarioId(Long usuarioId);

    @Query("SELECT r FROM ReservaClase r WHERE r.usuario.id = :userId AND r.horario.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<ReservaClase> findReservasClasesByUsuarioAndFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND (r.fecha < :fechaInicio OR r.fecha > :fechaFin)")
    List<ReservaClase> findReservasClasesByUsuarioExcludingFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}
