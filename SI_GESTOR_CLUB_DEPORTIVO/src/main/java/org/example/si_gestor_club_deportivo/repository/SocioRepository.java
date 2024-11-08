package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
	// Puedes agregar métodos personalizados aquí si es necesario

}
