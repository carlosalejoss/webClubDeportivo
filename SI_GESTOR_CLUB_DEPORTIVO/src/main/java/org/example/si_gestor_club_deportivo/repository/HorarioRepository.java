package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio de la entidad {@link Horario}.
 * Esta interfaz extiende {@link JpaRepository} para permitir realizar operaciones CRUD
 * sobre la tabla "horario_clase" en la base de datos.
 */
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    /**
     * Obtiene una lista de horarios de clases ordenada por fecha y hora de inicio.
     *
     * @param claseId El ID de la clase para la cual se desean obtener los horarios.
     * @return Una lista de objetos {@link Horario} ordenada primero por la fecha y luego
     *         por la hora de inicio de manera ascendente.
     */
    @Query("SELECT h FROM Horario h WHERE h.clase.id = :claseId ORDER BY h.fecha ASC, h.horaInicio ASC")
    List<Horario> findByClaseIdOrderByFechaAndHoraInicio(@Param("claseId") Long claseId);

}