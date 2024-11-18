package org.example.si_gestor_club_deportivo.dto;

public class PistaDTO {

    private String nombre;
    private String tipo;
    private int numero_pista;
    private String descripcion;

    // Constructor vacío
    public PistaDTO() {}

    // Constructor con todos los campos
    public PistaDTO(String nombre, String tipo, int numero_pista, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.numero_pista = numero_pista;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumero_pista() {
        return numero_pista;
    }

    public void setNumero_pista(int numero_pista) {
        this.numero_pista = numero_pista;
    }

    public String getDescripcion() {return this.descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

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