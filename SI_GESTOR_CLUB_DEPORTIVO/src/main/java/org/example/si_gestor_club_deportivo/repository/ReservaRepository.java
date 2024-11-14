package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	// Puedes definir métodos personalizados aquí si es necesario
    List<Reserva> findByFechaBetween(LocalDate inicio, LocalDate fin);
}