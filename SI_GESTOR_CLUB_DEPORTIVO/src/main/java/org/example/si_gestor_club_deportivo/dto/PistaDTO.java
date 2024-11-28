package org.example.si_gestor_club_deportivo.dto;

/**
 * Clase DTO (Data Transfer Object) que representa una pista deportiva.
 * Esta clase se utiliza para transferir la informacion de una pista
 * entre diferentes capas de la aplicacion.
 */
public class PistaDTO {

    private String nombre;
    private String tipo;
    private int numero_pista;
    private String descripcion;

    /**
     * Constructor vacio de la clase PistaDTO.
     * Se utiliza para crear una instancia de PistaDTO sin inicializar los campos.
     */
    public PistaDTO() {}

    /**
     * Constructor con todos los campos de la pista.
     * @param nombre Nombre de la pista.
     * @param tipo Tipo de deporte de la pista (por ejemplo, 'Tenis', 'Futbol', etc.).
     * @param numero_pista Numero de identificacion de la pista.
     * @param descripcion Descripcion adicional sobre la pista.
     */
    public PistaDTO(String nombre, String tipo, int numero_pista, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.numero_pista = numero_pista;
        this.descripcion = descripcion;
    }

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
     * @return El tipo de la pista (por ejemplo, 'Tenis', 'Futbol', etc.).
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la pista.
     * @param tipo El tipo de la pista (por ejemplo, 'Tenis', 'Futbol', etc.).
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el numero de la pista.
     * @return El numero de identificacion de la pista.
     */
    public int getNumero_pista() {
        return numero_pista;
    }

    /**
     * Establece el numero de la pista.
     * @param numero_pista El numero de identificacion de la pista.
     */
    public void setNumero_pista(int numero_pista) {
        this.numero_pista = numero_pista;
    }

    /**
     * Obtiene la descripcion de la pista.
     * @return La descripcion de la pista.
     */
    public String getDescripcion() {
        return this.descripcion;
    }

    /**
     * Establece la descripcion de la pista.
     * @param descripcion La descripcion de la pista.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve una representacion en formato String de la instancia de PistaDTO.
     * @return Una cadena de texto que representa la pista.
     */
    @Override
    public String toString() {
        return "PistaDTO{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", numero_pista=" + numero_pista +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}