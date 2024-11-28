package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con los usuarios.
 * Proporciona endpoints para obtener, crear, eliminar y gestionar usuarios.
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return Lista de todos los usuarios.
     */
    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    /**
     * Guarda un nuevo usuario en el sistema.
     *
     * @param usuario El usuario a guardar.
     * @return El usuario guardado.
     */
    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     */
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    /**
     * Obtiene todos los usuarios administradores.
     *
     * @return Lista de usuarios administradores.
     */
    @GetMapping("/administradores")
    public List<Usuario> obtenerAdministradores() {
        return usuarioService.obtenerAdministradores();
    }

    /**
     * Obtiene todos los usuarios normales (no administradores).
     *
     * @return Lista de usuarios normales.
     */
    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuariosNormales() {
        return usuarioService.obtenerUsuariosNormales();
    }
}