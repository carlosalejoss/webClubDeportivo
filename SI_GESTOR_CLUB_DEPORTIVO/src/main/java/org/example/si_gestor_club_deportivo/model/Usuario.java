package org.example.si_gestor_club_deportivo.model;

import jakarta.persistence.*;

/**
 * Clase que representa a un usuario en el sistema.
 * Esta clase esta mapeada a la tabla "usuario" en la base de datos y contiene los datos asociados
 * a un usuario, como su nombre, apellidos, telefono, DNI, email, contraseña y si es un administrador o no.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /**
     * Identificador unico del usuario, generado automaticamente.
     * Este campo se mapea a la columna "id" de la tabla "usuario".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario.
     * Este campo se mapea a la columna "nombre" de la tabla "usuario" y no puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Apellidos del usuario.
     * Este campo se mapea a la columna "apellidos" de la tabla "usuario" y no puede ser nulo.
     */
    @Column(nullable = false)
    private String apellidos;

    /**
     * Telefono del usuario.
     * Este campo se mapea a la columna "telefono" de la tabla "usuario" y no puede ser nulo.
     */
    @Column(nullable = false)
    private String telefono;

    /**
     * DNI del usuario. Debe ser unico y no puede ser nulo.
     * Este campo se mapea a la columna "dni" de la tabla "usuario".
     */
    @Column(unique = true, nullable = false)
    private String dni;

    /**
     * Email del usuario. Debe ser unico y no puede ser nulo.
     * Este campo se mapea a la columna "email" de la tabla "usuario".
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Contraseña del usuario. No puede ser nula.
     * Este campo se mapea a la columna "password" de la tabla "usuario".
     */
    @Column(nullable = false)
    private String password;

    /**
     * Indicador de si el usuario es administrador o no. Este campo no puede ser nulo.
     * Este campo se mapea a la columna "esadmin" de la tabla "usuario".
     */
    @Column(name = "esadmin", nullable = false)
    private boolean esAdmin;

    /**
     * Token utilizado para el restablecimiento de la contraseña.
     * Este campo se mapea a la columna "password_reset_token" de la tabla "usuario".
     */
    @Column(name = "password_reset_token")
    private String passwordResetToken;

    // Getters y Setters

    /**
     * Obtiene el identificador del usuario.
     * @return El identificador del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     * @param id El identificador del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * @param apellidos Los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el telefono del usuario.
     * @return El telefono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el telefono del usuario.
     * @param telefono El telefono del usuario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el DNI del usuario.
     * @return El DNI del usuario.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del usuario.
     * @param dni El DNI del usuario.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el email del usuario.
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param password La contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Indica si el usuario es un administrador.
     * @return true si el usuario es administrador, false en caso contrario.
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }

    /**
     * Establece si el usuario es un administrador.
     * @param esAdmin true si el usuario es administrador, false en caso contrario.
     */
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    /**
     * Obtiene el token de restablecimiento de la contraseña.
     * @return El token de restablecimiento de la contraseña.
     */
    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    /**
     * Establece el token de restablecimiento de la contraseña.
     * @param passwordResetToken El token de restablecimiento de la contraseña.
     */
    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}