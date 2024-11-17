package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	// Puedes definir métodos personalizados aquí si es necesario
    List<Reserva> findByFechaBetweenAndPistaNombre(LocalDate inicio, LocalDate fin, String pistaNombre);

    boolean existsByUsuarioIdAndFecha(Long user_id, LocalDate fecha);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND r.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findReservasByUsuarioAndFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND (r.fecha < :fechaInicio OR r.fecha > :fechaFin)")
    List<Reserva> findReservasByUsuarioExcludingFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}