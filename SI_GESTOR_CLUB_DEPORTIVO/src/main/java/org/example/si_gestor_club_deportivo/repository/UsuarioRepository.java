package org.example.si_gestor_club_deportivo.repository;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio de la entidad {@link Usuario}.
 * Esta interfaz extiende {@link JpaRepository} para realizar operaciones CRUD sobre la tabla "usuario"
 * en la base de datos, relacionada con los usuarios del sistema.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra todos los usuarios que son administradores.
     *
     * @param esAdmin El valor que indica si el usuario es administrador o no.
     * @return Una lista de usuarios que son administradores si {@code esAdmin} es {@code true},
     *         o usuarios que no son administradores si {@code esAdmin} es {@code false}.
     */
    List<Usuario> findAllByEsAdmin(boolean esAdmin);

    /**
     * Encuentra un usuario por su correo electronico.
     * Este metodo es util para la autenticacion de usuarios.
     *
     * @param email El correo electronico del usuario.
     * @return El usuario correspondiente al correo electronico proporcionado.
     */
    Usuario findByEmail(String email);

    /**
     * Encuentra un usuario por su DNI.
     *
     * @param dni El DNI del usuario.
     * @return El usuario correspondiente al DNI proporcionado.
     */
    Usuario findByDni(String dni);

    /**
     * Encuentra un usuario por su numero de telefono.
     *
     * @param telefono El numero de telefono del usuario.
     * @return El usuario correspondiente al numero de telefono proporcionado.
     */
    Usuario findByTelefono(String telefono);

    /**
     * Encuentra un usuario por su token de restablecimiento de contraseña.
     * Este metodo es util para recuperar el token cuando se solicita un restablecimiento de contraseña.
     *
     * @param token El token de restablecimiento de contraseña.
     * @return El usuario correspondiente al token proporcionado.
     */
    Usuario findByPasswordResetToken(String token);
}