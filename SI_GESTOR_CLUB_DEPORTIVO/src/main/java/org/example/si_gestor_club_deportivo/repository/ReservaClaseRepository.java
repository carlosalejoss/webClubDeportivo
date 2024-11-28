package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio de la entidad {@link ReservaClase}.
 * Esta interfaz extiende {@link JpaRepository} para realizar operaciones CRUD sobre la tabla "reserva_clase"
 * en la base de datos, relacionada con las reservas de clases.
 */
public interface ReservaClaseRepository extends JpaRepository<ReservaClase, Long> {

    /**
     * Encuentra todas las reservas asociadas a un horario especifico.
     *
     * @param horarioId El identificador del horario de la clase.
     * @return Una lista de reservas que corresponden al horario especificado.
     */
    List<ReservaClase> findByHorarioId(Long horarioId);

    /**
     * Verifica si un usuario tiene una reserva para un horario especifico.
     *
     * @param horarioId El identificador del horario de la clase.
     * @param usuarioId El identificador del usuario.
     * @return {@code true} si el usuario tiene una reserva para el horario especificado, de lo contrario {@code false}.
     */
    boolean existsByHorarioIdAndUsuarioId(Long horarioId, Long usuarioId);

    /**
     * Encuentra todas las reservas realizadas por un usuario.
     *
     * @param usuarioId El identificador del usuario.
     * @return Una lista de todas las reservas realizadas por el usuario especificado.
     */
    List<ReservaClase> findByUsuarioId(Long usuarioId);

    /**
     * Encuentra las reservas de clases realizadas por un usuario dentro de un rango de fechas especifico.
     *
     * @param userId El identificador del usuario.
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Una lista de reservas de clases realizadas por el usuario dentro del rango de fechas especificado.
     */
    @Query("SELECT r FROM ReservaClase r WHERE r.usuario.id = :userId AND r.horario.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<ReservaClase> findReservasClasesByUsuarioAndFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    /**
     * Encuentra las reservas de clases realizadas por un usuario fuera de un rango de fechas especifico.
     *
     * @param userId El identificador del usuario.
     * @param fechaInicio La fecha de inicio del rango que se desea excluir.
     * @param fechaFin La fecha de fin del rango que se desea excluir.
     * @return Una lista de reservas de clases realizadas por el usuario fuera del rango de fechas especificado.
     */
    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :userId AND (r.fecha < :fechaInicio OR r.fecha > :fechaFin)")
    List<ReservaClase> findReservasClasesByUsuarioExcludingFechaRange(@Param("userId") Long userId, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

}