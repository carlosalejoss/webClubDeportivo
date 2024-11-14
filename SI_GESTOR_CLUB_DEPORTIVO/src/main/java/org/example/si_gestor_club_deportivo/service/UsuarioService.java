package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Usuario;
import org.example.si_gestor_club_deportivo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerAdministradores() {
        return usuarioRepository.findAllByEsAdmin(true);
    }

    public List<Usuario> obtenerUsuariosNormales() {
        return usuarioRepository.findAllByEsAdmin(false);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    public Usuario findByTelefono(String telefono) {
        return usuarioRepository.findByTelefono(telefono);
    }

    // Getter for usuarioRepository
    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

}