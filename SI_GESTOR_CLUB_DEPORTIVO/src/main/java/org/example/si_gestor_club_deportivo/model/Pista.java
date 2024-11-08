package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pista")
public class Pista {

    private String nombre;
    private String tipo;

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
}