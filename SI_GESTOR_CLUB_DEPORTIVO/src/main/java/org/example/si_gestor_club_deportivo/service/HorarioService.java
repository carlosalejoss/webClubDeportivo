package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.model.Horario;
import org.example.si_gestor_club_deportivo.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioClaseRepository;

    /**
     * Devuelve todos los horarios registrados.
     *
     * @return Lista de horarios.
     */
    public List<Horario> obtenerTodosLosHorarios() {
        return horarioClaseRepository.findAll();
    }

    /**
     * Crea un nuevo horario.
     *
     * @param horario Horario a crear.
     * @return Horario creado.
     */
    public Horario crearHorario(Horario horario) {
        return horarioClaseRepository.save(horario);
    }

    /**
     * Actualiza un horario existente.
     *
     * @param horario Horario con datos actualizados.
     * @return Horario actualizado.
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
     * @param id ID del horario a eliminar.
     */
    public void eliminarHorario(Long id) {
        horarioClaseRepository.deleteById(id);
    }

    public List<Horario> obtenerHorariosPorClase(Long claseId) {
        return horarioClaseRepository.findByClaseId(claseId);
    }

    /**
     * Obtiene un horario por su ID.
     *
     * @param id ID del horario.
     * @return El horario encontrado o null si no existe.
     */
    public Horario obtenerHorarioPorId(Long id) {
        return horarioClaseRepository.findById(id).orElse(null);
    }

    public void guardarHorario(Horario nuevoHorario) {
        horarioClaseRepository.save(nuevoHorario);
    }
}