package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;

/**
 * Clase que representa una pista en el sistema.
 * Esta clase esta mapeada a la tabla "pista" en la base de datos y contiene la informacion sobre
 * una pista deportiva, como su nombre, tipo, numero de pista y una descripcion.
 */
@Entity
@Table(name = "pista")
public class Pista {

    /**
     * Nombre unico de la pista, es la clave primaria.
     * Este campo se mapea a la columna "nombre" de la tabla "pista".
     */
    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    /**
     * Tipo de la pista (por ejemplo, futbol, baloncesto, tenis, etc.).
     * Este campo se mapea a la columna "tipo" de la tabla "pista".
     */
    @Column(nullable = false)
    private String tipo;

    /**
     * Numero de la pista (por ejemplo, pista 1, pista 2).
     * Este campo se mapea a la columna "numero_pista" de la tabla "pista".
     */
    @Column(nullable = false)
    private int numero_pista;

    /**
     * Descripcion detallada de la pista.
     * Este campo se mapea a la columna "descripcion" de la tabla "pista".
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Constructor vacio utilizado por JPA para la creacion de instancias de la clase.
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
     * Obtiene el numero de la pista.
     * @return El numero de la pista.
     */
    public int getNumero_pista() {
        return numero_pista;
    }

    /**
     * Establece el numero de la pista.
     * @param numero_pista El numero de la pista.
     */
    public void setNumero_pista(int numero_pista) {
        this.numero_pista = numero_pista;
    }

    /**
     * Obtiene la descripcion de la pista.
     * @return La descripcion de la pista.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripcion de la pista.
     * @param descripcion La descripcion de la pista.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}