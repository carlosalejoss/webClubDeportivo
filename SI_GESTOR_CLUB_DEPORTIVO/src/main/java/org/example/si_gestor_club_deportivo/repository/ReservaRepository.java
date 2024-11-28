package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de la entidad {@link Reserva}.
 * Esta interfaz extiende {@link JpaRepository} para realizar operaciones CRUD sobre la tabla "reserva"
 * en la base de datos, relacionada con las reservas de las pistas.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Encuentra todas las reservas de una pista dentro de un rango de fechas específico.
     *
     * @param inicio La fecha de inicio del rango.
     * @param fin La fecha de fin del rango.
     * @param pistaNombre El nombre de la pista.
     * @return Una lista de reservas dentro del rango de fechas para la pista especificada.
     */
    List<Reserva> findByFechaBetweenAndPistaNombre(LocalDate inicio, LocalDate fin, String pistaNombre);

    /**
     * Verifica si un usuario tiene una reserva en una fecha específica.
     *
     * @param user_id El identificador del usuario.
     * @param fecha La fecha de la reserva.
     * @return {@code true} si el usuario tiene una reserva en esa fecha, de lo contrario {@code false}.
     */
    boolean existsByUsuarioIdAndFecha(Long user_id, LocalDate fecha);

    /**
     * Encuentra todas las reservas realizadas por un usuario dentro de un rango de fechas específico.
     *
     * @param userId El identificador del usuario.
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Una lista de reservas realizadas por el usuario dentro del rango de fechas especificado.
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND r.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Reserva> findReservasByUsuarioAndFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    /**
     * Encuentra todas las reservas realizadas por un usuario fuera de un rango de fechas específico.
     *
     * @param userId El identificador del usuario.
     * @param fechaInicio La fecha de inicio del rango a excluir.
     * @param fechaFin La fecha de fin del rango a excluir.
     * @return Una lista de reservas realizadas por el usuario fuera del rango de fechas especificado.
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND (r.fecha < :fechaInicio OR r.fecha > :fechaFin)")
    List<Reserva> findReservasByUsuarioExcludingFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}