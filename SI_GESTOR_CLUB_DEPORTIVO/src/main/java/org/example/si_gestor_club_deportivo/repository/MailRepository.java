package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}