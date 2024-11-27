package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes añadir métodos de consulta adicionales si los necesitas
    // Método para obtener todos los administradores
    List<Usuario> findAllByEsAdmin(boolean esAdmin);

    // Método para obtener un usuario por su email (útil para autenticación)
    Usuario findByEmail(String email);

    Usuario findByDni(String dni);

    Usuario findByTelefono(String telefono);

    Usuario findByPasswordResetToken(String token);
}