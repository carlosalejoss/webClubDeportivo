package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
	// Puedes definir métodos personalizados aquí si es necesario
}