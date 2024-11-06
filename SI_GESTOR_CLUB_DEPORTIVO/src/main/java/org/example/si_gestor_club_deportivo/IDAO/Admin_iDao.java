package org.example.si_gestor_club_deportivo.IDAO;

import java.util.UUID;

import org.example.si_gestor_club_deportivo.DTO.Admin_DTO;

public interface Admin_iDao {

    public void insertarOactualizar (Admin_DTO u);

    public void eliminar (Admin_DTO u);
    

    public Admin_DTO obtener (UUID IDReser);
}