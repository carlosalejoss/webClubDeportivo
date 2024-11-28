package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Clase que representa un horario de clase en el sistema.
 * Esta clase esta mapeada a la tabla "horario_clase" en la base de datos y contiene la informacion sobre
 * el horario de las clases, incluyendo la fecha, la hora de inicio y la hora de fin.
 */
@Entity
@Table(name = "horario_clase")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacion con la clase a la que pertenece este horario.
     * La clase es referenciada por su identificador en la columna "clase_id".
     */
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", nullable = false, referencedColumnName = "id")
    private Clase clase;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    /**
     * Constructor vacio para la clase `Horario`.
     * Este constructor es utilizado por JPA para la creacion de objetos de la clase.
     */
    public Horario() {}

    /**
     * Obtiene el identificador unico del horario.
     * @return El identificador del horario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador unico del horario.
     * @param id El identificador del horario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la clase asociada a este horario.
     * @return La clase asociada al horario.
     */
    public Clase getClase() {
        return clase;
    }

    /**
     * Establece la clase asociada a este horario.
     * @param clase La clase a asociar con este horario.
     */
    public void setClase(Clase clase) {
        this.clase = clase;
    }

    /**
     * Obtiene la fecha en la que se imparte este horario de clase.
     * @return La fecha del horario.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en la que se imparte este horario de clase.
     * @param fecha La fecha del horario.
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la hora de inicio de la clase.
     * @return La hora de inicio de la clase.
     */
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Establece la hora de inicio de la clase.
     * @param horaInicio La hora de inicio de la clase.
     */
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Obtiene la hora de fin de la clase.
     * @return La hora de fin de la clase.
     */
    public LocalTime getHoraFin() {
        return horaFin;
    }

    /**
     * Establece la hora de fin de la clase.
     * @param horaFin La hora de fin de la clase.
     */
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}