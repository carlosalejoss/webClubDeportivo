package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Horario;
import org.example.si_gestor_club_deportivo.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByClaseId(Long claseId);
}
