package org.example.si_gestor_club_deportivo.dto;

public class UsuarioDTO {
    private Long userId;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id) {
        this.userId = id;
    }

    public long getUserId() {
        return userId;
    }
}
