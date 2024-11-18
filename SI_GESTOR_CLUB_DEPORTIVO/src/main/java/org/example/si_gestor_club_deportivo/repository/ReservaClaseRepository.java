package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.ReservaClase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaClaseRepository extends JpaRepository<ReservaClase, Long> {
    List<ReservaClase> findByHorarioId(Long horarioId);

    boolean existsByHorarioIdAndUsuarioId(Long horarioId, Long usuarioId);

    List<ReservaClase> findByUsuarioId(Long usuarioId);
}
