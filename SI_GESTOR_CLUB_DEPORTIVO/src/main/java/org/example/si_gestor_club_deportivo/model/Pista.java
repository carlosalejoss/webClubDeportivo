package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pista")
public class Pista {

    @Id
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int numero_pista;

    @Column(nullable = false)
    private String descripcion;

    // Constructor vacío para JPA
    public Pista() {}

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

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
}