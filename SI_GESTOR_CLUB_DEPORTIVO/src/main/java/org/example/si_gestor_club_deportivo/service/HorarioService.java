package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.model.Horario;
import org.example.si_gestor_club_deportivo.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la logica de negocio relacionada con los horarios de las clases.
 * Proporciona metodos para gestionar los horarios en el sistema.
 */
@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioClaseRepository;

    /**
     * Devuelve todos los horarios registrados en el sistema.
     *
     * @return Lista de horarios.
     */
    public List<Horario> obtenerTodosLosHorarios() {
        return horarioClaseRepository.findAll();
    }

    /**
     * Crea un nuevo horario para una clase.
     *
     * @param horario El horario a crear.
     * @return El horario creado.
     */
    public Horario crearHorario(Horario horario) {
        return horarioClaseRepository.save(horario);
    }

    /**
     * Actualiza un horario existente en el sistema.
     *
     * @param horario El horario con los datos actualizados.
     * @return El horario actualizado.
     * @throws IllegalArgumentException Si el ID del horario es nulo.
     */
    public Horario actualizarHorario(Horario horario) {
        if (horario.getId() == null) {
            throw new IllegalArgumentException("El ID del horario no puede ser nulo.");
        }
        return horarioClaseRepository.save(horario);
    }

    /**
     * Elimina un horario por su ID.
     *
     * @param id El ID del horario a eliminar.
     */
    public void eliminarHorario(Long id) {
        horarioClaseRepository.deleteById(id);
    }

    /**
     * Obtiene todos los horarios asociados a una clase especifica, ordenados por fecha y hora de inicio.
     *
     * @param claseId El ID de la clase.
     * @return Una lista de horarios para la clase especificada.
     */
    public List<Horario> obtenerHorariosPorClase(Long claseId) {
        return horarioClaseRepository.findByClaseIdOrderByFechaAndHoraInicio(claseId);
    }

    /**
     * Obtiene un horario por su ID.
     *
     * @param id El ID del horario.
     * @return El horario encontrado o {@code null} si no existe.
     */
    public Horario obtenerHorarioPorId(Long id) {
        return horarioClaseRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo horario en el sistema.
     *
     * @param nuevoHorario El horario a guardar.
     */
    public void guardarHorario(Horario nuevoHorario) {
        horarioClaseRepository.save(nuevoHorario);
    }
}