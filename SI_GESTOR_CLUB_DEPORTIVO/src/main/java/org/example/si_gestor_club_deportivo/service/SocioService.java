package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Socio;
import org.example.si_gestor_club_deportivo.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    @Autowired
    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    // Método para obtener todos los socios
    public List<Socio> obtenerTodosLosSocios() {
        return socioRepository.findAll();
    }

    // Método para obtener un socio por su ID
    public Optional<Socio> obtenerSocioPorId(Long id) {
        return socioRepository.findById(id);
    }

    // Método para crear un nuevo socio
    public Socio crearSocio(Socio socio) {
        return socioRepository.save(socio);
    }

    // Método para actualizar un socio
    public Socio actualizarSocio(Long id, Socio socioActualizado) {
        return socioRepository.findById(id)
                .map(socio -> {
                    socio.setNombre(socioActualizado.getNombre());
                    socio.setApellidos(socioActualizado.getApellidos());
                    socio.setTelefono(socioActualizado.getTelefono());
                    socio.setDni(socioActualizado.getDni());
                    socio.setEmail(socioActualizado.getEmail());
                    socio.setPassword(socioActualizado.getPassword());
                    return socioRepository.save(socio);
                })
                .orElseThrow(() -> new RuntimeException("Socio no encontrado con ID " + id));
    }

    // Método para eliminar un socio por su ID
    public void eliminarSocio(Long id) {
        socioRepository.deleteById(id);
    }
}