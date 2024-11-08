package org.example.si_gestor_club_deportivo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDTO {

    private Long id;
    private Long userId;
    private String pistaNombre;
    private LocalDate fecha;
    private LocalTime hora;
    private Double precio;

    // Constructor vacío
    public ReservaDTO() {}

    // Constructor con todos los campos
    public ReservaDTO(Long id, Long userId, String pistaNombre, LocalDate fecha, LocalTime hora, Double precio) {
        this.id = id;
        this.userId = userId;
        this.pistaNombre = pistaNombre;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPistaNombre() {
        return pistaNombre;
    }

    public void setPistaNombre(String pistaNombre) {
        this.pistaNombre = pistaNombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ReservaDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", pistaNombre='" + pistaNombre + '\'' +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", precio=" + precio +
                '}';
    }
}