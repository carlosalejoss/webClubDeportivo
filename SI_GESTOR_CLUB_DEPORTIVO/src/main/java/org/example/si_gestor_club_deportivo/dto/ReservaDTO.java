package org.example.si_gestor_club_deportivo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDTO {

    private Long id;
    private Long userId;
    private String pistaNombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double precio;

    // Constructor vacío
    public ReservaDTO() {}

    // Constructor con todos los campos
    public ReservaDTO(Long id, Long userId, String pistaNombre, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Double precio) {
        this.id = id;
        this.userId = userId;
        this.pistaNombre = pistaNombre;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {return horaFin;}

    public void setHoraFin(LocalTime horaFin) {this.horaFin = horaFin;}

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
                ", hora=" + horaInicio + " - " + horaFin +
                ", precio=" + precio +
                '}';
    }
}