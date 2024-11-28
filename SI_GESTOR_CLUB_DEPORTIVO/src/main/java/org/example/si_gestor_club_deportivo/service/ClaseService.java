package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Clase;
import org.example.si_gestor_club_deportivo.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de negocio relacionada con las clases.
 * Proporciona métodos para gestionar las clases en el sistema.
 */
@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    /**
     * Devuelve todas las clases disponibles en el sistema.
     *
     * @return Una lista de todas las clases.
     */
    public List<Clase> obtenerTodasLasClases() {
        return claseRepository.findAll();
    }

    /**
     * Crea una nueva clase y la guarda en la base de datos.
     *
     * @param clase La clase a crear.
     * @return La clase creada.
     */
    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

    /**
     * Actualiza los datos de una clase existente.
     *
     * @param clase La clase con los datos actualizados.
     * @return La clase actualizada.
     * @throws IllegalArgumentException Si el ID de la clase es nulo.
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
     * @param id El ID de la clase a eliminar.
     */
    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }

    /**
     * Obtiene los tipos distintos de clases disponibles en el sistema.
     *
     * @return Una lista de tipos de clases.
     */
    public List<String> obtenerTiposDeClases() {
        return claseRepository.findDistinctTipos();
    }

    /**
     * Obtiene una lista de clases filtradas por su tipo.
     *
     * @param tipo El tipo de clase a filtrar.
     * @return Una lista de clases del tipo especificado.
     */
    public List<Clase> obtenerClasesPorTipo(String tipo) {
        return claseRepository.findByTipo(tipo);
    }

    /**
     * Obtiene una clase por su ID.
     *
     * @param id El ID de la clase.
     * @return La clase con el ID especificado, o {@code null} si no se encuentra.
     */
    public Clase obtenerClasePorId(Long id) {
        return claseRepository.findById(id).orElse(null);
    }

    /**
     * Obtiene una lista de clases por una lista de IDs.
     *
     * @param ids Una lista de IDs de las clases.
     * @return Una lista de clases correspondientes a los IDs especificados.
     */
    public List<Clase> obtenerClasesPorIds(List<Long> ids) {
        return claseRepository.findAllById(ids);
    }

    /**
     * Guarda una clase en la base de datos.
     *
     * @param nuevaClase La clase a guardar.
     */
    public void guardarClase(Clase nuevaClase) {
        claseRepository.save(nuevaClase);
    }
}
