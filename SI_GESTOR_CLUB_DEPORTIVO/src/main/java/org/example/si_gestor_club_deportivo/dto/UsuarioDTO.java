package org.example.si_gestor_club_deportivo.dto;

public class UsuarioDTO {
    private Long userId;

    public UsuarioDTO() {}

    public UsuarioDTO(Long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
