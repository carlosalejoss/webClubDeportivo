package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.repository.PistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PistaService {

    private final PistaRepository pistaRepository;

    @Autowired
    public PistaService(PistaRepository pistaRepository) {
        this.pistaRepository = pistaRepository;
    }

    // Método para obtener todas las pistas
    public List<Pista> obtenerTodasLasPistas() {
        return pistaRepository.findAll();
    }

    // Método para obtener una pista por su nombre
    public Optional<Pista> obtenerPistaPorNombre(String nombre) {
        return pistaRepository.findById(nombre);
    }

    // Método para crear una nueva pista
    public Pista crearPista(Pista pista) {
        return pistaRepository.save(pista);
    }

    // Método para actualizar una pista existente
    public Pista actualizarPista(String nombre, Pista pistaActualizada) {
        return pistaRepository.findById(nombre)
                .map(pista -> {
                    pista.setTipo(pistaActualizada.getTipo());
                    return pistaRepository.save(pista);
                })
                .orElseThrow(() -> new RuntimeException("Pista no encontrada con nombre " + nombre));
    }

    // Método para eliminar una pista por su nombre
    public void eliminarPista(String nombre) {
        pistaRepository.deleteById(nombre);
    }

    public List<Pista> obtenerPistasPorTipo(String tipo) {
        return pistaRepository.findByTipo(tipo);
    }

    public Pista obtenerUltimaPistaPorTipo(String tipo) {
        return pistaRepository.findByTipoOnly(tipo);
    }
}