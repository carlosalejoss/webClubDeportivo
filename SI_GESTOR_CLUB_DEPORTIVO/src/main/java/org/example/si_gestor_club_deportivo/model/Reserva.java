package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una reserva en el sistema.
 * Esta clase está mapeada a la tabla "reserva" en la base de datos y contiene la información
 * sobre una reserva, incluyendo los detalles del usuario, pista, fecha, hora y precio.
 */
@Entity
@Table(name = "reserva")
public class Reserva {

    /**
     * Identificador único de la reserva, generado automáticamente.
     * Este campo se mapea a la columna "id" de la tabla "reserva".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario asociado a la reserva. Este campo se mapea a la columna "user_id" en la tabla "reserva".
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Usuario usuario;

    /**
     * Pista asociada a la reserva. Este campo se mapea a la columna "pista_nombre" en la tabla "reserva".
     */
    @ManyToOne
    @JoinColumn(name = "pista_nombre", referencedColumnName = "nombre")
    private Pista pista;

    /**
     * Fecha de la reserva. Este campo se mapea a la columna "fecha" en la tabla "reserva".
     */
    @Column(name = "fecha")
    private LocalDate fecha;

    /**
     * Hora de inicio de la reserva. Este campo se mapea a la columna "horainicio" en la tabla "reserva".
     */
    @Column(name = "horainicio")
    private LocalTime horaInicio;

    /**
     * Hora de fin de la reserva. Este campo se mapea a la columna "horafin" en la tabla "reserva".
     */
    @Column(name = "horafin")
    private LocalTime horaFin;

    /**
     * Precio de la reserva. Este campo se mapea a la columna "precio" en la tabla "reserva".
     */
    @Column(name = "precio")
    private Double precio;

    /**
     * Constructor vacío utilizado por JPA para la creación de instancias de la clase.
     */
    public Reserva() {}

    // Getters y Setters

    /**
     * Obtiene el identificador de la reserva.
     * @return El identificador de la reserva.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la reserva.
     * @param id El identificador de la reserva.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario asociado a la reserva.
     * @return El usuario asociado a la reserva.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a la reserva.
     * @param usuario El usuario asociado a la reserva.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la pista asociada a la reserva.
     * @return La pista asociada a la reserva.
     */
    public Pista getPista() {
        return pista;
    }

    /**
     * Establece la pista asociada a la reserva.
     * @param pista La pista asociada a la reserva.
     */
    public void setPista(Pista pista) {
        this.pista = pista;
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
     * Obtiene el rango de horas de la reserva en formato "HH:mm - HH:mm".
     * @return El rango de horas de la reserva.
     */
    public String getHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return horaInicio.format(formatter) + " - " + horaFin.format(formatter);
    }

    /**
     * Establece la hora de fin de la reserva.
     * @param horaFin La hora de fin de la reserva.
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Obtiene la hora de fin de la reserva.
     * @return La hora de fin de la reserva.
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de inicio de la reserva.
     * @param horaInicio La hora de inicio de la reserva.
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de inicio de la reserva.
     * @return La hora de inicio de la reserva.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece tanto la hora de inicio como la hora de fin de la reserva.
     * @param horaInicio La hora de inicio de la reserva.
     * @param horaFin La hora de fin de la reserva.
     */
    public void setHora(LocalTime horaInicio, LocalTime horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /**
     * Obtiene el precio de la reserva.
     * @return El precio de la reserva.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la reserva.
     * @param precio El precio de la reserva.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}