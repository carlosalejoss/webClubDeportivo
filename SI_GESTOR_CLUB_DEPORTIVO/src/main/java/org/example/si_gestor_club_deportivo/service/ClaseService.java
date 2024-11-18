package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    /**
     * Devuelve todas las clases disponibles.
     *
     * @return Lista de todas las clases.
     */
    public List<Clase> obtenerTodasLasClases() {
        return claseRepository.findAll();
    }

    /**
     * Crea una nueva clase.
     *
     * @param clase Clase a crear.
     * @return Clase creada.
     */
    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

    /**
     * Actualiza los datos de una clase existente.
     *
     * @param clase Clase con datos actualizados.
     * @return Clase actualizada.
     */
    public Clase actualizarClase(Clase clase) {
        if (clase.getId() == null) {
            throw new IllegalArgumentException("El ID de la clase no puede ser nulo.");
        }
        return claseRepository.save(clase);
    }

    /**
     * Elimina una clase por su ID.
     *
     * @param id ID de la clase a eliminar.
     */
    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }

    public List<String> obtenerTiposDeClases() {
        return claseRepository.findDistinctTipos();
    }

    public List<Clase> obtenerClasesPorTipo(String tipo) {
        return claseRepository.findByTipo(tipo);
    }

    public Clase obtenerClasePorId(Long id) {
        return claseRepository.findById(id).orElse(null);
    }
}
