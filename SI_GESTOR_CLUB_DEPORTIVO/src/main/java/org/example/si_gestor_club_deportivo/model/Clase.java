package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Clase que representa una clase en el sistema deportivo.
 * Esta clase es mapeada a la tabla "clase" en la base de datos
 * y contiene la información relacionada con las clases disponibles en el centro deportivo.
 */
@Entity
@Table(name = "clase")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "profesor", nullable = false, length = 50)
    private String profesor;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "max_asistentes", nullable = false)
    private Integer maxAsistentes;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /**
     * Constructor vacío para la clase `Clase`.
     * Este constructor es utilizado por JPA para la creación de objetos de la clase.
     */
    public Clase() {}

    /**
     * Obtiene el identificador único de la clase.
     * @return El identificador de la clase.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la clase.
     * @param id El identificador de la clase.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la clase.
     * @return El nombre de la clase.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la clase.
     * @param nombre El nombre de la clase.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del profesor que imparte la clase.
     * @return El nombre del profesor.
     */
    public String getProfesor() {
        return profesor;
    }

    /**
     * Establece el nombre del profesor que imparte la clase.
     * @param profesor El nombre del profesor.
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    /**
     * Obtiene el tipo de la clase (ej. Yoga, Pilates, etc.).
     * @return El tipo de clase.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la clase.
     * @param tipo El tipo de clase.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción de la clase.
     * @return La descripción de la clase.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la clase.
     * @param descripcion La descripción de la clase.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el número máximo de asistentes a la clase.
     * @return El número máximo de asistentes.
     */
    public Integer getMaxAsistentes() {
        return maxAsistentes;
    }

    /**
     * Establece el número máximo de asistentes a la clase.
     * @param maxAsistentes El número máximo de asistentes.
     */
    public void setMaxAsistentes(Integer maxAsistentes) {
        this.maxAsistentes = maxAsistentes;
    }

    /**
     * Obtiene el precio de la clase.
     * @return El precio de la clase.
     */
    public BigDecimal getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la clase.
     * @param precio El precio de la clase.
     */
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}