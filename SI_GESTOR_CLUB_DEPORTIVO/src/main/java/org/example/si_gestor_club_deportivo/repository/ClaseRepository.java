package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de la entidad {@link Clase}.
 * Esta interfaz extiende {@link JpaRepository}, lo que permite realizar operaciones CRUD
 * sobre la tabla "clase" en la base de datos de manera sencilla.
 */
@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    /**
     * Obtiene una lista de los tipos de clases disponibles de manera distinta.
     * Esta consulta utiliza el operador DISTINCT para evitar duplicados en los tipos de clases.
     *
     * @return Una lista de tipos de clases únicos.
     */
    @Query("SELECT DISTINCT c.tipo FROM Clase c")
    List<String> findDistinctTipos();

    /**
     * Obtiene una lista de clases según el tipo.
     *
     * @param tipo El tipo de clase que se desea buscar.
     * @return Una lista de clases que coinciden con el tipo especificado.
     */
    List<Clase> findByTipo(String tipo);

}