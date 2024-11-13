package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista, String> {
	// Puedes definir métodos personalizados aquí si es necesario
}