package org.example.si_gestor_club_deportivo.controller;

import org.example.si_gestor_club_deportivo.dto.UsuarioDTO;
import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/administradores")
    public List<Usuario> obtenerAdministradores() {
        return usuarioService.obtenerAdministradores();
    }

    @GetMapping("/usuarios")
    public List<Usuario> obtenerUsuariosNormales() {
        return usuarioService.obtenerUsuariosNormales();
    }
}