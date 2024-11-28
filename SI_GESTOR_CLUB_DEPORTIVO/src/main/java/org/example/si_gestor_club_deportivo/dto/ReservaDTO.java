package org.example.si_gestor_club_deportivo.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase DTO (Data Transfer Object) que representa una reserva de pista.
 * Esta clase se utiliza para transferir la informacion de una reserva
 * entre diferentes capas de la aplicacion.
 */
public class ReservaDTO {

    private Long id;
    private Long userId;
    private String pistaNombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double precio;

    /**
     * Constructor vacio de la clase ReservaDTO.
     * Se utiliza para crear una instancia de ReservaDTO sin inicializar los campos.
     */
    public ReservaDTO() {}

    /**
     * Constructor con todos los campos de la reserva.
     * @param id El identificador unico de la reserva.
     * @param userId El identificador del usuario que realiza la reserva.
     * @param pistaNombre El nombre de la pista que se ha reservado.
     * @param fecha La fecha en la que se realiza la reserva.
     * @param horaInicio La hora de inicio de la reserva.
     * @param horaFin La hora de fin de la reserva.
     * @param precio El precio total de la reserva.
     */
    public ReservaDTO(Long id, Long userId, String pistaNombre, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Double precio) {
        this.id = id;
        this.userId = userId;
        this.pistaNombre = pistaNombre;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador unico de la reserva.
     * @return El identificador de la reserva.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador unico de la reserva.
     * @param id El identificador de la reserva.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el identificador del usuario que realizo la reserva.
     * @return El identificador del usuario.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Establece el identificador del usuario que realizo la reserva.
     * @param userId El identificador del usuario.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Obtiene el nombre de la pista reservada.
     * @return El nombre de la pista.
     */
    public String getPistaNombre() {
        return pistaNombre;
    }

    /**
     * Establece el nombre de la pista reservada.
     * @param pistaNombre El nombre de la pista.
     */
    public void setPistaNombre(String pistaNombre) {
        this.pistaNombre = pistaNombre;
    }

    /**
     * Obtiene la fecha de la reserva.
     * @return La fecha de la reserva.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la reserva.
     * @param fecha La fecha de la reserva.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora de inicio de la reserva.
     * @return La hora de inicio de la reserva.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la reserva.
     * @param horaInicio La hora de inicio de la reserva.
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de fin de la reserva.
     * @return La hora de fin de la reserva.
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de fin de la reserva.
     * @param horaFin La hora de fin de la reserva.
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el precio de la reserva.
     * @return El precio total de la reserva.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la reserva.
     * @param precio El precio total de la reserva.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve una representacion en formato String de la instancia de ReservaDTO.
     * @return Una cadena de texto que representa la reserva.
     */
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