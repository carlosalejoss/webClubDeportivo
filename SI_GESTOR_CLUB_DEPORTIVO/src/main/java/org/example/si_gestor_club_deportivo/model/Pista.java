package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;

/**
 * Clase que representa una pista en el sistema.
 * Esta clase está mapeada a la tabla "pista" en la base de datos y contiene la información sobre
 * una pista deportiva, como su nombre, tipo, número de pista y una descripción.
 */
@Entity
@Table(name = "pista")
public class Pista {

    /**
     * Nombre único de la pista, es la clave primaria.
     * Este campo se mapea a la columna "nombre" de la tabla "pista".
     */
    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    /**
     * Tipo de la pista (por ejemplo, fútbol, baloncesto, tenis, etc.).
     * Este campo se mapea a la columna "tipo" de la tabla "pista".
     */
    @Column(nullable = false)
    private String tipo;

    /**
     * Número de la pista (por ejemplo, pista 1, pista 2).
     * Este campo se mapea a la columna "numero_pista" de la tabla "pista".
     */
    @Column(nullable = false)
    private int numero_pista;

    /**
     * Descripción detallada de la pista.
     * Este campo se mapea a la columna "descripcion" de la tabla "pista".
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Constructor vacío utilizado por JPA para la creación de instancias de la clase.
     */
    public Pista() {}

    /**
     * Obtiene el nombre de la pista.
     * @return El nombre de la pista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la pista.
     * @param nombre El nombre de la pista.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de la pista.
     * @return El tipo de la pista.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la pista.
     * @param tipo El tipo de la pista.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el número de la pista.
     * @return El número de la pista.
     */
    public int getNumero_pista() {
        return numero_pista;
    }

    /**
     * Establece el número de la pista.
     * @param numero_pista El número de la pista.
     */
    public void setNumero_pista(int numero_pista) {
        this.numero_pista = numero_pista;
    }

    /**
     * Obtiene la descripción de la pista.
     * @return La descripción de la pista.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la pista.
     * @param descripcion La descripción de la pista.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}