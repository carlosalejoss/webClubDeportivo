package org.example.si_gestor_club_deportivo.IDAO;

import java.util.UUID;
import org.example.si_gestor_club_deportivo.DTO.Socio_DTO;

public interface Socio_IDAO {

    void insertarOactualizar (Socio_DTO u);

    void eliminar (Socio_DTO u);

    Socio_DTO obtener (UUID IDSocio);
}