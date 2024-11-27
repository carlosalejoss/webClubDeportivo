package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    @Query("SELECT h FROM Horario h WHERE h.clase.id = :claseId ORDER BY h.fecha ASC, h.horaInicio ASC")
    List<Horario> findByClaseIdOrderByFechaAndHoraInicio(@Param("claseId") Long claseId);

}
