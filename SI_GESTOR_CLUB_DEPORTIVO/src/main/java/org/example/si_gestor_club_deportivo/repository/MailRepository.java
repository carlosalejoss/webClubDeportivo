package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de la entidad {@link Usuario}.
 * Esta interfaz extiende {@link JpaRepository} para realizar operaciones CRUD sobre la tabla "usuario"
 * en la base de datos relacionadas con la gestion de usuarios.
 */
@Repository
public interface MailRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra un usuario por su direccion de correo electronico.
     *
     * @param email El correo electronico del usuario que se desea encontrar.
     * @return Un objeto {@link Optional} que contiene el usuario encontrado, o vacio si no se encuentra.
     */
    Optional<Usuario> findByEmail(String email);

}