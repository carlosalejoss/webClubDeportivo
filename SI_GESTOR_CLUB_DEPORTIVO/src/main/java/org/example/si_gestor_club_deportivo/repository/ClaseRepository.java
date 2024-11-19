package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    @Query("SELECT DISTINCT c.tipo FROM Clase c")
    List<String> findDistinctTipos();

    List<Clase> findByTipo(String tipo);

}
