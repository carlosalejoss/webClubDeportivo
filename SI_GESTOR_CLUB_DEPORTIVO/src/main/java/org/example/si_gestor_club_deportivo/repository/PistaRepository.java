package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de la entidad {@link Pista}.
 * Esta interfaz extiende {@link JpaRepository} para realizar operaciones CRUD sobre la tabla "pista"
 * en la base de datos, relacionadas con las pistas deportivas.
 */
@Repository
public interface PistaRepository extends JpaRepository<Pista, String> {

    /**
     * Encuentra todas las pistas de un tipo especifico.
     *
     * @param tipo El tipo de pista que se desea buscar (por ejemplo, "Tenis", "Baloncesto").
     * @return Una lista de pistas que coinciden con el tipo especificado.
     */
    List<Pista> findByTipo(String tipo);

    /**
     * Encuentra la pista de un tipo especifico con el numero de pista mas alto.
     * Esta consulta se utiliza para encontrar la pista mas alta disponible dentro de un tipo especifico
     * (por ejemplo, la pista de baloncesto con el numero de pista mas alto).
     *
     * @param tipo El tipo de pista para la cual se desea encontrar la pista con el numero mas alto.
     * @return La pista con el numero de pista mas alto dentro del tipo especificado.
     */
    @Query("SELECT p FROM Pista p WHERE p.tipo = :tipo AND p.numero_pista = (SELECT MAX(p2.numero_pista) FROM Pista p2 WHERE p2.tipo = :tipo)")
    Pista findByTipoOnly(@Param("tipo") String tipo);

}