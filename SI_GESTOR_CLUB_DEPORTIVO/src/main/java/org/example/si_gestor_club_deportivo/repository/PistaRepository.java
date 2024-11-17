package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PistaRepository extends JpaRepository<Pista, String> {
    List<Pista> findByTipo(String tipo);

    @Query("SELECT p FROM Pista p WHERE p.tipo = :tipo AND p.numero_pista = (SELECT MAX(p2.numero_pista) FROM Pista p2 WHERE p2.tipo = :tipo)")
    Pista findByTipoOnly(@Param("tipo") String tipo);
    // Puedes definir métodos personalizados aquí si es necesaria
}