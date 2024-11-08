package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes añadir métodos de consulta adicionales si los necesitas
}