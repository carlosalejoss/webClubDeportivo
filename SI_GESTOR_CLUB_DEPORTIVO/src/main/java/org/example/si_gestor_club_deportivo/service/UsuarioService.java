package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestion de usuarios en el sistema.
 * Este servicio permite realizar operaciones CRUD sobre los usuarios, como registrar, obtener, actualizar, y eliminar usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a registrar.
     */
    public void registrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Guarda un usuario en la base de datos (crea o actualiza).
     *
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Obtiene todos los usuarios administradores.
     *
     * @return Lista de usuarios administradores.
     */
    public List<Usuario> obtenerAdministradores() {
        return usuarioRepository.findAllByEsAdmin(true);
    }

    /**
     * Obtiene todos los usuarios normales (no administradores).
     *
     * @return Lista de usuarios normales.
     */
    public List<Usuario> obtenerUsuariosNormales() {
        return usuarioRepository.findAllByEsAdmin(false);
    }

    /**
     * Busca un usuario por su correo electronico.
     *
     * @param email El correo electronico del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Busca un usuario por su DNI.
     *
     * @param dni El DNI del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario findByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    /**
     * Busca un usuario por su numero de telefono.
     *
     * @param telefono El numero de telefono del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuario findByTelefono(String telefono) {
        return usuarioRepository.findByTelefono(telefono);
    }

    /**
     * Obtiene un usuario por su token de restablecimiento de contraseña.
     *
     * @param token El token de restablecimiento de contraseña.
     * @return El usuario asociado al token o null si no existe.
     */
    public Usuario obtenerUsuarioPorToken(String token) {
        return usuarioRepository.findByPasswordResetToken(token);
    }

    /**
     * Getter para obtener el repositorio de usuarios.
     *
     * @return El repositorio de usuarios.
     */
    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    /**
     * Actualiza la contraseña de un usuario sin encriptar.
     * Este metodo se utiliza para restablecer la contraseña de un usuario.
     *
     * @param email            El correo electronico del usuario.
     * @param nuevaContrasegna La nueva contraseña a establecer.
     * @return true si la contraseña fue actualizada correctamente, false si no se encontro el usuario.
     */
    public boolean actualizarContrasegnaSinEncriptar(String email, String nuevaContrasegna) {
        System.out.println("Buscando usuario con email: " + email);
        email = email.trim().toLowerCase(); // Normalizacion
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null) {
            System.out.println("Usuario encontrado: " + usuario.getEmail());
            usuario.setPassword(nuevaContrasegna);
            usuarioRepository.save(usuario);
            System.out.println("Contraseña guardada correctamente.");
            return true;
        }

        System.out.println("Usuario no encontrado.");
        return false;
    }
}
