package org.example.si_gestor_club_deportivo.service;

import org.example.si_gestor_club_deportivo.model.Pista;
import org.example.si_gestor_club_deportivo.repository.PistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestion de pistas deportivas.
 * Contiene metodos para obtener, crear, actualizar y eliminar pistas, asi como para obtener pistas por tipo.
 */
@Service
public class PistaService {

    private final PistaRepository pistaRepository;

    /**
     * Constructor para inicializar el servicio de pistas con el repositorio correspondiente.
     *
     * @param pistaRepository El repositorio de pistas para interactuar con la base de datos.
     */
    @Autowired
    public PistaService(PistaRepository pistaRepository) {
        this.pistaRepository = pistaRepository;
    }

    /**
     * Obtiene todas las pistas registradas en la base de datos.
     *
     * @return Lista de todas las pistas.
     */
    public List<Pista> obtenerTodasLasPistas() {
        return pistaRepository.findAll();
    }

    /**
     * Obtiene una pista por su nombre.
     *
     * @param nombre El nombre de la pista a buscar.
     * @return Un objeto Optional que contiene la pista si existe, o vacio si no se encuentra.
     */
    public Optional<Pista> obtenerPistaPorNombre(String nombre) {
        return pistaRepository.findById(nombre);
    }

    /**
     * Crea una nueva pista.
     *
     * @param pista La pista a crear.
     * @return La pista creada.
     */
    public Pista crearPista(Pista pista) {
        return pistaRepository.save(pista);
    }

    /**
     * Actualiza una pista existente.
     *
     * Si la pista con el nombre proporcionado no existe, se lanza una excepcion.
     *
     * @param nombre           El nombre de la pista a actualizar.
     * @param pistaActualizada La pista con los nuevos datos.
     * @return La pista actualizada.
     * @throws RuntimeException Si no se encuentra una pista con el nombre especificado.
     */
    public Pista actualizarPista(String nombre, Pista pistaActualizada) {
        return pistaRepository.findById(nombre)
                .map(pista -> {
                    pista.setTipo(pistaActualizada.getTipo());
                    return pistaRepository.save(pista);
                })
                .orElseThrow(() -> new RuntimeException("Pista no encontrada con nombre " + nombre));
    }

    /**
     * Elimina una pista por su nombre.
     *
     * @param nombre El nombre de la pista a eliminar.
     */
    public void eliminarPista(String nombre) {
        pistaRepository.deleteById(nombre);
    }

    /**
     * Obtiene todas las pistas de un tipo especifico.
     *
     * @param tipo El tipo de las pistas que se desean obtener.
     * @return Lista de pistas de ese tipo.
     */
    public List<Pista> obtenerPistasPorTipo(String tipo) {
        return pistaRepository.findByTipo(tipo);
    }

    /**
     * Obtiene la ultima pista registrada de un tipo especifico, basada en el numero de pista mas alto.
     *
     * @param tipo El tipo de la pista que se desea obtener.
     * @return La ultima pista del tipo especificado.
     */
    public Pista obtenerUltimaPistaPorTipo(String tipo) {
        return pistaRepository.findByTipoOnly(tipo);
    }
}