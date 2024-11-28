package org.example.si_gestor_club_deportivo.dto;

/**
 * Clase DTO (Data Transfer Object) que representa a un usuario del sistema.
 * Esta clase se utiliza para transferir la informacion del usuario
 * entre diferentes capas de la aplicacion.
 */
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String dni;
    private String email;
    private boolean esAdmin;

    /**
     * Obtiene el identificador unico del usuario.
     * @return El identificador del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador unico del usuario.
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
     * Obtiene el numero de telefono del usuario.
     * @return El telefono del usuario.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el numero de telefono del usuario.
     * @param telefono El numero de telefono del usuario.
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
     * Obtiene el correo electronico del usuario.
     * @return El correo electronico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electronico del usuario.
     * @param email El correo electronico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene si el usuario es administrador.
     * @return Verdadero si el usuario es administrador, falso si no lo es.
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }

    /**
     * Establece si el usuario es administrador.
     * @param esAdmin Verdadero si el usuario es administrador, falso si no lo es.
     */
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}