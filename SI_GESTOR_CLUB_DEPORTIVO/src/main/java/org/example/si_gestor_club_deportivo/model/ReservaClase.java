package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Clase que representa una reserva de clase en el sistema.
 * Esta clase esta mapeada a la tabla "reserva_clase" en la base de datos y contiene la informacion
 * sobre una reserva de clase, incluyendo los detalles del usuario, horario de la clase y la fecha de la reserva.
 */
@Entity
@Table(name = "reserva_clase")
public class ReservaClase {

    /**
     * Identificador unico de la reserva de clase, generado automaticamente.
     * Este campo se mapea a la columna "id" de la tabla "reserva_clase".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario asociado a la reserva de clase. Este campo se mapea a la columna "usuario_id" en la tabla "reserva_clase".
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, referencedColumnName = "id")
    private Usuario usuario;

    /**
     * Horario de la clase reservada. Este campo se mapea a la columna "horario_id" en la tabla "reserva_clase".
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id", referencedColumnName = "id")
    private Horario horario;

    /**
     * Fecha en la que se realizo la reserva. Este campo se mapea a la columna "fecha_reserva" en la tabla "reserva_clase".
     * Se define como un TIMESTAMP con valor por defecto `CURRENT_TIMESTAMP`.
     */
    @Column(name = "fecha_reserva", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaReserva;

    /**
     * Constructor vacio utilizado por JPA para la creacion de instancias de la clase.
     */
    public ReservaClase() {}

    // Getters and Setters

    /**
     * Obtiene el identificador de la reserva de clase.
     * @return El identificador de la reserva de clase.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la reserva de clase.
     * @param id El identificador de la reserva de clase.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario asociado a la reserva de clase.
     * @return El usuario asociado a la reserva de clase.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a la reserva de clase.
     * @param usuario El usuario asociado a la reserva de clase.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el horario de la clase reservada.
     * @return El horario de la clase reservada.
     */
    public Horario getHorarioClase() {
        return horario;
    }

    /**
     * Establece el horario de la clase reservada.
     * @param horarioClase El horario de la clase reservada.
     */
    public void setHorarioClase(Horario horarioClase) {
        this.horario = horarioClase;
    }

    /**
     * Obtiene la fecha en la que se realizo la reserva.
     * @return La fecha de la reserva.
     */
    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha de la reserva.
     * @param fechaReserva La fecha de la reserva.
     */
    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
}