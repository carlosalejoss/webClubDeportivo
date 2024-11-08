package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Administrador;
import org.example.si_gestor_club_deportivo.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    @Autowired
    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    // Método para obtener todos los administradores
    public List<Administrador> obtenerTodosLosAdministradores() {
        return administradorRepository.findAll();
    }

    // Método para obtener un administrador por su ID
    public Optional<Administrador> obtenerAdministradorPorId(Long id) {
        return administradorRepository.findById(id);
    }

    // Método para crear un nuevo administrador
    public Administrador crearAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    // Método para actualizar un administrador existente
    public Administrador actualizarAdministrador(Long id, Administrador administradorActualizado) {
        return administradorRepository.findById(id)
                .map(admin -> {
                    admin.setNombre(administradorActualizado.getNombre());
                    admin.setApellidos(administradorActualizado.getApellidos());
                    admin.setTelefono(administradorActualizado.getTelefono());
                    admin.setDni(administradorActualizado.getDni());
                    admin.setEmail(administradorActualizado.getEmail());
                    admin.setPassword(administradorActualizado.getPassword());
                    return administradorRepository.save(admin);
                })
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID " + id));
    }

    // Método para eliminar un administrador por su ID
    public void eliminarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }
}